package cn.easybuy.dao.order;
import java.util.List;

import cn.easybuy.entity.Order;
import cn.easybuy.params.OrderParams;

/***
 * 订单处理的dao层
 */
public interface OrderDao {

	public void saveOrder(Order order) ;//保存订单

	public void deleteById(Integer id);//根据id删除指定订单
	
	public Order getOrderById(Integer id) ;//根据id获得订单
	
	public List<Order> queryOrderList(OrderParams params) ;//查询订单列表
	
	public Integer queryOrderCount(OrderParams params);//查询订单数量
}
