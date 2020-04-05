package user.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.*;

public class DruidUtils {
    private static DataSource ds;
    static {
        //1,加载配置文件
        Properties pro= new Properties();

        InputStream is = DruidUtils.class.getClassLoader().getResourceAsStream("druid.properties");

        try {
            pro.load(is);
            //创建数据库连接池
            ds = DruidDataSourceFactory.createDataSource(pro);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关闭数据库连接
     * @param rs
     * @param stmt
     * @param conn
     */
    public static void closeConnection(ResultSet rs, Statement stmt, Connection conn){
        try {
            if(rs!=null){
                rs.close();
            }
            if(stmt!=null){
                stmt.close();
            }
            if(conn!=null){
                conn.close();//;//在数据库连接池中，关闭连接，意味着归还连接
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws SQLException {
        Connection connection = getConnection();
        System.out.println(connection);

    }
}
