package cn.easybuy.utils;

import java.sql.*;


public class DataSource {
    private static String driver = ConfigManager.getProperty("driver"); // 读取配置文件driver
    private static String url = ConfigManager.getProperty("url");  // 读取配置文件url
    private static String user = ConfigManager.getProperty("username"); // 读取配置文件user
    private static String password = ConfigManager.getProperty("password"); // 读取配置文件password

    /*获取数据库连接对象*/
    public static Connection openConnection() throws SQLException{
        Connection conn = null;
        try {
            Class.forName(driver);  // 加载驱动
            conn = DriverManager.getConnection(url,user,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return conn;       // 返回连接数据库对象
    }

    /* 关闭数据库连接,关闭流 */
    public static void closeConnection(Connection conn) {
        // 若数据库连接对象不为空，则关闭
        try {
            if (null != conn  && !conn.isClosed())
                conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* 关闭PreparedStatement / ResultSet连接 */
    public static void closeTwo (PreparedStatement pstm, ResultSet rs){
        // 若结果集对象rs不为空，则关闭
        try {
            if (rs != null && !rs.isClosed())
                rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 若pstm对象不为空，则关闭
        try {
            if (pstm != null && !pstm.isClosed())
                pstm.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
