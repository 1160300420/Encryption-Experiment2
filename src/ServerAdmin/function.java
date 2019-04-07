package ServerAdmin;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JTextArea;

public interface  function {
  /**
   * 修改： 普通用户修改自己的密码，管理员修改任意普通用户密码及余额
   * @throws SQLException 
   */
  public void modify(Connection conn,QueryItem id_op,QueryItem pw_op,QueryItem bar_op,QueryItem pw,QueryItem id) throws SQLException;
  /**
   * 添加：用户为注册，管理员为添加
   */
  public void adduser(Connection conn, String id_reg, String pw);
  /**
   * 查询：普通用户查询自己的余额，管理员查询各个用户的列表
   * @throws SQLException 
   */
  public void query( Vector<Vector<String>> dataModel,Connection conn,String id_query,String password,Float balance_query) throws SQLException;
  /**
   * 删除：普通用户注销账号，管理员对选中的用户进行删除
   * @throws SQLException 
   */
  public void delete(Connection conn,QueryItem id) throws SQLException;

}

