package user.dao;

import user.utils.DBConfig;
import user.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean login(String u_username, String u_password) {

        String sql = "select * from user  where u_username=? and u_password=?";

        Connection connection = null;
        PreparedStatement pstmt =  null;
        ResultSet rs = null;
        try {
            connection = DBUtils.getConnection();

            //获取执行SQL语句的对象
            pstmt = connection.prepareStatement(sql);
            //设置参数
            pstmt.setString(1,u_username);
            pstmt.setString(2,u_password);

            //执行SQL
            rs = pstmt.executeQuery();
            System.out.println(rs);
            //处理结果
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtils.closeConnection(rs,pstmt,connection);
        }
        return false;
    }

    @Override
    public int re(String u_username, String u_password, String u_sex, String u_hobbies, String u_phone, String u_email,
                  String u_address,String is_delete) {

        String sql = "insert into user(u_username,u_password,u_sex,u_hobbies,u_phone,u_email,u_address,is_delete)value(?,?,?,?,?,?,?,?)";

        Connection connection = null;
        PreparedStatement pstmt =  null;
        try {
            connection = DBUtils.getConnection();

            //获取执行SQL语句的对象
            pstmt = connection.prepareStatement(sql);
            //设置参数
            //从一开始
            pstmt.setString(1,u_username);
            pstmt.setString(2,u_password);
            pstmt.setString(3,u_sex);
            pstmt.setString(4,u_hobbies);
            pstmt.setString(5,u_phone);
            pstmt.setString(6,u_email);
            pstmt.setString(7,u_address);
            pstmt.setString(8,"0");

            //执行SQL
            int i = pstmt.executeUpdate();
            System.out.println(i);
            //处理结果
           return i;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            DBUtils.closeConnection(null,pstmt,connection);
        }
        return -1;
    }
}

