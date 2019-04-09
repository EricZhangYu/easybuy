package cn.easybuy.dao;

import cn.easybuy.utils.EmptyUtils;

import java.sql.*;


public class BaseDaoImpl {
	private Connection connection;
	private PreparedStatement pstm;//多次执行sql语句应使用PreparedStatement对象处理
	private ResultSet rs;//数据库处理结果集
	
	public BaseDaoImpl(Connection connection) {
		super();
		this.connection = connection;
	}
	//根据条件查询出数据库中的结果集
	public ResultSet executeQuery(String sql,Object[] params){
		ResultSet rs=null;
		try {
			pstm=connection.prepareStatement(sql);
			if(!EmptyUtils.isEmpty(params)) {
				for (int i = 0; i < params.length; i++) {
					pstm.setObject(i + 1, params[i]);
				}
			}
			rs=pstm.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}

	//返回新增记录的主键值
	public int executeInsert(String sql,Object[]params)  {
		Long id=0L;
		try {
			//拿主键值  在多表关联的时候  其它表的修改或许会用到这个值，所以说这个地方最好返回一个主键值
			pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < params.length; i++) {
				pstm.setObject(i + 1, params[i]);
			}
			pstm.executeUpdate();
			ResultSet rs = pstm.getGeneratedKeys();//返回一个object对象  包含自动生成的主键值
			if (rs.next()) {
				id = rs.getLong(1);
			}
		}catch(SQLException e){
			e.printStackTrace();
			id=null;
		}
		return id.intValue();
	}

	//修改，删除和新增
	//返回值 是操作影响的行数
	public int execuptUpdate(String sql,Object[] params){
		int updateRows=0;
		try {
			pstm=connection.prepareStatement(sql);
			for(int i=0;i<params.length;i++){
                pstm.setObject(i+1,params[i]);
            }
			updateRows=pstm.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			updateRows=-1;
		}
		return updateRows;
	}


	public boolean closeResource(){
		if(null!=pstm){
			try {
				pstm.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	public boolean closeResource(ResultSet rs){
		if(null!=rs){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
}
