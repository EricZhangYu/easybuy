package cn.easybuy.service.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import cn.easybuy.dao.user.UserDao;
import cn.easybuy.dao.user.UserDaoImpl;
import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;
import cn.easybuy.utils.DataSource;

public class UserServiceImpl implements UserService {

	private Connection connection;//提供数据库的连接  对连接进行手动的提交和事务的回滚
	private UserDao uDao;

	@Override
	public User getUserByLoginName(String loginName) {
		User user = null;
		try {
			connection = DataSource.openConnection();//获取数据库连接
			uDao = new UserDaoImpl(connection);//实例化uesrDao
			user = uDao.getUserByLoginName(loginName);//获取user对象
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);//资源的释放
		}
		return user;
	}

	@Override
	public boolean save(User user) {
		boolean flag = true;
		try {
			connection = DataSource.openConnection();
			uDao = new UserDaoImpl(connection);
			int count = uDao.save(user);
			flag = count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public void update(User user) {//更新用户信息
		Connection connection = null;
		try {
			connection = DataSource.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.update(user);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
	}

	@Override
	public void delete(String id) {//根据ID删除用户
		Connection connection = null;
		try {
			connection = DataSource.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userDao.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
	}

	@Override
	public List<User> queryUserList(UserParam userParam) {
		Connection connection = null;
		List<User> userList = null;
		try {
			connection = DataSource.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			userList = userDao.queryUserList(userParam);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return userList;
	}

	@Override
	public int queryUserCount(UserParam userParam) {
		Connection connection = null;
		int count=0;
		try {
			connection = DataSource.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			count = userDao.queryUserCount(userParam);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return count;
	}

	@Override
	public User queryUserById(Integer userId) {
		Connection connection = null;
		User user = null;
		try {
			connection = DataSource.openConnection();
			UserDao userDao = new UserDaoImpl(connection);
			user = (User) userDao.queryUserById(userId);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DataSource.closeConnection(connection);
		}
		return user;
	}
}
