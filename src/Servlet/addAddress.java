package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import encrypt.HttpEncryptUtil;
@WebServlet("/addAddress")
public class addAddress extends HttpServlet {
private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public addAddress() {
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
      request.setCharacterEncoding("UTF-8");
      boolean type=false;
      PrintWriter out = response.getWriter();
      try {
      int addressId = Integer.parseInt(HttpEncryptUtil.serverDecrypt(request.getParameter("addressId")));
      String userId= HttpEncryptUtil.serverDecrypt(request.getParameter("userId"));
      String name=HttpEncryptUtil.serverDecrypt(request.getParameter("name"));
      String phone=HttpEncryptUtil.serverDecrypt(request.getParameter("phone"));
      String bigAddress=HttpEncryptUtil.serverDecrypt(request.getParameter("bigAddress"));
      String smallAddress=HttpEncryptUtil.serverDecrypt(request.getParameter("smallAddress"));
      String address=HttpEncryptUtil.serverDecrypt(request.getParameter("address"));
      response.setContentType("text/html; charset=UTF-8");
      System.out.println(userId+"\n"+addressId+"\n"+name+"\n"+phone+"\n"+bigAddress+"\n"+smallAddress+"\n"+address);
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          String sql="insert into demodatabase.addresslist values('"+userId+"',"+addressId+",'"+name+"','"+phone+"','"+bigAddress+"','"+smallAddress+"','"+address+"')";
          stmt.execute(sql);
          type=true;
          out.print(type);
          adminmain.logger.info("用户"+userId+"添加地址"+address);
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      finally
      {
          DBUtil.Close();
          System.out.println(type+"---------");
          out.flush();
          out.close();
      }
  }
}
