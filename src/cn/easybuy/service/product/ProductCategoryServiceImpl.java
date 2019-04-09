package cn.easybuy.service.product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.easybuy.dao.product.ProductCategoryDao;
import cn.easybuy.dao.product.ProductCategoryDaoImpl;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.utils.DataSource;
//import cn.easybuy.utils.DataSourceUtil;
import cn.easybuy.utils.DataSource;
import cn.easybuy.utils.ProductCategoryVo;

public class ProductCategoryServiceImpl implements ProductCategoryService {
    private Connection connection;
    private ProductCategoryDao pcDao;//定义变量之后如果报空指针异常，首先判断要对其进行初始化

    public ProductCategory getById(Integer id) {
        Connection connection = null;
        ProductCategory productCategory = null;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategory =productCategoryDao.queryProductCategoryById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return productCategory;
    }

    public List<ProductCategory> queryAllProductCategorylist(String parentId) {
        List<ProductCategory> pcList=new ArrayList<ProductCategory>();

        try {
//            connection=DataSourceUtil.openConnection();
            connection=DataSource.openConnection();//service层要调用dao层的方法，那么必须保证sercivev层里面的connection连接和dao层里面连接是一个对象
            pcDao=new ProductCategoryDaoImpl(connection);//这里的connection就是dao层里面的connection  保证这两个层里用的是同一个数据库的连接
            if(null==parentId||"".equals(parentId)){
                parentId="0";//查询到所有的一级分类
            }
            pcList=pcDao.queryAllProductCategorylist(parentId);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
//            DataSourceUtil.closeConnection(connection);
            DataSource.closeConnection(connection);
        }
        return pcList;
    }

    @Override
    public List<ProductCategoryVo> queryAllProductCategorylist() {

        //首先查询一级分类的列表 点一一个用于保存结果的结果集
        List<ProductCategoryVo> pc1VoList=new ArrayList<ProductCategoryVo>();
        //查询一级分类
        List<ProductCategory> pcList=queryAllProductCategorylist(null);
        //查询二级分类
        for(ProductCategory productCategory1:pcList){
            ProductCategoryVo pc1Vo=new ProductCategoryVo();//一级分类的vo列表
            pc1Vo.setProductCategory(productCategory1);
            //查询二级分类Vo列表
            List<ProductCategoryVo> pc2VoList=new ArrayList<ProductCategoryVo>();
            //查询二级分类
            List<ProductCategory> pc2List=queryAllProductCategorylist(productCategory1.getId().toString());
            for(ProductCategory productCategory2:pc2List){
                ProductCategoryVo pc2Vo=new ProductCategoryVo();
                pc2Vo.setProductCategory(productCategory2);
                //查询三级分类的Vo列表
                List<ProductCategoryVo>pc3VoList=new ArrayList<ProductCategoryVo>();
                //查询三级分类
                List<ProductCategory> pc3List=queryAllProductCategorylist(productCategory2.getId().toString());
                for(ProductCategory productCategory3:pc3List){
                    ProductCategoryVo pc3Vo=new ProductCategoryVo();
                    pc3Vo.setProductCategory(productCategory3);
                    pc3VoList.add(pc3Vo);
                }
                pc2Vo.setProductCategoryVosList(pc3VoList);
                pc2VoList.add(pc2Vo);
            }
            pc1Vo.setProductCategoryVosList(pc2VoList);
            pc1VoList.add(pc1Vo);
        }
        return pc1VoList;
    }

    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params) {
        Connection connection = null;
        List<ProductCategory> rtn = null;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategorylist(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return rtn;
    }

    //查询商品数目
    @Override
    public int queryProductCategoryCount(ProductCategoryParam params) {
        Connection connection = null;
        int rtn = 0;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategoryCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return rtn;
    }

    //根据sql查询商品分类
    public List<ProductCategory> queryProductCategorylistBySql(ProductCategoryParam params) {
        Connection connection = null;
        List<ProductCategory> rtn = null;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            rtn = productCategoryDao.queryProductCategorylist(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    public void modifyProductCategory(ProductCategory productCategory) {
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.update(productCategory);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    @Override
    public void addProductCategory(ProductCategory productCategory) {
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.save(productCategory);//调用dao里面的save方法进行商品分类的添加
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            ProductCategoryDao productCategoryDao = new ProductCategoryDaoImpl(connection);
            productCategoryDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
    }
}
