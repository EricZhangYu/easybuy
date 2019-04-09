// Servlet Annotated Class.java模板(带注解的Servlet)

package cn.easybuy.web.pre;

import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.ProductCategoryVo;
import cn.easybuy.web.AbstractServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//商品
@WebServlet(name = "Product", urlPatterns = {"/Product"})
public class ProductServlet extends AbstractServlet {

    private ProductService productService;
    private ProductCategoryService productCategoryService;

    //根据搜索框中的关键词寻找商品
    public String queryProductList(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
        request.setCharacterEncoding("UTF-8");  // 处理post请求乱码问题
        String categoryId=request.getParameter("categoryId");
        String keyWord=request.getParameter("keyWord");
        //第几页
        String currentPageStr=request.getParameter("currentPage");
        //页面的大小
        String pageSizeStr=request.getParameter("pageSize");
        //页面容量
        int rowPerPage= EmptyUtils.isNotEmpty(pageSizeStr)? Integer.parseInt(pageSizeStr):8;
        //当前页
        int currentPage=EmptyUtils.isNotEmpty(currentPageStr)?Integer.parseInt(currentPageStr):1;
        //实现分页必须实现的属性
        ProductParam params=new ProductParam();
        params.setCategoryId(EmptyUtils.isNotEmpty(categoryId)?Integer.parseInt(categoryId):null);
        params.setKeyword(keyWord);//接收到的关键词
        //计算出从哪条记录开始
        params.openPage((currentPage-1)*rowPerPage,rowPerPage);
        //页面的总数量
        int total=productService.queryProductCount(params);
        Pager pager=new Pager(total,rowPerPage,currentPage);
        pager.setUrl("/Product?action=queryProductList&categoryId="+(EmptyUtils.isNotEmpty(categoryId)?categoryId:null)+"&keyWord="+(EmptyUtils.isNotEmpty(keyWord)?keyWord:null));

        //给前台返回商品列表
        List<ProductCategoryVo> productCategoryVoList=productCategoryService.queryAllProductCategorylist();
        List<Product> pList=productService.queryProductList(params);
        request.setAttribute("pList",pList);
        request.setAttribute("pager",pager);
        request.setAttribute("total",total);
        request.setAttribute("keyWord",keyWord);
        request.setAttribute("pcList",productCategoryVoList);
        return "/pre/product/productList";
    }

    public void init() throws ServletException{
        productService=new ProductServiceImpl();
        productCategoryService=new ProductCategoryServiceImpl();
    }

    @Override
    public Class getServletClass() {
        return ProductServlet.class;
    }

}
