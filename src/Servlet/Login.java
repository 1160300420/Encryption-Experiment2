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
import encrypt.MD5Util;


/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
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
        System.out.println("未经过base64解密的ID："+ID);
        ID=keys.decryptBASE64(ID);
        System.out.println("经过base64解密的ID："+ID);
        String PW= request.getParameter("PW");
        System.out.println("经过RSA加密的PW："+PW);
        try {
          PW=HttpEncryptUtil.serverDecrypt(PW);
          System.out.println("经过RSA解密的PW："+PW);
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
            Statement stmt1=con.createStatement();
            String sql1="select * from demodatabase.encryptsalt where userId='"+ID+"'";
            ResultSet rs1=stmt1.executeQuery(sql1);
            String salt="";
            while(rs1.next()) {
              salt=rs1.getString(2);
            }
            String sql="select * from demodatabase.usertable where uid='"+ID+"' and pwd='"+MD5Util.encodebyMD5(PW+salt)+"'";
            ResultSet rs=stmt.executeQuery(sql);
            while(rs.next())
            {
                type=true;
                adminmain.logger.info("用户"+ID+"已成功登陆");
            }
        }
        catch(Exception ex)
        {
            adminmain.logger.info("用户"+ID+"登陆失败");
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

