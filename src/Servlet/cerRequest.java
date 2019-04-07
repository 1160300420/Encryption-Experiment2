package Servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.corba.se.spi.presentation.rmi.IDLNameTranslator;

import encrypt.HTTPTool;
import encrypt.HttpEncryptUtil;
import encrypt.KeyUtil;
import encrypt.RSAUtil;
import sun.misc.BASE64Decoder;
@WebServlet("/cerRequest")
public class cerRequest extends HttpServlet{
 private static final long serialVersionUID = 1L;
  
  /**
   * @see HttpServlet#HttpServlet()
   */
  public cerRequest() {
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
      String id=request.getParameter("name");
      try {
        id=HttpEncryptUtil.serverDecrypt(id);
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
     boolean isTrue=false;
      HTTPTool httpTool=new HTTPTool("http://172.20.10.4:8080/ca/connectServlet?name="+id);
      String str=httpTool.getresult();
      BASE64Decoder base64Decoder=new BASE64Decoder();
      byte[] bytes=base64Decoder.decodeBuffer(str);

      CertificateFactory cf = null;
      try {
        cf = CertificateFactory.getInstance("X.509");
      } catch (CertificateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      X509Certificate cert = null;
      try {
        cert = (X509Certificate)cf.generateCertificate(new ByteArrayInputStream(bytes));
      } catch (CertificateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      //PublicKey publicKey=cert.getPublicKey();
      System.out.println(cert);
      try {
        cert.verify(RSAUtil.string2PublicKey(KeyUtil.CA_PUBLIC_KEY));
      } catch (InvalidKeyException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (CertificateException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NoSuchAlgorithmException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (NoSuchProviderException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (SignatureException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(str);
        out.flush();
        out.close();
  
  }
}
