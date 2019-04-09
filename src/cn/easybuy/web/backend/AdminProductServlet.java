package cn.easybuy.web.backend;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.easybuy.entity.Product;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.params.ProductParam;
import cn.easybuy.service.product.ProductCategoryService;
import cn.easybuy.service.product.ProductService;
import cn.easybuy.service.product.ProductCategoryServiceImpl;
import cn.easybuy.service.product.ProductServiceImpl;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.Params;
import cn.easybuy.utils.ReturnResult;
import cn.easybuy.utils.StringUtils;
import cn.easybuy.web.AbstractServlet;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/admin/product"},name = "adminProduct")
public class AdminProductServlet extends AbstractServlet {

	private ProductCategoryService productCategoryService;
	private static final String TMP_DIR_PATH = "c:\\tmp";
	private File tmpDir;//临时文件目录
	private static final String DESTINATION_DIR_PATH = "/files";//文件上传的绝对路径
	private File destinationDir;
	private ProductService productService;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		tmpDir = new File(TMP_DIR_PATH);
		if (!tmpDir.exists()) {//如果目录不存在，则新建目录
			tmpDir.mkdirs();
		}
		String realPath = getServletContext().getRealPath(DESTINATION_DIR_PATH);
		destinationDir = new File(realPath);
		destinationDir.mkdirs();//创建目录
		if (!destinationDir.isDirectory()) {
			throw new ServletException(DESTINATION_DIR_PATH
					+ " is not a directory");
		}
		productService = new ProductServiceImpl();
		productCategoryService=new ProductCategoryServiceImpl();
	}
	/**
	 * 商品的主页面
	 * @param request
	 * @param response
	 * @return
	 */
	public String index(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//获取当前页数
		String currentPageStr = request.getParameter("currentPage");
		//获取页大小
		String pageSize = request.getParameter("pageSize");
		int rowPerPage  = EmptyUtils.isEmpty(pageSize)?8:Integer.parseInt(pageSize);
		int currentPage = EmptyUtils.isEmpty(currentPageStr)?1:Integer.parseInt(currentPageStr);
		ProductParam params =new ProductParam();
		int total=productService.getProductRowCount(new ProductParam());
		Pager pager=new Pager(total,rowPerPage,currentPage);
		params.openPage((pager.getCurrentPage()-1)*pager.getRowPerPage(),pager.getRowPerPage());
		pager.setUrl("/admin/product?action=index");
		List<Product> productList=productService.queryProductList(params);
		request.setAttribute("productList", productList);
		request.setAttribute("pager", pager);
		request.setAttribute("menu",5);
		return "/backend/product/productList";
	}
	/**
	 * 添加商品
	 * @return
	 */
	public String toAddProduct(HttpServletRequest request,HttpServletResponse response)throws Exception{
		request.setAttribute("menu",6);
		request.setAttribute("product",new Product());
		//查询一级分类
		ProductCategoryParam params =new ProductCategoryParam();
		params.setType(1);
		List<ProductCategory> productCategoryList=productCategoryService.queryProductCategoryList(params);
		//一级分类列表
		request.setAttribute("productCategoryList1", productCategoryList);
		return "/backend/product/toAddProduct";
	}
	/**
	 * 添加商品
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void addProduct(HttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String, String> params = new HashMap<String, String>();//用于接受前台传递过来的form表单所有数据
		Product product=null;
		//用来设置缓冲区的大小和临时文件目录
		DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
		fileItemFactory.setSizeThreshold(1 * 1024 * 1024); // 1 MB  临时文件目录
		fileItemFactory.setRepository(tmpDir);
		String fileName = null;
		//临时文件的处理
		ServletFileUpload uploadHandler = new ServletFileUpload(fileItemFactory);
		uploadHandler.setHeaderEncoding("utf-8");
		try {
			List items = uploadHandler.parseRequest(request);
			Iterator itr = items.iterator();//封装表单的数据
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				if (item.isFormField()) {//如果是一个普通的数据
					params.put(item.getFieldName(), item.getString("utf-8"));
				} else {
					if (item.getSize() > 0) {//修改图片  判断上传文件的字节数
						fileName = item.getName().substring(
								item.getName().lastIndexOf("."));
						File file = new File(destinationDir, fileName);
						fileName = StringUtils.randomUUID() + item.getName().substring(item.getName().lastIndexOf("."));
						file = new File(destinationDir, fileName);//图片名与商品ID一致
						item.write(file);//保存商品图片  将图片写入目标路径
					}
				}
			}
			//获取商品参数   将params中封装的所有表单的数据放到product对象里面
			product=copyToProduct(params);
			if(EmptyUtils.isNotEmpty(fileName)){
				product.setFileName(fileName);
			}
			productService.saveOrUpdate(product);
		}catch (Exception e){
			e.printStackTrace();
		}
		response.sendRedirect(request.getContextPath()+"/admin/product?action=index");
	}
	/**
	 * 添加商品
	 * @return
	 */
	public String toUpdateProduct(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String id=request.getParameter("id");
		Product product=productService.findById(id);
		request.setAttribute("menu",6);//菜单的编号
		//查询一级分类
		ProductCategoryParam params =new ProductCategoryParam();
		params.setType(1);
		List<ProductCategory> productCategoryList1=productCategoryService.queryProductCategoryList(params);
		params.setType(2);
		List<ProductCategory> productCategoryList2=productCategoryService.queryProductCategoryList(params);
		params.setType(3);
		List<ProductCategory> productCategoryList3=productCategoryService.queryProductCategoryList(params);
		//一级分类列表
		request.setAttribute("productCategoryList1", productCategoryList1);
		request.setAttribute("productCategoryList2", productCategoryList2);
		request.setAttribute("productCategoryList3", productCategoryList3);
		request.setAttribute("product", product);
		return "/backend/product/toAddProduct";
	}
	/**
	 * 根据id删除商品
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ReturnResult deleteById(HttpServletRequest request,HttpServletResponse response)throws Exception{
		ReturnResult result=new ReturnResult();
		String id=request.getParameter("id");
		productService.deleteById(Integer.parseInt(id));
		result.returnSuccess();
		return result;
	}

	private Product copyToProduct(Map<String,String> params)throws Exception{
		Product product=new Product();
		String id=params.get("id");
		String name=params.get("name");
		String description=params.get("description");
		String price=params.get("price");
		String stock=params.get("stock");
		String categoryLevel1Id=params.get("categoryLevel1Id");
		String categoryLevel2Id=params.get("categoryLevel2Id");
		String categoryLevel3Id=params.get("categoryLevel3Id");
		product.setName(name);
		product.setDescription(description);
		product.setPrice(Float.valueOf(price));
		product.setStock(Integer.parseInt(stock));
		product.setCategoryLevel1Id(EmptyUtils.isNotEmpty(categoryLevel1Id)?Integer.parseInt(categoryLevel1Id):0);
		product.setCategoryLevel2Id(EmptyUtils.isNotEmpty(categoryLevel2Id)?Integer.parseInt(categoryLevel2Id):0);
		product.setCategoryLevel3Id(EmptyUtils.isNotEmpty(categoryLevel3Id)?Integer.parseInt(categoryLevel3Id):0);
		product.setId(EmptyUtils.isNotEmpty(id)?Integer.parseInt(id):null);
		product.setFileName(params.get("fileName"));
		return product;
	}

	@Override
	public Class getServletClass() {
		return AdminProductServlet.class;
	}
}
