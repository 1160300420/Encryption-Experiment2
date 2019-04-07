package Servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import encrypt.Base64Utils;
import encrypt.HttpEncryptUtil;
import encrypt.KeyUtil;
import encrypt.RSAUtil;
import encrypt.RSAUtils;
import net.sf.json.JSONObject;
@WebServlet("/PayRequest")
public class PayRequest extends HttpServlet {
  private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public PayRequest() {
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
    System.out.println("经过RSA加密的PW：" + ID);
    String urlStr = "http://172.20.10.3:8080/JavaWeb/request";
    String string=null;
    try {
      ID = HttpEncryptUtil.serverDecrypt(ID);
      System.out.println("经过RSA解密的PW：" + ID);
      String[] temp = ID.split("//");
      System.out.println("1:"+temp[1]);
      String contrast1 = HttpEncryptUtil.decryprtByapppublic1(KeyUtil.APP_PUBLIC_KEY, temp[0]);
      System.out.println("====="+contrast1);
     /* JSONObject jsonObject=JSONObject.fromObject(ID);
      string =(String)jsonObject.get("content");*/
      } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    String str1="";
    String ID1=null;
    try {
      try {
        ID1=HttpEncryptUtil.encryptBybankpublic(ID);
        System.out.println(ID1);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      URL url = new URL(urlStr);//由输入的参数设置url
      HttpURLConnection con = (HttpURLConnection) url.openConnection();//准备连接类
      HttpURLConnection.setFollowRedirects(true);
      con.setRequestMethod("POST"); //设置为Get方法
      con.setConnectTimeout(8000);
      con.setReadTimeout(8000);
      con.setUseCaches(false);
      con.setDoOutput(true);
      String data="name="+ID1;
      System.out.println("========"+data);
      OutputStream outputStream=con.getOutputStream();
      outputStream.write(data.getBytes());
      outputStream.flush();
      outputStream.close();
      con.connect();
      if (HttpURLConnection.HTTP_OK == con.getResponseCode()) {//查看返回值
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));//获取返回值
        str1 = in.readLine();
        in.close();
      }
      else {
        System.out.println("failed");
      }
      //if(str1.equals("ok")) {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        System.out.println(str1);
        out.print(str1);
        out.flush();
        out.close();
     // }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
