package cn.easybuy.dao.product;

import cn.easybuy.entity.Product;
import cn.easybuy.params.ProductParam;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {
    /*
    *根据条件查询商品列表
    * */
    public List<Product> queryProductList(ProductParam params)throws SQLException;

    //根据条件查询商品数量
    public int queryProductCount(ProductParam params)throws  SQLException;

    public Product getProductById(Integer id)throws Exception;//根据id获取商品

    public void update(Product product) throws Exception;//更新商品

    void updateStock(Integer id, Integer quantity) throws SQLException;//更新商品库存

    public void deleteById(Integer id) throws Exception;//根据id删除指定商品

    public void save(Product product) throws Exception;//保存商品
}
