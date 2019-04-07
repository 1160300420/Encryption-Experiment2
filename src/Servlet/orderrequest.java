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

import com.sun.xml.internal.messaging.saaj.util.Base64;

import DBTool.DBUtil;
import ServerAdmin.adminmain;
import encrypt.HttpEncryptUtil;
import encrypt.KeyUtil;
import encrypt.MD5Util;
import encrypt.RSAUtil;
import encrypt.RSAUtils;
@WebServlet("/orderrequest")
public class orderrequest extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public orderrequest() {
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
    String PW = request.getParameter("name");
    System.out.println("经过RSA加密的PW：" + PW);
    try {
      PW = HttpEncryptUtil.serverDecrypt(PW);
      System.out.println("经过RSA解密的PW：" + PW);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String type = "no";
    String[] temp = PW.split("//");
    String contrast = MD5Util.encodebyMD5(temp[2] + MD5Util.encodebyMD5(temp[1]));
    String contrast1 = null;
    try {
      /*byte[] decrya=RSAUtils.decryptData(RSAUtil.base642Byte(temp[0]), RSAUtil.string2PublicKey(KeyUtil.APP_PUBLIC_KEY));//RSAUtil.privateDecrypt(encrya, RSAUtil.string2PrivateKey(KeyUtil.APP_PRIVATE_KEY));
      contrast1=new String(decrya);*/
      contrast1 = HttpEncryptUtil.decryprtByapppublic1(KeyUtil.APP_PUBLIC_KEY, temp[0]);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    if (contrast.equals(contrast1)) {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      String order_list="";
      try {
        String[] oi = temp[1].split(";");
        String[] oii = oi[1].split("%");//得到本次交易的商品列表，形如id，count
        Connection con = DBUtil.getConnection();
        Statement stmt = con.createStatement();
        Statement stmt1 = con.createStatement();
        for (int count = 0; count < oii.length; count++) {
          String[] oiii=oii[count].split(",");
          String sql1 = "select * from demodatabase.goodlist where id='" + oiii[0] + "'";
          ResultSet rs1 = stmt1.executeQuery(sql1);
          while (rs1.next()) {
            if(rs1.getDouble(8)>Integer.parseInt(oiii[1])) {
            type = "ok";
            order_list=order_list+ rs1.getString(5);
            System.out.println("success");
            }
          }
        }
      //向数据库添加订单
        String strstr = "insert into demodatabase.orderlist values('" + oi[0] + "','" +order_list + "')";
        stmt.execute(strstr);
        adminmain.logger.info("用户" + oi[0] + "添加订单" + order_list);
      } catch (Exception ex) {
        ex.printStackTrace();
      } finally {
        DBUtil.Close();
        try {
          out.print(type);
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
        out.flush();
        out.close();
      }
    } else {
      System.out.println("contrast" + contrast);
      try {
        //System.out.println(HttpEncryptUtil.decryptByapppublic(KeyUtil.APP_PUBLIC_KEY,temp[0]));
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }
}
