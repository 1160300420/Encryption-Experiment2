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
@WebServlet("/delshopcart")
public class delshopcart extends HttpServlet{
private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public delshopcart() {
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
      String userid=request.getParameter("id");
      try {
        ID=HttpEncryptUtil.serverDecrypt(ID);
        userid=HttpEncryptUtil.serverDecrypt(userid);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      String[] recid=ID.split(",");
      boolean type=false;
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      try
      {
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          for(int i=0;i<recid.length;i++) {
          String sql="delete from demodatabase.shopcartlist where id='"+recid[i]+"' and userId='"+userid+"'";
          stmt.execute(sql);
          adminmain.logger.info("用户"+userid+"删除购物车商品"+recid[i]);
          }
          type=true;
          out.print(type);
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
