package cn.easybuy.service.order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

import cn.easybuy.dao.order.OrderDao;
import cn.easybuy.dao.order.OrderDetailDao;
import cn.easybuy.dao.product.ProductDao;
import cn.easybuy.dao.order.OrderDaoImpl;
import cn.easybuy.dao.order.OrderDetailDaoImpl;
import cn.easybuy.dao.product.ProductDaoImpl;
import cn.easybuy.entity.Order;
import cn.easybuy.entity.OrderDetail;
import cn.easybuy.entity.User;
import cn.easybuy.params.OrderDetailParam;
import cn.easybuy.params.OrderParams;
import cn.easybuy.utils.DataSource;
import cn.easybuy.utils.Pager;
import cn.easybuy.utils.ShoppingCart;
import cn.easybuy.utils.ShoppingCartItem;
import cn.easybuy.utils.StringUtils;

public class OrderServiceImpl implements OrderService {
	private Connection connection;
	private OrderDao orderDao;
	private OrderDetailDao orderDetailDao;
	private ProductDao productDao;
	/**
	 * 结算购物车物品 1.生成订单 2.生成详情添加 3.库存修改 注意加入事物的控制
	 */
	public Order payShoppingCart( User user,String address,ShoppingCart cart) {// 购物
		Order order = new Order();
		try {
			connection = DataSource.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			OrderDetailDao orderDetailDao = new OrderDetailDaoImpl(connection);
			ProductDao productDao = new ProductDaoImpl(connection);
			// 开启事物控制，整体提交
			connection.setAutoCommit(false);
			// 更新订单
			order.setUserId(user.getId());
			order.setUserAddress(address);
			order.setCreateTime(new Date());
			order.setCost(cart.getTotalCost());
			order.setSerialNumber(StringUtils.randomUUID());//生成订单编号
			order.setLoginName(user.getLoginName());
			orderDao.saveOrder(order);
			for (ShoppingCartItem item : cart.getItems()) {
				// 更新订单详情
				OrderDetail ordrDetail = new OrderDetail();
				ordrDetail.setOrderId(order.getId());
				ordrDetail.setCost(item.getCost());
				ordrDetail.setProduct(item.getProduct());
				ordrDetail.setQuantity(item.getQuantity());
				orderDetailDao.saveOrderDetail(ordrDetail, order.getId());//信息的保存
				//修改库存
				productDao.updateStock(item.getProduct().getId(),item.getQuantity());
			}
			connection.commit();//进行事务的提交
		} catch (SQLException e) {
			connection.rollback();//如果出现异常，进行事务的回滚
			order = null;
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
			return order;
		}
	}

	public Order findById(String id) {// 根据ID获取订单
		Connection connection = null;
		Order order = null;
		try {
			connection = DataSource.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			order = (Order) orderDao.getOrderById(Integer.valueOf(id));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return order;
	}

	public int getOrderRowCount(OrderParams params) {// 获取订单记录条数
		Connection connection = null;
		int rtn = 0;
		try {
			connection = DataSource.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			rtn = orderDao.queryOrderCount(params);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return rtn;
	}

	@Override
	public List<OrderDetail> queryOrderDetailList(OrderDetailParam param) {
		Connection connection = null;
		List<OrderDetail> rtn = null;
		try {
			connection = DataSource.openConnection();
			OrderDetailDao orderDetailDao = new OrderDetailDaoImpl(connection);
			rtn = orderDetailDao.queryOrderDetailList(param);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return rtn;
	}

	/**
	 * 查询订单列表
	 * 
	 * @param userId
	 * @param pager
	 * @return
	 */
	@Override
	public List<Order> queryOrderList(Integer userId, Pager pager) {
		Connection connection = null;
		List<Order> orderList = null;
		try {
			connection = DataSource.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			OrderParams params = new OrderParams();
			params.setUserId(userId);
			params.setStartIndex((pager.getCurrentPage() - 1) * pager.getRowPerPage());
			params.setPageSize(pager.getRowPerPage());
			params.setSort(" createTime desc ");
			orderList = orderDao.queryOrderList(params);
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				OrderDetailParam orderDetailParam=new OrderDetailParam();
				orderDetailParam.setOrderId(order.getId());
				order.setOrderDetailList(queryOrderDetailList(orderDetailParam));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return orderList;
	}

	public List<Order> queryOrderList(Pager pager) {
		Connection connection = null;
		List<Order> orderList = null;
		try {
			connection = DataSource.openConnection();
			OrderDao orderDao = new OrderDaoImpl(connection);
			OrderParams params = new OrderParams();
			params.openPage((pager.getCurrentPage() - 1) * pager.getRowPerPage(), pager.getRowPerPage());
			orderList=orderDao.queryOrderList(params);
			for (int i = 0; i < orderList.size(); i++) {
				Order order = orderList.get(i);
				OrderDetailParam orderDetailParam=new OrderDetailParam();
				orderDetailParam.setOrderId(order.getId());
				order.setOrderDetailList(queryOrderDetailList(orderDetailParam));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return orderList;
	}



}
