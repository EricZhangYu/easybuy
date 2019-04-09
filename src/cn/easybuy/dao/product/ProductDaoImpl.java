package cn.easybuy.dao.product;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.utils.EmptyUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDaoImpl  extends BaseDaoImpl implements ProductDao {
    public ProductDaoImpl(Connection connection) {
        super(connection);
    }

    public Product tableToClass(ResultSet rs) throws Exception {
        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("name"));
        product.setDescription(rs.getString("description"));
        product.setPrice(rs.getFloat("price"));
        product.setStock(rs.getInt("stock"));
        product.setCategoryLevel1Id(rs.getInt("categoryLevel1Id"));
        product.setCategoryLevel2Id(rs.getInt("categoryLevel2Id"));
        product.setCategoryLevel3Id(rs.getInt("categoryLevel3Id"));
        product.setFileName(rs.getString("fileName"));
        return product;
    }

    @Override
    public List<Product> queryProductList(ProductParam params) throws SQLException{
        List<Product> pList=new ArrayList<Product>();
        StringBuffer sql=new StringBuffer("select * from easybuy_product where 1=1 ");
        ResultSet rs=null;
        List<Object> paramsList=new ArrayList<Object>();
        try {
            //根据关键词进行查询
            if(EmptyUtils.isNotEmpty(params.getKeyword())){
                sql.append(" and name like ?");
                paramsList.add("%"+params.getKeyword()+"%");
            }
            //根据分类进行查询
            if(EmptyUtils.isNotEmpty(params.getCategoryId())){
                //可能根据一级分类  二级分类 三级分类进行查询，所以要将三种情况都考虑进来
                sql.append(" and(categoryLevel1Id=? or categoryLevel2Id=? or categoryLevel3Id=?)");
                paramsList.add(params.getCategoryId());
                paramsList.add(params.getCategoryId());
                paramsList.add(params.getCategoryId());
            }
            //排序
            if(EmptyUtils.isNotEmpty(params.getSort())){
                sql.append(" order by " + params.getSort());
            }
            //分页
            if(EmptyUtils.isNotEmpty(params.isPage())){
                sql.append(" limit "+params.getStartIndex()+" ,"+params.getPageSize());
            }
            //对从数据库中查询出来的结果集进行赋值
            rs=this.executeQuery(sql.toString(),paramsList.toArray());
            while (rs.next()){
                Product p=tableToClass(rs);
                //将rs中的数据保存到pList中
                pList.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            this.closeResource(rs);
            this.closeResource();
        }
        return pList;
    }

    @Override
    public int queryProductCount(ProductParam params) {
        Integer num=0;
        StringBuffer sql=new StringBuffer("select count(*) count from easybuy_product where 1=1");
        ResultSet rs=null;
        List<Object> paramsList=new ArrayList<Object>();
        try {
            if(EmptyUtils.isNotEmpty(params.getKeyword())){
                sql.append(" and name like ?");
                paramsList.add("%"+params.getKeyword()+"%");
            }
            if(EmptyUtils.isNotEmpty(params.getCategoryId())){
                sql.append(" and(categoryLevel1Id=? or categoryLevel2Id=? or categoryLevel3Id=?)");
                paramsList.add(params.getCategoryId());
                paramsList.add(params.getCategoryId());
                paramsList.add(params.getCategoryId());
            }
            rs=this.executeQuery(sql.toString(),paramsList.toArray());
            while (rs.next()){
                num=rs.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }finally {
            this.closeResource(rs);
            this.closeResource();
        }
        return num;
    }

    public Product getProductById(Integer id) throws Exception {
        String sql = " select id,name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete from easybuy_product where id = ? ";
        ResultSet resultSet = null;
        Product product = null;
        try {
            Object params[] = new Object[] { id };
            resultSet = this.executeQuery(sql, params);
            while (resultSet.next()) {
                product = tableToClass(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeResource(resultSet);
            this.closeResource();
            return product;
        }
    }
    public void updateStock(Integer id, Integer quantity) {
        try {
            Object[] params = new Object[] {quantity,id};
            String sql = " update easybuy_product set stock=? where id=? ";
            this.execuptUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    public void update(Product product) {
        try {
            Object[] params = new Object[] {product.getName(),product.getStock(),product.getPrice(),product.getFileName(),product.getCategoryLevel1Id(),product.getCategoryLevel2Id(),product.getCategoryLevel3Id(),product.getId()};
            String sql = " update easybuy_product set name=?,stock=?,price=?,fileName=?,categoryLevel1Id=?,categoryLevel3Id=?,categoryLevel3Id=? where id=? ";
            this.execuptUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    public void deleteById(Integer id) throws SQLException {
        String sql = " delete from easybuy_product where id = ? ";
        Object params[] = new Object[] { id };
        try{
            this.execuptUpdate(sql.toString(), params);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

    public void save(Product product) {
        Integer id=0;
        String sql=" insert into easybuy_product(name,description,price,stock,categoryLevel1Id,categoryLevel2Id,categoryLevel3Id,fileName,isDelete) values(?,?,?,?,?,?,?,?,?) ";
        try {
            Object param[]=new Object[]{product.getName(),product.getDescription(),product.getPrice(),product.getStock(),product.getCategoryLevel1Id(),product.getCategoryLevel2Id(),product.getCategoryLevel3Id(),product.getFileName(),0};
            id=this.executeInsert(sql,param);
            product.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }
}
