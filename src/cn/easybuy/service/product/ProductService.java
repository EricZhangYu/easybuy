package cn.easybuy.service.product;

import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;
import cn.easybuy.utils.Pager;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    //根据条件查询商品列表
    public List<Product> queryProductList(ProductParam params);

    //根据条件查询商品数量
    public int queryProductCount(ProductParam params);

    // 查询商品数目
    public int getProductRowCount(ProductParam params);

    //根据id查询商品
    Product findById(String id);//根据ID查询商品

    // 根据分类查询商品列表
    public List<Product> getProductsByCategory(Integer categoryId, int level, Pager pager, String keyWord);

    // 根据分类查询商品数目
    int getProductRowCount(String categoryId,int level,String keyWord);
//    int getProductRowCount(ProductParam params);

    //根据分类id查询数目
    public int getProductCountBycategory(Integer categoryId);

    //根据id删除商品
    public void deleteById(Integer id);

    //保存一款商品
    Integer saveOrUpdate(Product product)throws SQLException;

}


