package cn.easybuy.web.pre;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.easybuy.entity.News;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.service.news.NewsService;
import cn.easybuy.service.news.NewsServiceImpl;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.utils.ProductCategoryVo;
import cn.easybuy.web.AbstractServlet;

import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "Home", urlPatterns = "/Home")
public class HomeServlet extends AbstractServlet {
  private  ProductCategoryService pcService;
  private NewsService newsService;
  private static Logger logger=Logger.getLogger(String.valueOf(HomeServlet.class));
  
  //在servlet初始化的时候执行一些内容
  public void init()throws ServletException{
		pcService=new ProductCategoryServiceImpl();
		newsService=new NewsServiceImpl();

  }
  
    /*protected void doGet(HttpServletRequest request,HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        request.setCharacterEncoding("UTF-8");  // 处理post请求乱码问题
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        response.setContentType("text/html;charset=UTF-8"); // 处理响应乱码问题:字节流需getBytes("UTF-8")
        // str = new String(str.getBytes("ISO-8859-1"), "UTF-8");// 处理get请求乱码问题
        // response.getWriter().write("你好 servlet!");
        pcService=new ProductCategoryServiceImpl();//需要对pcService进行初始化
        List<ProductCategory> pcList=pcService.queryAllProductCategorylist("0");
        request.setAttribute("pcList",pcList);
        //跳转回首页
        request.getRequestDispatcher("/pre/Index.jsp").forward(request,response);
    }*/
	
	public String index(HttpServletRequest request, HttpServletResponse response)throws Exception{
       //list通过service里面的方法来进行查询获取
		List<ProductCategoryVo> pcList=pcService.queryAllProductCategorylist();//这里得到的pcList将是三个级别所有的分类
        List<News> newsList=newsService.queryAllNews();//获取到想要的newsList
		request.setAttribute("pcList",pcList);//将值返回给前台
		request.setAttribute("newsList",newsList);
        return "/pre/Index";
	}

	public String index2(HttpServletRequest request,HttpServletResponse response)throws Exception{
	    String testString=request.getParameter("testString");   
	    logger.info(testString);
		return "/pre/Index";
		}
	
	@Override
	public Class getServletClass() {
		return HomeServlet.class;
	}
	
	
	
	
	
	
	
	
	
}
