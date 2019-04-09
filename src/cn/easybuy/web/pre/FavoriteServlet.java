// Servlet Annotated Class.java模板(带注解的Servlet)

package cn.easybuy.web.pre;

import cn.easybuy.entity.Product;
import cn.easybuy.entity.User;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.MemcachedUtils;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

//商品的收藏
@WebServlet(name = "Favorite", urlPatterns = "/Favorite")
public class FavoriteServlet extends AbstractServlet {

    private ProductService productService;

    public void init() throws ServletException {
        productService=new ProductServiceImpl();
    }

    @Override
    public Class getServletClass() {
        return FavoriteServlet.class;
    }

    //添加收藏
    public ReturnResult addFavorite(HttpServletRequest request, HttpServletResponse response)throws Exception{
        ReturnResult result=new ReturnResult();
        String id=request.getParameter("id");
        Product product=productService.findById(id);
        List<Product> favoriteList=queryFavoriteList(request);
        //判断是否满了
        if(favoriteList.size()>0&&favoriteList.size()==5){
            favoriteList.remove(0);
        }
        boolean falg=false;
        for(int i=0;i<favoriteList.size();i++){
            if(favoriteList.get(i).getId().equals(product.getId())){
                falg=true;
                break;
            }
        }
        if(!falg){
            favoriteList.add(favoriteList.size(),product);
        }
        String key=getFavoriteKey(request);
        MemcachedUtils.add(key,favoriteList);
        return result.returnSuccess();
    }

    //从Memcached中获取已经收藏的product
    public List<Product> queryFavoriteList(HttpServletRequest request)throws Exception{
        String key=getFavoriteKey(request);
        List<Product>recentProducts=(List<Product>) MemcachedUtils.get(key);
        if(EmptyUtils.isEmpty(recentProducts)){
            recentProducts=new ArrayList<Product>();
        }
        return  recentProducts;
    }

    //获取Memcached中的key值
    public String getFavoriteKey(HttpServletRequest request)throws Exception{
        HttpSession session=request.getSession();
        User user=(User)session.getAttribute("loginUser");
        //判断用户是否登陆
        String key= EmptyUtils.isEmpty(user)?session.getId():user.getLoginName();
        return key;
    }

    public String favoriteList(HttpServletRequest request, HttpServletResponse response)throws Exception{
        List<Product>favoriteList=queryFavoriteList(request);
        request.setAttribute("favoriteList",favoriteList);
        return "/pre/product/favoriteList";
    }

}
