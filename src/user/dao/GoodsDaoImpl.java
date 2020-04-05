package user.dao;

import user.domain.Goods;
import user.utils.DBConfig;
import user.utils.DBUtils;
import user.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GoodsDaoImpl implements GoodsDao {
    @Override
    public List<Goods> getList() {
        List<Goods> goodsList = new ArrayList<>();
        //定于Sql语句
        //查询is_delete为0的
        String sql = "select * from goods where is_delete=0";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                int g_id = rs.getInt("g_id");
                String g_goods_name = rs.getString("g_goods_name");
                String g_goods_pic = rs.getString("g_goods_pic");
                double g_goods_price = rs.getInt("g_goods_price");
                String g_goods_description = rs.getString("g_goods_description");
                int g_goods_stock = rs.getInt("g_goods_stock");
                String is_delete = rs.getString("is_delete");

                Goods goods = new Goods();

                goods.setG_id(g_id);
                goods.setG_goods_name(g_goods_name);
                goods.setG_goods_pic(g_goods_pic);
                goods.setG_goods_price(g_goods_price);
                goods.setG_goods_description(g_goods_description);
                goods.setG_goods_stock(g_goods_stock);
                goods.setIs_delete(is_delete);

                goodsList.add(goods);
            }
            return goodsList;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection(rs,pstmt,conn);
        }
        return null;
    }


    @Override
    public int add(Goods goods) {
        String sql = "insert into goods(g_goods_name,g_goods_pic,g_goods_price,g_goods_description,g_goods_stock,is_delete)" +
                "value(?,?,?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt =null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, goods.getG_goods_name());
            pstmt.setString(2, goods.getG_goods_pic());
            pstmt.setDouble(3, goods.getG_goods_price());
            pstmt.setString(4, goods.getG_goods_description());
            pstmt.setInt(5, goods.getG_goods_stock());
            pstmt.setString(6,"0");

            int result = pstmt.executeUpdate();
            if(result>0){
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection(null,pstmt,conn);
        }
        return -1;
    }


    @Override
    public Goods getGoodsId(int id) {
        List<Goods> goodsList = new ArrayList<>();
        //定于Sql语句
        //查询is_delete为0的
        String sql = "select * from goods where g_id=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                int g_id = rs.getInt("g_id");
                String g_goods_name = rs.getString("g_goods_name");
                String g_goods_pic = rs.getString("g_goods_pic");
                double g_goods_price = rs.getInt("g_goods_price");
                String g_goods_description = rs.getString("g_goods_description");
                int g_goods_stock = rs.getInt("g_goods_stock");
                String is_delete = rs.getString("is_delete");

                Goods goods = new Goods();

                goods.setG_id(g_id);
                goods.setG_goods_name(g_goods_name);
                goods.setG_goods_pic(g_goods_pic);
                goods.setG_goods_price(g_goods_price);
                goods.setG_goods_description(g_goods_description);
                goods.setG_goods_stock(g_goods_stock);
                goods.setIs_delete(is_delete);
                return goods;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection(rs,pstmt,conn);
        }
        return null;
    }

    @Override
    public int edit(Goods goods) {
        String sql = "update goods set g_goods_name=?,g_goods_pic=?,g_goods_price=?," +
                "g_goods_description=?,g_goods_stock=?,is_delete=? where g_id=?";
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtils.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, goods.getG_goods_name());
            pstmt.setString(2, goods.getG_goods_pic());
            pstmt.setDouble(3, goods.getG_goods_price());
            pstmt.setString(4, goods.getG_goods_description());
            pstmt.setInt(5, goods.getG_goods_stock());
            pstmt.setString(6,goods.getIs_delete());
            pstmt.setInt(7, goods.getG_id());

            //执行
            int result = pstmt.executeUpdate();
            System.out.println("受影响行数："+result);
            return result;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtils.closeConnection(null, pstmt, conn);
        }
        return -1;
    }

    @Override
    //逻辑删除 改变一下isdelete的值
    public int delete(String[] ids) {
        StringBuffer str = new StringBuffer();
        String idStr = null;
        if (ids != null) {
            for (String id : ids) {
                str.append(id).append(",");
            }
            idStr = str.toString().substring(0, str.length() - 1);
        }
            System.out.println("批量选中的id"+idStr);
            String sql = "update goods set is_delete=? where g_id in(" + idStr + ")";

             Connection conn = null;
             PreparedStatement pstmt = null;
            try {
                conn = DBUtils.getConnection();
                pstmt = conn.prepareStatement(sql);//预编译

                pstmt.setString(1, "1");

                int result = pstmt.executeUpdate();
                return result;

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DBUtils.closeConnection(null, pstmt, conn);
            }
        return -1;
    }



    @Override
    public int findTotalCount() {
        //1,定义SQL
        String sql  = "select count(*) totalCount from goods where is_delete='0'";
        //
        Connection conn = null;
        PreparedStatement pstmt =null;
        ResultSet rs = null;

        try {
            //2,通过数据库连接池获取数据库连接
            conn = DruidUtils.getConnection();
            //3,获取执行SQL语句的对象
            pstmt = conn.prepareStatement(sql);
            //执行查询
            rs = pstmt.executeQuery();
            //处理查询结果
            if(rs.next()){
                int totalCount = rs.getInt("totalCount");
                return totalCount;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            //关闭资源
            DruidUtils.closeConnection(rs,pstmt,conn);
        }
        return 0;
    }


    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        //1.定义模板初始化sql
        String sql = "select count(*) totalCount from goods where 1=1 and is_delete='0'";
        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key) || "method".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        System.out.println(sb.toString());
        System.out.println(params);


        Long count = 0L;
        List<Map<String, Object>> datas = new ArrayList<>();


        try {
            datas = DBUtils.executeQuery(sb.toString(),params.toArray());

            count = (Long) datas.get(0).get("totalCount");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count.intValue();
    }

    @Override
    public List<Map<String, Object>> getListNew() {
        List<Map<String, Object>> datas = new ArrayList<>();

        //1,定义SQL
        //只查询删除状态为0的，没有逻辑删除过的记录
        String sql = "select * from goods where is_delete='0'";
        try {
            datas = DBUtils.executeQuery(sql,null);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return datas;
    }

    @Override
    public List<Goods> findByPage(int start, int rows) {
        List<Goods> list = new ArrayList<>();
        //1,定义执行的SQL语句
        String sql  = "select * from goods where is_delete = '0' limit ?,?";
        //2,
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            //,获取数据库连接
            conn = DruidUtils.getConnection();
            //
            pstmt = conn.prepareStatement(sql);
            //设置参数
            pstmt.setInt(1,start);
            pstmt.setInt(2,rows);
            //执行SQL
            rs = pstmt.executeQuery();
            while(rs.next()){
                //逐列获取
                int g_id = rs.getInt("g_id");
                String g_goods_name = rs.getString("g_goods_name");
                String g_goods_pic = rs.getString("g_goods_pic");
                double g_goods_price = rs.getInt("g_goods_price");
                String g_goods_description = rs.getString("g_goods_description");
                int g_goods_stock = rs.getInt("g_goods_stock");
                String is_delete = rs.getString("is_delete");

                Goods goods = new Goods();

                goods.setG_id(g_id);
                goods.setG_goods_name(g_goods_name);
                goods.setG_goods_pic(g_goods_pic);
                goods.setG_goods_price(g_goods_price);
                goods.setG_goods_description(g_goods_description);
                goods.setG_goods_stock(g_goods_stock);
                goods.setIs_delete(is_delete);

                list.add(goods);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from goods  where is_delete=0";

        StringBuilder sb = new StringBuilder(sql);
        //2.遍历map
        Set<String> keySet = condition.keySet();
        //定义参数的集合
        List<Object> params = new ArrayList<Object>();
        for (String key : keySet) {

            //排除分页条件参数
            if("currentPage".equals(key) || "rows".equals(key) ||"method".equals(key)){
                continue;
            }

            //获取value
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //有值
                sb.append(" and "+key+" like ? ");
                params.add("%"+value+"%");//？条件的值
            }
        }

        //添加分页查询
        sb.append(" limit ?,? ");
        //添加分页查询参数值
        params.add(start);
        params.add(rows);
        sql = sb.toString();
        System.out.println(sql);
        System.out.println(params);


        List<Map<String, Object>> datas = new ArrayList<>();
        try {
            datas = DBUtils.executeQuery(sql,params.toArray());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datas;
    }
}

