package cn.easybuy.service.user;

import cn.easybuy.entity.User;
import cn.easybuy.params.UserParam;

import java.util.List;

//根据用户的账号获取用户记录
public interface UserService {
	public User getUserByLoginName(String loginName);//根据用户名查询用户信息

	public boolean save(User user);//保存用户信息

	public void update(User user);//更新用户信息

	void delete(String id);//根据用户名删除用户

	public List<User> queryUserList(UserParam userParam);//查询用户列表

	public int queryUserCount(UserParam params);//查询用户数量

	public User queryUserById(Integer userId);//根据ID查询用户
}
