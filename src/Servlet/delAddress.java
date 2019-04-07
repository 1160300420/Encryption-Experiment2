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

@WebServlet("/delAddress")
public class delAddress extends HttpServlet{
private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public delAddress() {
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
      String userId=request.getParameter("userid");
      String ID = request.getParameter("name"); 
      try {
        userId=HttpEncryptUtil.serverDecrypt(userId);
        ID=HttpEncryptUtil.serverDecrypt(ID);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      boolean type=false;
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      try
      {
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          String sql="delete from demodatabase.addresslist where addressId='"+ID+"' and userId='"+userId+"'";
          stmt.execute(sql);
          type=true;
          out.print(type);
          adminmain.logger.info("用户"+userId+"删除地址"+ID);
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      finally
      {
          DBUtil.Close();
          System.out.println(type);
          out.flush();
          out.close();
      }
  }
}
