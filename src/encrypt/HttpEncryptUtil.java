package encrypt;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;


import com.sun.org.apache.bcel.internal.generic.NEW;

import net.sf.json.JSONObject;
import net.sf.json.util.NewBeanInstanceStrategy;

public class HttpEncryptUtil {
//服务器加密响应给APP的内容
  public static String serverEncrypt(String appPublicKeyStr, String aesKeyStr, String content) throws Exception{
      //将Base64编码后的APP公钥转换成PublicKey对象
      PublicKey appPublicKey = RSAUtil.string2PublicKey(appPublicKeyStr);
      //将Base64编码后的AES秘钥转换成SecretKey对象
      SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
      //用APP公钥加密AES秘钥
      byte[] encryptAesKey = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), appPublicKey);
      //用AES秘钥加密响应内容
      byte[] encryptContent = AESUtil.encryptAES(content.getBytes("UTF-8"), aesKey);
      
      JSONObject result = new JSONObject();
      result.put("ak", new String(RSAUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", "").getBytes(),"UTF-8"));
      result.put("ct", new String(RSAUtil.byte2Base64(encryptContent).replaceAll("\r\n", "").getBytes(),"UTF-8"));
      
      return result.toString();
  }
//APP解密服务器的响应内容
  public static String appDecrypt(String appPrivateKeyStr, String content) throws Exception{
      JSONObject result = JSONObject.fromObject(content);
      String encryptAesKeyStr = (String) result.get("ak");
      String encryptContent = (String) result.get("ct");

      //将Base64编码后的APP私钥转换成PrivateKey对象
      PrivateKey appPrivateKey = RSAUtil.string2PrivateKey(appPrivateKeyStr);
      //用APP私钥解密AES秘钥
      byte[] aesKeyBytes = RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptAesKeyStr), appPrivateKey);
      //用AES秘钥解密请求内容
      SecretKey aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes));
      byte[] response = AESUtil.decryptAES(RSAUtil.base642Byte(encryptContent), aesKey);
      JSONObject result2 = new JSONObject();
      //!!!注意汉字转码
      result2.put("ak", new String(aesKeyBytes,"UTF-8"));
      //result2.put("ct", new String(request));
      result2.put("ct", new String(response,"UTF-8"));
      return result2.get("ct").toString();
  }
  
  //服务器用server私钥解密APP的请求内容
  public static String serverDecrypt(String content) throws Exception{
      JSONObject result = JSONObject.fromObject(content);
      String encryptAesKeyStr = (String) result.get("ak");
      String encryptAppPublicKeyStr = (String) result.get("apk");
      String encryptContent = (String) result.get("ct");
      
      //将Base64编码后的Server私钥转换成PrivateKey对象
      PrivateKey serverPrivateKey = RSAUtil.string2PrivateKey(KeyUtil.SERVER_PRIVATE_KEY);
      //用Server私钥解密AES秘钥
      byte[] aesKeyBytes = RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptAesKeyStr), serverPrivateKey);
      //用AES秘钥解密APP公钥
      SecretKey aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes));
      byte[] appPublicKeyBytes = AESUtil.decryptAES(RSAUtil.base642Byte(encryptAppPublicKeyStr), aesKey);
      //用AES秘钥解密请求内容
      byte[] request = AESUtil.decryptAES(RSAUtil.base642Byte(encryptContent), aesKey);
      JSONObject result2 = new JSONObject();
      //!!!注意汉字转码
      result2.put("ak", new String(aesKeyBytes,"UTF-8"));
      result2.put("apk", new String(appPublicKeyBytes,"UTF-8"));
      //result2.put("ct", new String(request));
      result2.put("ct", new String(request,"UTF-8"));
      return result2.get("ct").toString();
  }
  //服务器用APP公钥解密APP请求内容
  public static String serDecryptByapp(String content) throws Exception {
    JSONObject result = JSONObject.fromObject(content);
    String co=(String)result.get("content");
    PublicKey appPublicKey=RSAUtil.string2PublicKey(KeyUtil.APP_PUBLIC_KEY);
    byte[] ca=RSAUtil.publicDecrypt(RSAUtil.base642Byte(co), appPublicKey);
    JSONObject result2 = new JSONObject();
    //!!!注意汉字转码
    result2.put("ak", new String(ca,"UTF-8"));
    return result2.getString("ak").toString();
  }
  //服务器解密APP的公钥
  public static String serverDecrypt_apppub(String content) throws Exception{
      JSONObject result = JSONObject.fromObject(content);
      String encryptAesKeyStr = (String) result.get("ak");
      String encryptAppPublicKeyStr = (String) result.get("apk");
      String encryptContent = (String) result.get("ct");
      
      //将Base64编码后的Server私钥转换成PrivateKey对象
      PrivateKey serverPrivateKey = RSAUtil.string2PrivateKey(KeyUtil.SERVER_PRIVATE_KEY);
      //用Server私钥解密AES秘钥
      byte[] aesKeyBytes = RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptAesKeyStr), serverPrivateKey);
      //用AES秘钥解密APP公钥
      SecretKey aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes));
      byte[] appPublicKeyBytes = AESUtil.decryptAES(RSAUtil.base642Byte(encryptAppPublicKeyStr), aesKey);
      //用AES秘钥解密请求内容
      byte[] request = AESUtil.decryptAES(RSAUtil.base642Byte(encryptContent), aesKey);
      JSONObject result2 = new JSONObject();
      //!!!注意汉字转码
      result2.put("ak", new String(aesKeyBytes,"UTF-8"));
      result2.put("apk", new String(appPublicKeyBytes,"UTF-8"));
      //result2.put("ct", new String(request));
      result2.put("ct", new String(request,"UTF-8"));
      return result2.get("apk").toString();
  }
