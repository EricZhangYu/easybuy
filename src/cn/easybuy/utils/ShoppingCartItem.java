package cn.easybuy.utils;

import cn.easybuy.entity.Product;

import java.io.Serializable;

//购物车中的商品的属性
public class ShoppingCartItem implements Serializable{
    private Product product;
    private Integer quantity;//商品数量
    private float cost;//价格

    public ShoppingCartItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.cost=product.getPrice()*quantity;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public float getCost() {
        return cost;
    }
}
