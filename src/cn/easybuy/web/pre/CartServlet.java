// Servlet Annotated Class.java模板(带注解的Servlet)

package cn.easybuy.web.pre;

import cn.easybuy.entity.Order;
import cn.easybuy.entity.Product;
import cn.easybuy.entity.User;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.service.order.CartService;
import cn.easybuy.service.order.CartServiceImpl;
import cn.easybuy.service.order.OrderService;
import cn.easybuy.service.order.OrderServiceImpl;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.service.user.UserAddressService;
import cn.easybuy.service.user.UserAddressServiceImpl;
import cn.easybuy.service.user.UserService;
import cn.easybuy.service.user.UserServiceImpl;
import cn.easybuy.utils.*;
import cn.easybuy.web.AbstractServlet;
import com.sun.org.apache.xml.internal.resolver.readers.ExtendedXMLCatalogReader;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

//购物车
@WebServlet(name = "Cart", urlPatterns = {"/Cart"})
public class CartServlet extends AbstractServlet{

    private ProductService productService;
    private ProductCategoryService productCategoryService;
    private CartService cartService;
    private UserService userService;
    private UserAddressService userAddressService;
    private OrderService orderService;

    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        orderService = new OrderServiceImpl();
        userService = new UserServiceImpl();
        productCategoryService = new ProductCategoryServiceImpl();
        cartService = new CartServiceImpl();
        userAddressService = new UserAddressServiceImpl();
    }

    //添加购物车
    public ReturnResult add(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        request.setCharacterEncoding("UTF-8");  // 处理post请求乱码问题
        ReturnResult result=new ReturnResult();
        String id=request.getParameter("entityId");
        String quantityStr=request.getParameter("quantity");
        Integer quantity=1;
        if(EmptyUtils.isNotEmpty(quantityStr)){
            quantity=Integer.parseInt(quantityStr);
        }
        //根据对应的id查询出商品
        Product product=productService.findById(id);
        if(product.getStock()<quantity){
            return result.returnFail("商品数量不足");
        }
        //如果商品数量充足  获取购物车
       ShoppingCart cart=getCartFromSession(request);
        //往购物车中添加商品
        result=cart.addItem(product,quantity);
        if(result.getStatus()== Constants.ReturnResult.SUCCESS){
            cart.setSum(EmptyUtils.isEmpty(cart)?0.0:cart.getSum()+(product.getPrice()*quantity*1.0));
        }
        return result;
    }

    //从session中获取购物车信息
    private ShoppingCart getCartFromSession(HttpServletRequest request){
        HttpSession session=request.getSession();
        ShoppingCart cart=(ShoppingCart) session.getAttribute("cart");
        if(EmptyUtils.isEmpty(cart)){
            cart=new ShoppingCart();
            session.setAttribute("cart",cart);
        }
        return cart;
    }

    @Override
    public Class getServletClass() {
        return CartServlet.class;
    }

    public String refreshCart(HttpServletRequest request, HttpServletResponse response)throws Exception{
        return "/common/pre/searchBar";
    }
    public String toSettlement(HttpServletRequest request, HttpServletResponse response){
        List<ProductCategoryVo> pcList=productCategoryService.queryAllProductCategorylist();
        request.setAttribute("pcList",pcList);
        return "/pre/settlement/toSettlement";
    }

    //跳转到购物车维护页面
    public String toSettlement1(HttpServletRequest request, HttpServletResponse response)throws Exception{
        return "/pre/settlement/settlement1";
    }

    //将用户的地址查询出来
    public String settlement2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        User user = getUserFromSession(request);
        List<UserAddress> userAddressList = userAddressService.queryUserAdressList(user.getId());
        request.setAttribute("userAddressList", userAddressList);
        return "/pre/settlement/settlement2";
    }

    //成功提交订单
    public Object settlement3(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShoppingCart cart=getCartFromSession(request);
        //对购物车中商品的库存进行核对
        ReturnResult result=checkCart(request);
        if(result.getStatus()==Constants.ReturnResult.FAIL){
            return result;
        }
        User user=getUserFromSession(request);
        //新增地址
        String addressId=request.getParameter("addressId");
        String newAddress=request.getParameter("newAddress");
        String newRemark=request.getParameter("newRemark");

        UserAddress userAddress=new UserAddress();
        if("-1".equals(addressId)){
            userAddress.setAddress(newAddress);
            userAddress.setRemark(newRemark);
            userAddressService.addUserAddress(user.getId(),newAddress,newRemark);
        }else{
            userAddress=userAddressService.getUserAddressById(Integer.parseInt(addressId));
        }
        //生成订单
        Order order=orderService.payShoppingCart(user,userAddress.getAddress(),cart);
        request.setAttribute("order",order);
        clearCart(request,response);
        return "/pre/settlement/settlement3";
    }

    //清空购物车
    public ReturnResult clearCart(HttpServletRequest request, HttpServletResponse response)throws Exception{
        ReturnResult result=new ReturnResult();
        //结账后清空购物车
        request.getSession().removeAttribute("cart");
        result.returnSuccess(null);
        return result;
    }

    //核对购物车中的商品
    public ReturnResult checkCart(HttpServletRequest request){
        ReturnResult result=new ReturnResult();
        HttpSession session=request.getSession();
        ShoppingCart cart=getCartFromSession(request);
        for(ShoppingCartItem item:cart.getItems()){
            Product product=productService.findById(item.getProduct().getId()+"");
            if(product.getStock()<item.getQuantity()){
                return result.returnFail(product.getName()+"商品数量不足");
            }
        }
        return result;
    }

    //修改购物车
    public ReturnResult modifyCart(HttpServletRequest request, HttpServletResponse response)throws Exception{
        ReturnResult result=new ReturnResult();
        HttpSession session=request.getSession();//从session中获取cart
        String id= request.getParameter("entityId");//获取前台传过来的id
        String quantityStr=request.getParameter("quantity");//前台传过来的商品数量
        if(EmptyUtils.isEmpty(id)||EmptyUtils.isEmpty(quantityStr)){
            return result.returnFail("参数不能为空");
        }
        Integer quantity=Integer.parseInt(quantityStr);
        ShoppingCart cart=getCartFromSession(request);
        Product product=productService.findById(id);
        if(quantity>product.getStock()){
            return result.returnFail("商品数量不足");
        }
        //修改购物车
        cart=cartService.modifyShoppingCart(id,quantity,cart);
        session.setAttribute("cart",cart);
         return result.returnSuccess();
    }

    private User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("loginUser");
        return user;
    }
}
