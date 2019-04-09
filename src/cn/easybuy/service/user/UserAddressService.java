package cn.easybuy.service.user;
import cn.easybuy.entity.UserAddress;

import java.util.List;

public interface UserAddressService {
    // 根据loginName 查询用户地址
    public List<UserAddress> queryUserAdressList(Integer id) throws Exception;
    //给用户添加地址
    public Integer addUserAddress(Integer id, String address, String remark) throws Exception;
    //根据id查询地址
    public UserAddress getUserAddressById(Integer id);

}
