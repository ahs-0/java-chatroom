package date;
/**
 * 注册用户
 */

import common.User;
import utils.DbUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertCommand implements Command{
    @Override
    public boolean execute(User u) {
        boolean loop=false;
        String userName=u.getUserId();
        String pwd=u.getPassWord();
        Connection conn=null;
        PreparedStatement pstmt=null;
        try {
            conn= DbUtils.getConnection();
            String sql="insert into info (username,password) values(?,?)";
             pstmt= conn.prepareStatement(sql);
             pstmt.setString(1,userName);
             pstmt.setString(2,pwd);
            int rs = pstmt.executeUpdate();
            if(rs==1){
                loop=true;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            DbUtils.closeConnection(conn,null,pstmt);
            return loop;
        }
    }
}
