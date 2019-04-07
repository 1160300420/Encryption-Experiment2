 
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;

import org.apache.tomcat.util.codec.binary.Base64;
 
public class RSATest {
    // 数字签名，密钥算法
    private static final String RSA_KEY_ALGORITHM = "RSA";
 
    // 数字签名签名/验证算法
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
 
    // RSA密钥长度
    private static final int KEY_SIZE = 1024;
    
    private static final String PUBLIC_KEY = "publicKey";
    private static final String PRIVATE_KEY = "privateKey";
 
    /**
     * 初始化RSA密钥对
     * @return RSA密钥对
     * @throws Exception 抛出异常
     */
    private static Map<String, String> initKey() throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator
                .getInstance(RSA_KEY_ALGORITHM);
        SecureRandom secrand = new SecureRandom();
        secrand.setSeed("hahaha".getBytes());// 初始化随机产生器
        keygen.initialize(KEY_SIZE, secrand); // 初始化密钥生成器
        KeyPair keys = keygen.genKeyPair();
        String pub_key = Base64.encodeBase64String(keys.getPublic().getEncoded());
        String pri_key = Base64.encodeBase64String(keys.getPrivate().getEncoded());
        Map<String, String> keyMap = new HashMap<String, String>();
        keyMap.put(PUBLIC_KEY, pub_key);
        keyMap.put(PRIVATE_KEY, pri_key);
        System.out.println("公钥：" + pub_key);
        System.out.println("私钥：" + pri_key);
        return keyMap;
    }
    
    /**
     * 得到公钥
     * @param keyMap RSA密钥对
     * @return 公钥
     * @throws Exception 抛出异常
     */
    public static String getPublicKey(Map<String, String> keyMap) throws Exception{
        return keyMap.get(PUBLIC_KEY);
    }
    
    /**
     * 得到私钥
     * @param keyMap RSA密钥对
     * @return 私钥
     * @throws Exception 抛出异常
     */
    public static String getPrivateKey(Map<String, String> keyMap) throws Exception{
        return keyMap.get(PRIVATE_KEY);
    }
 
    /**
     * 数字签名
     * @param data 待签名数据
     * @param pri_key 私钥
     * @return 签名
     * @throws Exception 抛出异常
     */
    public static String sign(byte[] data, String pri_key) throws Exception {
        // 取得私钥
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key_bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);
 
        return Base64.encodeBase64String(signature.sign());
    }
 
    /**
     * RSA校验数字签名
     * @param data 数据
     * @param sign 签名
     * @param pub_key 公钥
     * @return 校验结果，成功为true，失败为false
     * @throws Exception 抛出异常
     */
    public boolean verify(byte[] data, byte[] sign, String pub_key) throws Exception {
        // 转换公钥材料
        // 实例化密钥工厂
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 初始化公钥
        // 密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key_bytes);
        // 产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }
 
    /**
     * 公钥加密
     * @param data 待加密数据
     * @param pub_key 公钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    private static byte[] encryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
 
    /**
     * 公钥加密
     * @param data 待加密数据
     * @param pub_key 公钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encryptByPubKey(String data, String pub_key) throws Exception {
        // 私匙加密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        byte[] enSign = encryptByPubKey(data.getBytes(), pub_key_bytes);
        return Base64.encodeBase64String(enSign);
    }
 
    /**
     * 私钥加密
     * @param data 待加密数据
     * @param pri_key 私钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    private static byte[] encryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
 
    /**
     * 私钥加密
     * @param data 待加密数据
     * @param pri_key 私钥
     * @return 密文
     * @throws Exception 抛出异常
     */
    public static String encryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙加密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        byte[] enSign = encryptByPriKey(data.getBytes(), pri_key_bytes);
        return Base64.encodeBase64String(enSign);
    }
 
    /**
     * 公钥解密
     * @param data 待解密数据
     * @param pub_key 公钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    private static byte[] decryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }
 
    /**
     * 公钥解密
     * @param data 待解密数据
     * @param pub_key 公钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decryptByPubKey(String data, String pub_key) throws Exception {
        // 公匙解密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        byte[] design = decryptByPubKey(Base64.decodeBase64(data), pub_key_bytes);
        return new String(design);
    }
 
    /**
     * 私钥解密
     * @param data 待解密数据
     * @param pri_key 私钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static byte[] decryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }
 
    /**
     * 私钥解密
     * @param data 待解密数据
     * @param pri_key 私钥
     * @return 明文
     * @throws Exception 抛出异常
     */
    public static String decryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙解密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        byte[] design = decryptByPriKey(Base64.decodeBase64(data), pri_key_bytes);
        return new String(design);
    }
 
     /**
     * @param args
     */
    @SuppressWarnings("static-access")
    public static void main(String[] args) throws Exception {
 
        RSATest das = new RSATest();
 
        String datastr = "天街小雨润如酥，草色遥看近却无。最是一年春好处，绝胜烟柳满皇都。";
        System.out.println("待加密数据：\n" + datastr);
        //获取密钥对
        Map<String, String> keyMap = new HashMap<String, String>();
        keyMap = das.initKey();
        //String pub_key = (String) keyMap.get(PUBLIC_KEY);
        String pub_key="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCodhipA7Ptytbo2GuJ7DK9FU31H3X2NYKCcvkMVydMTVVdu5TaWaZ6RPYUEieevcfqm8zcAiyZ+Xu35Gm8OvY9+Y5mtWp26XxdF9yTNBGIHx721oOC0K/bRiy9ypVhLyj7NlNRSEdDjmRIBoxdyDXg58XaSysSqRQt/0k0M65K2QIDAQAB";
        System.out.println(pub_key);
        String pri_key="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKh2GKkDs+3K1ujYa4nsMr0VTfUfdfY1goJy+QxXJ0xNVV27lNpZpnpE9hQSJ569x+qbzNwCLJn5e7fkabw69j35jma1anbpfF0X3JM0EYgfHvbWg4LQr9tGLL3KlWEvKPs2U1FIR0OOZEgGjF3INeDnxdpLKxKpFC3/STQzrkrZAgMBAAECgYAvbtkwtbcy7Fsows88zYHX1ajWrR4UzipKOr8/J0cMZ/XyZ3/ZH0cNxF8ZdacsPjmNKIM9K2uNiz63n5vaASwV/p0Fd4KDuPRz46v/kxaQM6AVBXlty45oyISbcafGs4MwO3hrjVA3eLrcDLrSr+DitEIabe+iWyioy1sUkGjsAQJBAPVNrZQPZGgsnU3QKHj8uKJBtsHaTIPsAGDw2Nnu75wqFLIbxosmBVCHAJMB0gcoaceIdp/myUNFSnF1f4PIJoECQQCvzqE7xTZG/JULZ/nRClwsDLN/PdvDqx1ZOK0f/vZ8+wwad58UlsdkRjtmvPR1WmW6IvcnOMu13pgHW18uNOhZAkEAt1LQEEDYlwoQnWCdfp3QFmLqBEHywPyU493bEjQBGkJQqTMc1E0b16ys/zBzGCpZs2cG0EWX0BBurBBVtdTVAQJAMtZWa1k4u8g4NSco5xjO1HILyaSSkv89KlqoPVNV5YgW1OR4XNTI7acdcDT9n523Qt7vhyj2Ry4J2j7CxDLKaQJARp7jnH1+7n9mVklsnyqeU9Qd9tHHslUzfynrro3LndaG1Q908074SvGBaCHZu3f6dk2EuESCHxY7FqT/AJmErA==\r\n" + 
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCodhipA7Ptytbo2GuJ7DK9FU31H3X2NYKCcvkMVydMTVVdu5TaWaZ6RPYUEieevcfqm8zcAiyZ+Xu35Gm8OvY9+Y5mtWp26XxdF9yTNBGIHx721oOC0K/bRiy9ypVhLyj7NlNRSEdDjmRIBoxdyDXg58XaSysSqRQt/0k0M65K2QIDAQAB\r\n" + 
            "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKh2GKkDs+3K1ujYa4nsMr0VTfUfdfY1goJy+QxXJ0xNVV27lNpZpnpE9hQSJ569x+qbzNwCLJn5e7fkabw69j35jma1anbpfF0X3JM0EYgfHvbWg4LQr9tGLL3KlWEvKPs2U1FIR0OOZEgGjF3INeDnxdpLKxKpFC3/STQzrkrZAgMBAAECgYAvbtkwtbcy7Fsows88zYHX1ajWrR4UzipKOr8/J0cMZ/XyZ3/ZH0cNxF8ZdacsPjmNKIM9K2uNiz63n5vaASwV/p0Fd4KDuPRz46v/kxaQM6AVBXlty45oyISbcafGs4MwO3hrjVA3eLrcDLrSr+DitEIabe+iWyioy1sUkGjsAQJBAPVNrZQPZGgsnU3QKHj8uKJBtsHaTIPsAGDw2Nnu75wqFLIbxosmBVCHAJMB0gcoaceIdp/myUNFSnF1f4PIJoECQQCvzqE7xTZG/JULZ/nRClwsDLN/PdvDqx1ZOK0f/vZ8+wwad58UlsdkRjtmvPR1WmW6IvcnOMu13pgHW18uNOhZAkEAt1LQEEDYlwoQnWCdfp3QFmLqBEHywPyU493bEjQBGkJQqTMc1E0b16ys/zBzGCpZs2cG0EWX0BBurBBVtdTVAQJAMtZWa1k4u8g4NSco5xjO1HILyaSSkv89KlqoPVNV5YgW1OR4XNTI7acdcDT9n523Qt7vhyj2Ry4J2j7CxDLKaQJARp7jnH1+7n9mVklsnyqeU9Qd9tHHslUzfynrro3LndaG1Q908074SvGBaCHZu3f6dk2EuESCHxY7FqT/AJmErA==";
        //String pri_key = (String) keyMap.get(PRIVATE_KEY);
        System.out.println(pri_key);
        // 公匙加密
        String pubKeyStr = RSATest.encryptByPubKey(datastr, pub_key);
        System.out.println("公匙加密结果：\n" + pubKeyStr);
        // 私匙解密
        String priKeyStr = RSATest.decryptByPriKey(pubKeyStr, pri_key);
        System.out.println("私匙解密结果：\n" + priKeyStr);
 
        //换行
        System.out.println();
        
        // 数字签名
        String str1 = "汉兵已略地";
        String str2 = "四面楚歌声";
        System.out.println("正确的签名：" + str1 + "\n错误的签名：" + str2);
        String sign = RSATest.sign(str1.getBytes(), pri_key);
        System.out.println("数字签名：\n" + sign);
        boolean vflag1 = das.verify(str1.getBytes(), Base64.decodeBase64(sign), pub_key);
        System.out.println("数字签名验证结果1：\n" + vflag1);
        boolean vflag2 = das.verify(str2.getBytes(), Base64.decodeBase64(sign), pub_key);
        System.out.println("数字签名验证结果2：\n" + vflag2);
    }
}