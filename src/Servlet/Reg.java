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

import com.sun.istack.internal.logging.Logger;

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import sun.util.logging.resources.logging;
@WebServlet("/Reg")
public class Reg extends HttpServlet{
  private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public Reg() {
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
      boolean type=false;
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      try
      {
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          String sql="select * from demodatabase.usertable where uid="+ID;
          ResultSet rs=stmt.executeQuery(sql);//rs为返回的查询返回的结果不为空，从第二个元素开始为查询到的数据，否则长度为一
          while(rs.next())
          {
              type=true;
          }
          adminmain.logger.info("用户"+ID+"申请注册");
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      finally
      {
          DBUtil.Close();
          System.out.println(type);
          out.print(type);
          out.flush();
          out.close();
      }
  }
}
