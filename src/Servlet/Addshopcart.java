package Servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import encrypt.HttpEncryptUtil;
import encrypt.MD5Util;

@WebServlet("/Addshopcart")
public class Addshopcart extends HttpServlet {

  /**
   * 向数据库中添加用户
   */
  private static final long serialVersionUID = 1L;
  public Addshopcart() {
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
 * @throws IOException 
 * @throws Exception 
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
    String ID = request.getParameter("name"); 
    String userID=request.getParameter("userid");
    try {
      ID=HttpEncryptUtil.serverDecrypt(ID);
      userID=HttpEncryptUtil.serverDecrypt(userID);
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
        String sql="select * from demodatabase.goodlist where id='"+ID+"'";
        ResultSet rSet=stmt.executeQuery(sql);
        while (rSet.next()) {
        String sql1="insert into demodatabase.shopcartlist values('"+rSet.getInt("currentPage")+"','"+rSet.getInt("id")+"','"+rSet.getInt("categoryId")+"','"+rSet.getInt("campaignId")+"','"+rSet.getString("name")+"','"+rSet.getString("imgUrl")+"','"+rSet.getDouble("price")+"','"+rSet.getDouble("sale")+"','"+userID+"')";
        Statement statement=con.createStatement();
        statement.execute(sql1);
        type=true;
        adminmain.logger.info("用户"+userID+"添加购物车商品"+ID);
        }
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
