package cn.easybuy.dao.user;

import java.util.List;

import cn.easybuy.entity.UserAddress;
import cn.easybuy.params.UserAddressParam;

public interface UserAddressDao {
	
	public List<UserAddress> queryUserAddressList(UserAddressParam param);//查询用户地址列表
	
	public Integer saveUserAddress(UserAddress userAddress);//保存用户地址
	
	public UserAddress getUserAddressById(Integer addressId);//根据id获取指定用户地址

}
