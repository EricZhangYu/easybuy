package cn.easybuy.dao.product;

import java.util.List;

import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;

/*
* 在数据库中查询所有的商品分类的信息
* */
public interface ProductCategoryDao {
    public List<ProductCategory> queryAllProductCategorylist(String parentId);//查询所有的商品分类列表

    void deleteById(Integer parseLong);//删除商品分类

    //分页查询出所有商品的分类
    public List<ProductCategory> queryProductCategorylist(ProductCategoryParam param);

    public ProductCategory queryProductCategoryById(Integer id);//根据id查询商品分类

    public Integer save(ProductCategory productCategory) ;//保存商品分类

    public Integer queryProductCategoryCount(ProductCategoryParam param);//查询商品分类数量

    public void update(ProductCategory productCategory) ;//更新
}
