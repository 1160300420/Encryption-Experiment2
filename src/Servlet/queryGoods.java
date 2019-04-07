package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import DBTool.DBUtil;

@WebServlet("/queryGoods")
public class queryGoods extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public queryGoods() {
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
      String curpage = request.getParameter("ID"); 
      String pagesize= request.getParameter("PW");
      int ps=Integer.parseInt(pagesize);
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      try
      {
          Connection con=DBUtil.getConnection();
          Statement stmt=con.createStatement();
          String sql="select * from demodatabase.goodlist where currentPage="+curpage;
          ResultSet rs=stmt.executeQuery(sql);
          int count=0;
          while(rs.next()&&count<ps)
          {
              count++;
              
              out.print(rs.getInt("id")+"\n");
              out.print(rs.getInt("categoryId")+"\n");
              out.print(rs.getInt("campaignId")+"\n");
              out.print(rs.getString("name")+"\n");
              out.print(rs.getString("imgUrl")+"\n");
              out.print(rs.getDouble("price")+"\n");
              out.print(rs.getDouble("sale")+"\n");
          }
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
