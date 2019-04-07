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

@WebServlet("/queryOrder")
public class queryOrder extends HttpServlet {
private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public queryOrder() {
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
      String id_order = request.getParameter("name"); 
      try {
        id_order=HttpEncryptUtil.serverDecrypt(id_order);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      String outstr=new String();
      try
      {
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          String sql="select * from demodatabase.orderlist where userId="+id_order;
          ResultSet rs=stmt.executeQuery(sql);
          int count=0;
          while(rs.next())
          {
              outstr=outstr+rs.getString("ordergoods")+";";
          }
          adminmain.logger.info("用户"+id_order+"查询订单");
      }
      catch(Exception ex)
      {
          ex.printStackTrace();
      }
      finally
      {
          DBUtil.Close();
          try {
            out.println(HttpEncryptUtil.serverEncrypt(HttpEncryptUtil.serverDecrypt_apppub(request.getParameter("name")), HttpEncryptUtil.serverDecrypt_aeskey(request.getParameter("name")), outstr));
          } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
          out.flush();
          out.close();
      }
  }
}
