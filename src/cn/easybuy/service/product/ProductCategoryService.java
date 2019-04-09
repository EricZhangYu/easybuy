package cn.easybuy.service.product;

import java.util.List;

import cn.easybuy.entity.ProductCategory;
import cn.easybuy.params.ProductCategoryParam;
import cn.easybuy.utils.ProductCategoryVo;

public interface ProductCategoryService {

    // 根据id查询商品分类
    public ProductCategory getById(Integer id);
    /**
     * 根据parentId查询所有的商品分类的信息
     */
    public List<ProductCategory> queryAllProductCategorylist(String parentId);

    //查询出所有的商品分类
    public List<ProductCategoryVo> queryAllProductCategorylist();

    //查询商品分类列表
    public List<ProductCategory> queryProductCategoryList(ProductCategoryParam params);

    //查询数目
    public int queryProductCategoryCount(ProductCategoryParam params);


    //根据sql查询商品分类
    public List<ProductCategory> queryProductCategorylistBySql(ProductCategoryParam params);

    public void modifyProductCategory(ProductCategory productCategory);
    /**
     * 添加商品分类
     */
    public void addProductCategory(ProductCategory productCategory);
    /**
     * 根据id删除
     */
    public void deleteById(Integer id);
}
