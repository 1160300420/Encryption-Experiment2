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

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import encrypt.HttpEncryptUtil;

@WebServlet("/queryShopcart")
public class queryShopcart extends HttpServlet {
private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public queryShopcart() {
    // TODO Auto-generated constructor stub
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
      String user_id = request.getParameter("ID"); 
      try {
        user_id=HttpEncryptUtil.serverDecrypt(user_id);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      try
      {
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          String sql="select * from demodatabase.shopcartlist where userId='"+user_id+"'";
          ResultSet rs=stmt.executeQuery(sql);
          while(rs.next())
          {
              out.print(rs.getInt("id")+"\n");
              out.print(rs.getInt("categoryId")+"\n");
              out.print(rs.getInt("campaignId")+"\n");
              out.print(rs.getString("name")+"\n");
              out.print(rs.getString("imgUrl")+"\n");
              out.print(rs.getDouble("price")+"\n");
              out.print(rs.getDouble("sale")+"\n");
          }
          adminmain.logger.info("用户"+user_id+"查询购物车列表");
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      finally
      {
          DBUtil.Close();
          out.flush();
          out.close();
      }
  }
}
