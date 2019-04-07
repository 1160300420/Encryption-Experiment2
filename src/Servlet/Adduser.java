package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.cj.protocol.Message;
import org.apache.shiro.crypto.hash.Sha256Hash;

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import encrypt.HttpEncryptUtil;
import encrypt.MD5Util;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
@WebServlet("/Adduser")
public class Adduser extends HttpServlet {

  /**
   * 向数据库中添加用户
   */
  private static final long serialVersionUID = 1L;
  public Adduser() {
    super();
    // TODO Auto-generated constructor stub
}

/**
 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // TODO Auto-generated method stub
    response.getWriter().append("Served at: ").append(request.getContextPath());
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    String ID = request.getParameter("ID"); 
    ID=keys.decryptBASE64(ID);
    String PW= request.getParameter("PW");
    String salt=UUID.randomUUID().toString();
    try {
      PW=HttpEncryptUtil.serverDecrypt(PW);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    //String PW_e=new Sha256Hash(PW,salt).toString();
    boolean type=false;
    response.setContentType("text/html; charset=UTF-8");
    PrintWriter out = response.getWriter();
    try
    {
        Connection con=DBUtil.getConnection();
        Statement stmt=con.createStatement();
        String sql1="insert into demodatabase.encryptsalt values('"+ID+"','"+salt+"')";
        String sql="insert into demodatabase.usertable values('"+ID+"','"+MD5Util.encodebyMD5(PW+salt)+"')";
        stmt.execute(sql);
        Statement statement=con.createStatement();
        statement.execute(sql1);
        type=true;
        adminmain.logger.info("添加用户"+ID);
    }
    catch(Exception ex)
    {
        ex.printStackTrace();
    }
    finally
    {
        DBUtil.Close();
        out.print(type);
        out.flush();
        out.close();
    }
}
  
}
