package cn.easybuy.service.order;

import cn.easybuy.utils.ShoppingCart;
import cn.easybuy.utils.ShoppingCartItem;

public class CartServiceImpl implements CartService {
    @Override
    //修改购物车商品数量
    public ShoppingCart modifyShoppingCart(String productId, Integer quantity, ShoppingCart cart) throws Exception {
        for(ShoppingCartItem item:cart.getItems()){
            if(item.getProduct().getId().toString().equals(productId)){
                if(quantity == 0||quantity<0){
                    cart.getItems().remove(item);
                    break;
                }else{
                    item.setQuantity(quantity);
                }
            }
        }
        //购物车总金额的重新计算
        cart=calculate(cart);
        return cart;
    }

    @Override
    //对购物车总金额进行重新计算
    public ShoppingCart calculate(ShoppingCart cart) throws Exception {
        double sum=0.0;
        for(ShoppingCartItem item:cart.getItems()){
            sum+=item.getQuantity()*item.getProduct().getPrice();//对每个item进行金额的计算
                item.setCost(item.getQuantity()*item.getProduct().getPrice());
        }
        cart.setSum(sum);
        return cart;
    }
}
