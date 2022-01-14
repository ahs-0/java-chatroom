package date;

import common.User;
import utils.DbUtils;

import java.sql.*;

/**
 * 实现登录功能
 */
public class QueryCommand implements Command{

    @Override
    public boolean execute(User u) {
        String username=u.getUserId();
        String pwd=u.getPassWord();
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;
        boolean loop=false;
        try {
            conn= DbUtils.getConnection();
            String sql="select * from info where username="+username+" and password="+pwd;
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
//            System.out.println(rs);
            while(rs.next()){
                loop=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return loop;
        }
    }
}
