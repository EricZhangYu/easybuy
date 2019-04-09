package cn.easybuy.utils;

import cn.easybuy.entity.Product;

import java.util.ArrayList;
import java.util.List;

//购物车中添加的商品进行操作
public class ShoppingCart {
    private List<ShoppingCartItem> items= new ArrayList<ShoppingCartItem>();;//购物车中的商品
    private double sum;//商品总金额

    public List<ShoppingCartItem> getItems() {
        return items;
    }

    public void setItems(List<ShoppingCartItem> items) {
        this.items = items;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ReturnResult addItem(Product product,Integer quantity){
        ReturnResult result=new ReturnResult();
        boolean falg=false;
        //对购物车中的商品进行遍历
        for(int i=0;i<items.size();i++){
            //判断购物车中是否已经有该商品 如果有则累加数量  不是则添加新的商品
            if(items.get(i).getProduct().getId().equals(product.getId())){
                //判断已经有的商品数量和添加的数量是否小于等于商品的库存
                if(items.get(i).getQuantity()+quantity>product.getStock()){
                    return  result.returnFail("商品数量不足");//如果比商品的库存多，返回  添加商品数量不足
                }else{
                    items.get(i).setQuantity(items.get(i).getQuantity()+quantity);//如果商品的数量充足，就将已经有的数量和新添加的数量进行累计
                    falg=true;
                }
            }
        }
        if(!falg){
            items.add(new ShoppingCartItem(product,quantity));
        }
        return result.returnSuccess();
    }

    //从购物车中删除商品
    public void removeItem(int index){
        items.remove(index);
    }

    //修改数量  index告诉后台要修改哪一个的数量
    public  void modefyQuantity(int index,Integer quantity){
        items.get(index).setQuantity(quantity);//设置items的数量
    }

    public float getTotalCost() {
        float sum = 0;
        for (ShoppingCartItem item : items) {
            sum = sum + item.getCost();
        }
        return sum;
    }
}
