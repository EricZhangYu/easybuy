package cn.easybuy.utils;

import cn.easybuy.entity.ProductCategory;

import java.util.List;

//商品级别的分类
public class ProductCategoryVo {
    private ProductCategory productCategory;//可以包含商品分类的数据
    private List<ProductCategoryVo> productCategoryVosList;//如果拿到了一(二级)级分类的Vo  将一级分类全部放去里面， 还要放一级分类对应的二(三级)级分类

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategoryVosList(List<ProductCategoryVo> productCategoryVosList) {
        this.productCategoryVosList = productCategoryVosList;
    }

    public List<ProductCategoryVo> getProductCategoryVosList() {
        return productCategoryVosList;
    }
}
