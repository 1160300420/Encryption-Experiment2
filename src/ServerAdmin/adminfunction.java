package ServerAdmin;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import sun.net.www.http.HttpClient;


public class adminfunction{
  /**
   * 修改用户名
   */
  public void modify_user(Connection conn, String oriid, String newid) throws SQLException {
    // TODO Auto-generated method stub
    String sql_modify = "update usertable set uid='" + newid + "' where uid='" + oriid + "' ";
    Statement modify_query = conn.createStatement();
    modify_query.execute(sql_modify);
    modify_query.close();
  }
  /**
   * 修改商品数量
   * @throws SQLException 
   */
  public void modify_goods(Connection conn,String good_id,String newmount) throws SQLException {
    String sql_modify = "update goodlist set sale='" + newmount + "' where id='" + good_id+ "' ";
    Statement modify_query = conn.createStatement();
    modify_query.execute(sql_modify);
    modify_query.close();
  }
  /**
   * 修改订单内容
   * @throws SQLException 
   */
  public void modify_order(Connection conn,String userid,String details) throws SQLException {
    String sql_modify = "update orderlist set ordergoods='" + details + "' where userId='" + userid + "' ";
    Statement modify_query = conn.createStatement();
    modify_query.execute(sql_modify);
    modify_query.close();
  }
  /**
      * 删除用户
   */
  public void delete(Connection conn, QueryItem id) throws SQLException {
    // TODO Auto-generated method stub
    String sql = "delete from usertable where uid = '" + id.getText() + "'";
    Statement statement = conn.createStatement();
    statement.execute(sql);
    statement.close();
  }
  /**
   * 添加用户
   */
  public void adduser(Connection conn, String id_reg, String pw_reg) {
    // TODO Auto-generated method stub
    String Regsql = "select * from usertable where uid ='" + id_reg + "'";
    boolean has_Reg = false;
    Statement stmt;
    try {
      stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(Regsql);
      while (rs.next()) {
        has_Reg = true;
      }
      stmt.close();
    } catch (SQLException e1) {
      e1.printStackTrace();
    }
    if (!has_Reg) {
      String Regsql_cer = "insert into usertable values('" + id_reg + "','" + pw_reg + "')";
      try {
        Statement stmt_cer = conn.createStatement();
        stmt_cer.execute(Regsql_cer);
        stmt_cer.close();
      } catch (SQLException e1) {
        // TODO Auto-generated catch block
        e1.printStackTrace();
      }
    }
  }
  /**
   * 添加订单
   */
  public void addorder(Connection conn, String id_reg, String pw_reg) {
    // TODO Auto-generated method stub
    String Regsql_cer = "insert into demotable.orderlist values('" + id_reg + "','" + pw_reg + "')";
    try {
      Statement stmt_cer = conn.createStatement();
      stmt_cer.execute(Regsql_cer);
      stmt_cer.close();
    } catch (SQLException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
  }
  /**
   * 查询用户列表
   * @throws SQLException 
   */
  public void query_user(Connection conn, String id, Vector<Vector<String>> dataModel) throws SQLException {
    if (id.equals("")) {
      //查询所有
      Statement smtm_query1 = conn.createStatement();
      Vector<String> record = null;
      ResultSet rs_admin = smtm_query1.executeQuery("select * from usertable");
      while (rs_admin.next()) {
        record = new Vector<String>();
        record.add(rs_admin.getString(1));
        record.add(rs_admin.getString(1));
        record.add("xxxxxx");
        dataModel.add(record);
      }
      rs_admin.close();
      smtm_query1.close();
    } else {
      //查询该用户
      Statement smtm_query1 = conn.createStatement();
      Vector<String> record = null;
      ResultSet rs_admin = smtm_query1.executeQuery("select * from usertable where uid='" + id + "'");
      while (rs_admin.next()) {
        record = new Vector<String>();
        record.add(rs_admin.getString(1));
        record.add(rs_admin.getString(1));
        record.add("xxxxxx");
        dataModel.add(record);
      }
      rs_admin.close();
      smtm_query1.close();
    }
  }
  /**
   * 查询商品列表
   * @throws SQLException 
   */
  public void query_goods(Connection conn, String id, Vector<Vector<String>> dataModel) throws SQLException {
    if (id.equals("")) {
      //查询所有
      Statement smtm_query1 = conn.createStatement();
      Vector<String> record = null;
      ResultSet rs_admin = smtm_query1.executeQuery("select * from goodlist");
      while (rs_admin.next()) {
        record = new Vector<String>();
        record.add(rs_admin.getInt(2) + "");
        record.add(rs_admin.getString(5));
        record.add("price:" + rs_admin.getDouble(7) + "sale:" + rs_admin.getDouble(8));
        dataModel.add(record);
      }
      rs_admin.close();
      smtm_query1.close();
    } else {
      //查询该商品
      Statement smtm_query1 = conn.createStatement();
      Vector<String> record = null;
      ResultSet rs_admin = smtm_query1.executeQuery("select * from goodlist where id='" + id + "'");
      while (rs_admin.next()) {
        record = new Vector<String>();
        record.add(rs_admin.getInt(2) + "");
        record.add(rs_admin.getString(5));
        record.add("price:" + rs_admin.getDouble(7) + "sale:" + rs_admin.getDouble(8));
        dataModel.add(record);
      }
      rs_admin.close();
      smtm_query1.close();
    }
  }
  /**
      * 查询订单
   */
  public void query_order(Connection conn, String id, Vector<Vector<String>> dataModel) throws SQLException {
    if (id.equals("")) {
      //查询所有
      Statement smtm_query1 = conn.createStatement();
      Vector<String> record = null;
      ResultSet rs_admin = smtm_query1.executeQuery("select * from orderlist");
      int i = 1;
      while (rs_admin.next()) {
        record = new Vector<String>();
        record.add("" + i);
        record.add(rs_admin.getString(1));
        record.add(rs_admin.getString(2));
        i++;
        dataModel.add(record);
      }
      rs_admin.close();
      smtm_query1.close();
    } else {
      //查询该用户的订单
      Statement smtm_query1 = conn.createStatement();
      Vector<String> record = null;
      ResultSet rs_admin = smtm_query1.executeQuery("select * from orderlist where userId='" + id + "'");
      int i = 1;
      while (rs_admin.next()) {
        record = new Vector<String>();
        record.add("" + i);
        record.add(rs_admin.getString(1));
        record.add(rs_admin.getString(2));
        i++;
        dataModel.add(record);
      }
      rs_admin.close();
      smtm_query1.close();
    }
  }

}
