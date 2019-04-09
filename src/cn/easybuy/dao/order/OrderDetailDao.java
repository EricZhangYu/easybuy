package cn.easybuy.dao.order;

import cn.easybuy.entity.OrderDetail;
import cn.easybuy.params.OrderDetailParam;

import java.sql.SQLException;
import java.util.List;

/**
 * 订单详细
 */
public interface OrderDetailDao {

	//保存订单详情
    public void saveOrderDetail(OrderDetail detail, int orderId) throws SQLException;

    //根据id删除订单
	public void deleteById(OrderDetail detail) throws Exception;

	public OrderDetail getOrderDetailById(Integer id)throws Exception; 

	//查询所有的订单列表
	public List<OrderDetail> queryOrderDetailList(OrderDetailParam params)throws Exception; 

	//查询订单列表的数量
	public Integer queryOrderDetailCount(OrderDetailParam params)throws Exception; 
}
