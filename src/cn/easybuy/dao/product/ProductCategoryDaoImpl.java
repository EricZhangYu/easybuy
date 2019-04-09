package cn.easybuy.dao.product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.utils.EmptyUtils;

//在数据库中查找商品列表的具体实现方法
public class ProductCategoryDaoImpl extends BaseDaoImpl implements  ProductCategoryDao{
//    private Connection connection;
//    private PreparedStatement pstm;
//    private ResultSet rs;


    public ProductCategoryDaoImpl(Connection connection){//创建一个构造方法，为了保证service层和dao层里面的connection是一个对象
    	super(connection);
    }

    public ProductCategory tableToClass(ResultSet rs) throws Exception{
    	ProductCategory pc=new ProductCategory();
    	//对所有的属性进行赋值
        pc.setId(rs.getInt("id"));
        pc.setName(rs.getString("name"));
        pc.setParentId(rs.getInt("parentId"));
        pc.setType(rs.getInt("type"));
        pc.setIconClass(rs.getString("iconClass"));
        return pc;
    }

    public ProductCategory mapToClass(Map map) throws Exception {
        ProductCategory productCategory = new ProductCategory();
        Object idObject=map.get("id");
        Object nameObject=map.get("name");
        Object parentIdObject=map.get("parentId");
        Object typeObject=map.get("type");
        Object iconClassObject=map.get("iconClass");
        Object parentNameObject=map.get("parentName");
        productCategory.setId(EmptyUtils.isEmpty(idObject)?null:(Integer)idObject);
        productCategory.setName(EmptyUtils.isEmpty(nameObject)?null:(String)nameObject);
        productCategory.setParentId(EmptyUtils.isEmpty(parentIdObject)?null:(Integer)parentIdObject);
        productCategory.setType(EmptyUtils.isEmpty(typeObject)?null:(Integer)typeObject);
        productCategory.setIconClass(EmptyUtils.isEmpty(iconClassObject)?null:(String)iconClassObject);
        productCategory.setParentName(EmptyUtils.isEmpty(parentNameObject)?null:(String)parentNameObject);
        return productCategory;
    }

    public List<ProductCategory> queryAllProductCategorylist(String parentId) {
        List<ProductCategory> pcList=new ArrayList<ProductCategory>();//pcList作为最后的返回值
        //定义sql查询语句  StringBuffer可以随时改变要搜索的数据库代码  改变的时候不需要再创建对象
        StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where 1=1 ");
        List<Object>params=new ArrayList<Object>();
        ResultSet rs=null;//表示数据库结果集的数据表
        if(parentId!=null&&!"".equals(parentId)){
            sql.append("and parentId=?");//parentId在非空的情况下拼接一个查询的条件
            params.add(parentId);
         }
        try {
//            pstm=connection.prepareStatement(sql.toString());
//            if(null!=parentId&&!"".equals(parentId)){
//                pstm.setObject(1,parentId);
//            }
//            rs=pstm.executeQuery();//执行查询
        	rs=this.executeQuery(sql.toString(), params.toArray());
            while(rs.next()){
            	ProductCategory pc=tableToClass(rs);
                pcList.add(pc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
           this.closeResource(rs);
           this.closeResource();
        }
        return pcList;
    }

    public void deleteById(Integer id){
        String sql = " delete from easybuy_product_category where id = ? ";
        Object params[] = new Object[] { id };
        this.execuptUpdate(sql.toString(), params);
    }

    @Override
    public List<ProductCategory> queryProductCategorylist(ProductCategoryParam params) {
        List<Object> paramsList=new ArrayList<Object>();
        List<ProductCategory> productList=new ArrayList<ProductCategory>();
        StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where 1=1 ");
        ResultSet resultSet=null;
        try {
            if(EmptyUtils.isNotEmpty(params.getName())){
                sql.append(" and name like ? ");
                paramsList.add("%"+params.getName()+"%");
            }
            if(EmptyUtils.isNotEmpty(params.getParentId())){
                sql.append(" and parentId = ? ");
                paramsList.add(params.getParentId());
            }
            if(EmptyUtils.isNotEmpty(params.getType())){
                sql.append(" and type = ? ");
                paramsList.add(params.getType());
            }
            if(params.isPage()){
                sql.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
            }
            resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
            while (resultSet.next()) {
                ProductCategory productCategory = this.tableToClass(resultSet);
                productList.add(productCategory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return productList;
    }

    public Integer queryProductCategoryCount(ProductCategoryParam params){
        List<Object> paramsList=new ArrayList<Object>();
        Integer count=0;
        StringBuffer sql=new StringBuffer("SELECT count(*) count FROM easybuy_product_category where 1=1 ");
        if(EmptyUtils.isNotEmpty(params.getName())){
            sql.append(" and name like ? ");
            paramsList.add("%"+params.getName()+"%");
        }
        if(EmptyUtils.isNotEmpty(params.getParentId())){
            sql.append(" and parentId = ? ");
            paramsList.add(params.getParentId());
        }
        ResultSet resultSet=this.executeQuery(sql.toString(), paramsList.toArray());
        try {
            while (resultSet.next()) {
                count=resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return count;
    }

    public ProductCategory queryProductCategoryById(Integer id){
        List<Object> paramsList=new ArrayList<Object>();
        ProductCategory productCategory=null;
        StringBuffer sql=new StringBuffer("SELECT id,name,parentId,type,iconClass  FROM easybuy_product_category where id = ? ");
        ResultSet resultSet=this.executeQuery(sql.toString(),new Object[]{id});
        try {
            while (resultSet.next()) {
                productCategory = this.tableToClass(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
            this.closeResource(resultSet);
        }
        return productCategory;
    }

    public Integer save(ProductCategory productCategory)  {//新增用户信息
        Integer id=0;
        try {
            String sql=" INSERT into easybuy_product_category(name,parentId,type,iconClass) values(?,?,?,?) ";
            Object param[]=new Object[]{productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass()};
            id=this.executeInsert(sql,param);
            productCategory.setId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
        return id;
    }

    @Override
    public void update(ProductCategory productCategory) {
        try {
            Object[] params = new Object[] {productCategory.getName(),productCategory.getParentId(),productCategory.getType(),productCategory.getIconClass(),productCategory.getId()};
            String sql = " UPDATE easybuy_product_category SET name=?,parentId=?,type=?,iconClass=? WHERE id =?  ";
            this.execuptUpdate(sql, params);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            this.closeResource();
        }
    }

}