//服务器解密AES的key
  public static String serverDecrypt_aeskey(String content) throws Exception{
      JSONObject result = JSONObject.fromObject(content);
      String encryptAesKeyStr = (String) result.get("ak");
      String encryptAppPublicKeyStr = (String) result.get("apk");
      String encryptContent = (String) result.get("ct");
      
      //将Base64编码后的Server私钥转换成PrivateKey对象
      PrivateKey serverPrivateKey = RSAUtil.string2PrivateKey(KeyUtil.SERVER_PRIVATE_KEY);
      //用Server私钥解密AES秘钥
      byte[] aesKeyBytes = RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptAesKeyStr), serverPrivateKey);
      //用AES秘钥解密APP公钥
      SecretKey aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes));
      byte[] appPublicKeyBytes = AESUtil.decryptAES(RSAUtil.base642Byte(encryptAppPublicKeyStr), aesKey);
      //用AES秘钥解密请求内容
      byte[] request = AESUtil.decryptAES(RSAUtil.base642Byte(encryptContent), aesKey);
      JSONObject result2 = new JSONObject();
      //!!!注意汉字转码
      result2.put("ak", new String(aesKeyBytes,"UTF-8"));
      result2.put("apk", new String(appPublicKeyBytes,"UTF-8"));
      //result2.put("ct", new String(request));
      result2.put("ct", new String(request,"UTF-8"));
      return result2.get("ak").toString();
  }
  //用app的公钥解密
  public static String decryptByapppublic(String appPublicKeyStr,String str) throws  Exception{
      JSONObject result = JSONObject.fromObject(str);
      String encryptContent=(String)result.get("content");
      PublicKey appPublickey=RSAUtil.string2PublicKey(appPublicKeyStr);
      byte[] decryptcontent=RSAUtil.publicDecrypt(RSAUtil.base642Byte(str),appPublickey);
      JSONObject result2=new JSONObject();
      result2.put("content",new String(decryptcontent,"UTF-8"));
      return result2.get("content").toString();
  }
  public static String decryprtByapppublic1(String appPublicKeyStr,String str) throws Exception {
    /*JSONObject result = JSONObject.fromObject(str);
    String encryptContent=(String)result.get("content");*/
    PublicKey appPublickey=RSAUtil.string2PublicKey(appPublicKeyStr);
    byte[] decryptcontent=RSAUtil.publicDecrypt(RSAUtil.base642Byte(str),appPublickey);
    JSONObject result2=new JSONObject();
    result2.put("content",new String(decryptcontent,"UTF-8"));
    return result2.get("content").toString();
  }
  //用app的私钥加密
  public static String encryptByappprivate(String str) throws Exception {
      //将Base64编码后的Server公钥转换成PublicKey对象
      PrivateKey apppivateKey = RSAUtil.string2PrivateKey(KeyUtil.SERVER_PRIVATE_KEY);
      //用app私钥加密
      byte[] encryptAesKey = RSAUtil.privateEncrypt(str.getBytes(), apppivateKey);
      JSONObject result = new JSONObject();
      result.put("content", RSAUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", ""));
      return result.toString();
  }
  //用app的私钥解密
  public static String decryptByappprivate(String appPrivateKeystr,String str) throws  Exception{
      JSONObject result = JSONObject.fromObject(str);
      String encryptContent=(String)result.get("content");
      PrivateKey appPrivateKey=RSAUtil.string2PrivateKey(appPrivateKeystr);
      byte[] decryptcontent=RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptContent),appPrivateKey);
      JSONObject result2=new JSONObject();
      result2.put("content",new String(decryptcontent,"UTF-8"));
      return result2.toString();
  }
  //用app的公钥加密
  public static String encryptByapppublic(String str) throws Exception {
      //将Base64编码后的Server公钥转换成PublicKey对象
      PublicKey publicKey = RSAUtil.string2PublicKey(KeyUtil.APP_PUBLIC_KEY);
      //用app私钥加密
      byte[] encryptAesKey = RSAUtil.publicEncrypt(str.getBytes("UTF-8"), publicKey);
      JSONObject result = new JSONObject();
      result.put("content", RSAUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", ""));
      return result.toString();
  }
//用BANK的公钥加密
  public static String encryptBybankpublic( String content) throws Exception {
    //将Base64编码后的Server公钥转换成PublicKey对象
    PublicKey bankPublicKey = RSAUtil.string2PublicKey(KeyUtil.BANK_PUBLIC_KEY);
    //每次都随机生成AES秘钥
    String aesKeyStr = AESUtil.genKeyAES();
    SecretKey aesKey = AESUtil.loadKeyAES(aesKeyStr);
    //用Server公钥加密AES秘钥
    byte[] encryptAesKey = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), bankPublicKey);
    //用AES秘钥加密请求内容
    //byte[] temp=content.getBytes("utf-8");
    byte[] encryptRequest = AESUtil.encryptAES(content.getBytes(), aesKey);

    JSONObject result = new JSONObject();
    result.put("ak", RSAUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", ""));
    result.put("ct", RSAUtil.byte2Base64(encryptRequest).replaceAll("\r\n", ""));
    return result.toString();
  }
}
