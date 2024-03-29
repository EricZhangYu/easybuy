package cn.easybuy.service.order;

import cn.easybuy.utils.ShoppingCart;

public interface CartService {
    //修改购物车商品数量
    public ShoppingCart modifyShoppingCart(String productId,Integer quantity,ShoppingCart cart)throws Exception;

    //对购物车总金额进行重新计算
    public ShoppingCart calculate(ShoppingCart cart)throws Exception;
}
