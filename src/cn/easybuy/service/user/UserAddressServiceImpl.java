package cn.easybuy.service.user;

import cn.easybuy.dao.user.UserAddressDao;
import cn.easybuy.dao.user.UserAddressDaoImpl;
import cn.easybuy.entity.UserAddress;
import cn.easybuy.params.UserAddressParam;
import cn.easybuy.utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by bdqn on 2016/5/12.
 */
public class UserAddressServiceImpl implements UserAddressService {
    /**
     * 查询用户地址列表
     *
     * @param id
     * @return
     * @throws Exception
     */
    public List<UserAddress> queryUserAdressList(Integer id) throws Exception{
        Connection connection = null;
        List<UserAddress> userAddressList = null;
        try {
            connection = DataSource.openConnection();
            UserAddressDao userAddressDao = new UserAddressDaoImpl(connection);
            UserAddressParam params = new UserAddressParam();
            params.setUserId(id);
            userAddressList = userAddressDao.queryUserAddressList(params);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	DataSource.closeConnection(connection);
        }
        return userAddressList;
    }
    /**
     * 添加用户地址
     *
     * @param id
     * @param address
     * @return
     */
    @Override
    public Integer addUserAddress(Integer id, String address,String remark) {
        Connection connection = null;
        Integer userAddressId = null;
        try {
            connection = DataSource.openConnection();
            UserAddressDao userAddressDao = new UserAddressDaoImpl(connection);
            UserAddress userAddress=new UserAddress();
            userAddress.setUserId(id);
            userAddress.setAddress(address);
            userAddress.setRemark(remark);
            userAddressId = userAddressDao.saveUserAddress(userAddress);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
        	DataSource.closeConnection(connection);
        }
        return userAddressId;
    }

    @Override
    public UserAddress getUserAddressById(Integer id) {
        Connection connection = null;
        UserAddress userAddress= null;
        try {
            connection = DataSource.openConnection();
            UserAddressDao userAddressDao = new UserAddressDaoImpl(connection);
            userAddress = (UserAddress) userAddressDao.getUserAddressById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            DataSource.closeConnection(connection);
            return  userAddress;
        }
    }
}
