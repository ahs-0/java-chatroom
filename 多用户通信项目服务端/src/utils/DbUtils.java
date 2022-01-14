package utils;

import java.sql.*;

public class DbUtils {
    /**
     * 建立连接
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String url="jdbc:mysql://localhost:3306/qq";
        //1.加载并注册JDBC驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.创建数据库连接
        String user="";
        String password="";
        Connection conn= DriverManager.getConnection(url,user,password);
        return conn;
    }

    /**
     * 关闭连接，释放资源
     * @param conn
     * @param re
     * @param stmt
     */
    public static void closeConnection(Connection conn, ResultSet re, Statement stmt){
        if(re!=null){
            try {
                re.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt!=null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if(conn!=null&&conn.isClosed()!=false){
                    conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
