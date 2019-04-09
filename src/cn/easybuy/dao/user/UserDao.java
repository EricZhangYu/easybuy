package cn.easybuy.dao.user;

import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;

import java.util.List;

//根据用户的账号获取用户记录
public interface UserDao {
	public User getUserByLoginName(String loginName) throws Exception;

	//用户注册
	public int save(User user);

	void update(User user) throws Exception;//更新用户信息

	public void deleteById(String id) throws Exception;//根据id删除用户

	public List<User> queryUserList(UserParam params)throws Exception;//查询用户列表

	public Integer queryUserCount(UserParam params) throws Exception;//查询用户数量

	public User queryUserById(Integer id) throws Exception;//根据id查询用户
}
