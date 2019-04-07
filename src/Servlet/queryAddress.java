package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.NEW;

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import encrypt.HttpEncryptUtil;
import encrypt.KeyUtil;
import encrypt.RSAUtil;
import encrypt.RSAUtils;
@WebServlet("/queryAddress")
public class queryAddress extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public queryAddress() {
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
    String ID = request.getParameter("name");
    String outstr = new String();
    try {
      ID=HttpEncryptUtil.serverDecrypt(ID);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    PrintWriter out = response.getWriter();
    boolean type = false;
    try {
      response.setContentType("text/html; charset=UTF-8");
      Connection con = DBUtil.getConnection();
      Statement stmt = con.createStatement();
      String sql = "select * from demodatabase.addresslist where userId=" + ID;
      ResultSet rs = stmt.executeQuery(sql);//rs为返回的查询返回的结果不为空，从第二个元素开始为查询到的数据，否则长度为一
      while (rs.next()) {
        outstr=outstr+rs.getString("userId") + ","+rs.getInt("addressId") + ","+rs.getString("name") + ","+rs.getString("phone") + ","+rs.getString("bigAddress") + ","+rs.getString("smallAddress") + ","+rs.getString("address") + ";";
        System.out.println("==========="+outstr);
      }
      adminmain.logger.info("用户"+ID+"获取收货地址列表");
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      DBUtil.Close();
      System.out.println(type);
      try {
        out.print(HttpEncryptUtil.serverEncrypt(HttpEncryptUtil.serverDecrypt_apppub(request.getParameter("name")),HttpEncryptUtil.serverDecrypt_aeskey(request.getParameter("name")),outstr));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      out.flush();
      out.close();
      /*try {
        byte[] address=RSAUtils.decryptData((HttpEncryptUtil.serverEncrypt(HttpEncryptUtil.serverDecrypt_apppub(request.getParameter("name")),HttpEncryptUtil.serverDecrypt_aeskey(request.getParameter("name")),outstr)).getBytes(), RSAUtil.string2PrivateKey(KeyUtil.app_private_key));
        System.out.println(new String(address));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }*/
    }
  }
}
