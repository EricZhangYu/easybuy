package cn.easybuy.service.product;

import cn.easybuy.dao.product.ProductDao;
import cn.easybuy.dao.product.ProductDaoImpl;
import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.utils.DataSource;
import cn.easybuy.utils.EmptyUtils;
import cn.easybuy.utils.Pager;
import org.apache.taglibs.standard.tag.common.sql.DataSourceUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName
 * @Author zhangyukang
 * @Date 2019/3/11 16:57
 **/
public class ProductServiceImpl implements ProductService{

    private Connection connection;
    //connection都是在service中打开的  在写service是实现类的时候，都要先定义connection

    private ProductDao pDao;
    @Override//查询商品列表
    public List<Product> queryProductList(ProductParam params) {
        List<Product> pList=new ArrayList<Product>();//作为返回值数据的接收者
        try {
            connection= DataSource.openConnection();//打开connection进行数据库的查询
            pDao=new ProductDaoImpl(connection);//dao的实例化
            pList=pDao.queryProductList(params);//查询product中的列表
        } catch (SQLException e) {
            e.printStackTrace();
            return  null;
        }finally {
            DataSource.closeConnection(connection);
        }
        return pList;
    }

    @Override
    public int queryProductCount(ProductParam params) {
        Integer num=0;
        try {
            connection= DataSource.openConnection();
            pDao=new ProductDaoImpl(connection);
            num=pDao.queryProductCount(params);
        } catch (SQLException e) {
            e.printStackTrace();
            return  0;
        }finally {
            DataSource.closeConnection(connection);
        }
        return num;
    }


     //根据id查询商品
    public Product findById(String id) {//根据ID查询商品
        Connection connection = null;
        Product product = null;
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            product = productDao.getProductById(Integer.parseInt(id));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return product;
    }

    /**
     * 根据商品分类查询商品列表
     *
     * @param categoryId
     * @param level
     * @param pager
     * @return
     */
    public List<Product> getProductsByCategory(Integer categoryId, int level, Pager pager, String keyWord) {//根据分类查询商品
        Connection connection = null;
        List<Product> rtn = new ArrayList<Product>();
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            //设置查询参数
            ProductParam params = new ProductParam();
            params.openPage((pager.getCurrentPage() - 1) * pager.getRowPerPage(), pager.getRowPerPage());
            params.setCategoryId(categoryId==0?null:categoryId);
            if(!EmptyUtils.isEmpty(keyWord)){
                params.setKeyword(keyWord);
            }
            rtn = productDao.queryProductList(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return rtn;
    }

    @Override
    /**
     * 根据分类id查询商品数目
     *
     * @param categoryId
     * @param level
     * @return
     */
    public int getProductRowCount(String categoryId, int level,String keyWord) {//查询商品记录数
        Connection connection = null;
        int rtn = 0;
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            ProductParam params = new ProductParam();
            Long id = null;
            if (EmptyUtils.isNotEmpty(categoryId)) {
                id = Long.parseLong(categoryId);
                params.setCategoryId(id==0L?null:id.intValue());
            }
            if(!EmptyUtils.isEmpty(keyWord)){
                params.setKeyword(keyWord);
            }
            rtn = productDao.queryProductCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return rtn;
    }

    /**
     * 查询商品全部的分类
     *
     * @return
     */
    public int getProductRowCount(ProductParam params) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            count = productDao.queryProductCount(params);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            DataSource.closeConnection(connection);
        }
        return count;
    }

    /**
     * 根据分类查询数目
     */
    @Override
    public int getProductCountBycategory(Integer categoryId) {
        Connection connection = null;
        int count = 0;
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            ProductParam param=new ProductParam();
            param.setCategoryLevel1Id(categoryId);
            count = productDao.queryProductCount(param);
            if(count>0) return count;
            param.setCategoryLevel2Id(categoryId);
            count = productDao.queryProductCount(param);
            if(count>0) return count;
            param.setCategoryLevel3Id(categoryId);
            count = productDao.queryProductCount(param);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return count;
    }

    @Override
    public void deleteById(Integer id) {
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            //设置查询参数
            productDao.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
    }

    @Override
    public Integer saveOrUpdate(Product product) {//保存一款商品
        Connection connection = null;
        try {
            connection = DataSource.openConnection();
            ProductDao productDao = new ProductDaoImpl(connection);
            ProductParam params = new ProductParam();
            if(EmptyUtils.isEmpty(product.getId())){
                productDao.save(product);
            } else {
                productDao.update(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSource.closeConnection(connection);
        }
        return null;
    }
}
