package cn.easybuy.dao.user;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import cn.easybuy.dao.BaseDaoImpl;
import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;
import cn.easybuy.utils.EmptyUtils;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {
	
	private Connection connection;

	public UserDaoImpl(Connection connection) {
		super(connection);
	}

	public User tableToClass(ResultSet rs) throws Exception {
        User user = new User();
        //将ResultSet中的值取到之后放到user中  在返回一个user对象
        user.setLoginName(rs.getString("loginName"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        user.setSex(rs.getInt("sex"));
        user.setIdentityCode(rs.getString("identityCode"));
        user.setEmail(rs.getString("email"));
        user.setMobile(rs.getString("mobile"));
        user.setType(rs.getInt("type"));
        user.setId(rs.getInt("id"));
        return user;
    }
	
	@Override
	public User getUserByLoginName(String loginName) throws Exception{
		StringBuffer sql=new StringBuffer("select * from easybuy_user where 1=1");
		List<Object>params=new ArrayList<Object>();
		
		if(!EmptyUtils.isEmpty(loginName)){
			sql.append(" and loginName = ? ");
			params.add(loginName);
		}
		ResultSet rs=executeQuery(sql.toString(), params.toArray());
		User user=null;
		while(rs.next()){
			user=tableToClass(rs);
		}
		return user;
	}

	@Override
	public int save(User user) {
		Integer id=0;
		StringBuffer sql=new StringBuffer("INSERT into easybuy_user(loginName,userName,password,sex,identityCode,email,mobile) values(?,?,?,?,?,?,?);");
		Object[]params=new Object[]{user.getLoginName(),user.getUserName(),user.getPassword(),user.getSex(),user.getIdentityCode(),user.getEmail(),user.getMobile()};
		//调用基类中的方法
		id=this.executeInsert(sql.toString(),params);
		return id;
	}

	//更新用户信息
	public void update(User user) {
		try {
			Object[] params = new Object[] {user.getLoginName(),user.getUserName(),user.getType(),user.getSex(),user.getIdentityCode(),user.getEmail(),user.getMobile(),user.getId()};
			String sql = " UPDATE easybuy_user SET loginName=?,userName =?,type=?,sex =?, identityCode =?, email =?, mobile =? WHERE id =?  ";
			this.execuptUpdate(sql, params);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
		}
	}

	@Override
	public void deleteById(String id) {
		String sql = " delete from easybuy_user where id = ? ";
		Object params[] = new Object[] { id };
		try{
			this.execuptUpdate(sql.toString(), params);
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
		}
	}

	@Override
	public List<User> queryUserList(UserParam params) throws Exception {
		List<Object> paramsList=new ArrayList<Object>();
		List<User> userList=new ArrayList<User>();
		StringBuffer sql=new StringBuffer("  select id,loginName,password,userName,sex,identityCode,email,mobile,type from easybuy_user where 1=1 ");
		ResultSet resultSet = null;
		try {
			if(EmptyUtils.isNotEmpty(params.getLoginName())){
				sql.append(" and loginName = ? ");
				paramsList.add(params.getLoginName());
			}
			if(EmptyUtils.isNotEmpty(params.getSort())){
				sql.append(" order by " + params.getSort()+" ");
			}
			if(params.isPage()){
				sql.append(" limit  " + params.getStartIndex() + "," + params.getPageSize());
			}
			resultSet=this.executeQuery(sql.toString(),paramsList.toArray());
			while (resultSet.next()) {
				User user = this.tableToClass(resultSet);
				userList.add(user);
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return userList;
	}

	public Integer queryUserCount(UserParam params) throws Exception {
		List<Object> paramsList=new ArrayList<Object>();
		StringBuffer sql=new StringBuffer(" select count(*) count from easybuy_user where 1=1 ");
		Integer count=0;
		if(EmptyUtils.isNotEmpty(params.getLoginName())){
			sql.append(" and loginName = ? ");
			paramsList.add(params.getLoginName());
		}
		ResultSet resultSet = this.executeQuery(sql.toString(),paramsList.toArray());
		try {
			while (resultSet.next()) {
				count=resultSet.getInt("count");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return count;
	}


	@Override
	public User queryUserById(Integer id) throws Exception {
		List<Object> paramsList=new ArrayList<Object>();
		List<User> userList=new ArrayList<User>();
		StringBuffer sql=new StringBuffer("  select id,loginName,userName,password,sex,identityCode,email,mobile,type from easybuy_user where id=? ");
		ResultSet resultSet = this.executeQuery(sql.toString(),new Object[]{id});
		User user=null;
		try {
			while (resultSet.next()) {
				user = this.tableToClass(resultSet);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			this.closeResource();
			this.closeResource(resultSet);
		}
		return user;
	}

}
