package cn.easybuy.service.order;
import java.util.List;

import cn.easybuy.entity.*;
import cn.easybuy.params.OrderDetailParam;
import cn.easybuy.params.OrderParams;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.Params;
import cn.easybuy.utils.ShoppingCart;

public interface OrderService {

	//订单生成
	Order payShoppingCart( User user,String adress,ShoppingCart cart);//购物

	List<Order> queryOrderList(Integer id, Pager pager);//查询订单列表

	public int getOrderRowCount(OrderParams params);

	List<OrderDetail> queryOrderDetailList(OrderDetailParam params);//查询订单详情

	Order findById(String parameter);//根据ID获取订单
	//查询全部订单
	List<Order> queryOrderList(Pager pager);
}
