package Servlet;

import encrypt.Base64Utils;
import encrypt.KeyUtil;
import encrypt.RSAUtil;
import encrypt.RSAUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class testRSA {
  public static void main(String[] args) {
    String key="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiY01/xHo08U9nrSlgEqaD8c9K+Y9L46V\n" +
        "D3UekpFNDYFIrMa8tihiqzuLCZG2xX3ezBE8BmR4SJfoA56dQ/DApPJsbFh4RBzKvb/AHd26fV5i\n" +
        "Z3Lb2QNU+WldgXH+AeoGxvGAE5m9BMDWhMQaeBcqzvLxwHFzcnEJadcVGpJbYw52UwX0qc9CW6Zn\n" +
        "OSipz3GSDzuqp48UWRTutyqf/Y9DfzGqTLxS8L/oMS7tpnP99Ybe7fP1+f8HdtCr4sn1c+anrWCM\n" +
        "Z/WcdglpCgRzOg2hOSW+meLnbKgJD/ZKtI/gzYwl5jR/FrW3P6fb925/XKoW+jO6QYtsCPL3taTJ\n" +
        "HF7I7QIDAQAB";
String key2="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCJjTX/EejTxT2etKWASpoPxz0r\n" +
    "5j0vjpUPdR6SkU0NgUisxry2KGKrO4sJkbbFfd7METwGZHhIl+gDnp1D8MCk8mxsWHhEHMq9v8Ad\n" +
    "3bp9XmJnctvZA1T5aV2Bcf4B6gbG8YATmb0EwNaExBp4FyrO8vHAcXNycQlp1xUakltjDnZTBfSp\n" +
    "z0Jbpmc5KKnPcZIPO6qnjxRZFO63Kp/9j0N/MapMvFLwv+gxLu2mc/31ht7t8/X5/wd20KviyfVz\n" +
    "5qetYIxn9Zx2CWkKBHM6DaE5Jb6Z4udsqAkP9kq0j+DNjCXmNH8Wtbc/p9v3bn9cqhb6M7pBi2wI\n" +
    "8ve1pMkcXsjtAgMBAAECggEAei8UgtH+1nZDiUGALdiWx2M9BEzNlvv5jrSDhsBJCLEJxGf443o2\n" +
    "Q1Jt6/8isoVfrje08Ry3+AvvzZ9MpTgrd5ncE9X2ZtKIiUrWNfFqociActIFVdzu02nz5HNEpaCj\n" +
    "in6lPUP4Mku1glPMC1bQ8Om/MynT2hlsujaTCXpucSrpdECaPRGnHTT+8/MRe5fDvAuaAJL7CtI8\n" +
    "fahKaIWsImDnz15PCBRRHulFkK4/kstn1uWVwxGDNiR0xT6NJ2VxrdMmSV/6j1lu/BoQwrVil3Rh\n" +
    "fbIBe25VjCZbXFQyoWIKe7i3nm2d1NbNMd9zhh6spnBrXUxZAJFe6vyDxoWfgQKBgQDh2Zgj7Qa6\n" +
    "6BSvRghcvu2PLbYOBS9e9gJZVLp7itaXvAIX068F+JoTFgQ9Cf61uLucnmx3mrSo+IhX1kSoTGkI\n" +
    "GD6s339MA+89s4Puun2K2auNR8Y8FpTYNpbGq5D8MKfhrbWeB0j6rAemm0dbUSyP7Tb4LRu0Kt83\n" +
    "cT7c73SEYQKBgQCb6gbIzSj5Xny2BhsMbEFLo2bGxBAo23LEvLgzC1KiD7TXe0NsWR6npNL5tNxS\n" +
    "tk0Z2jcrqUyRDqLVXyXuZJdbbvVcfEjKrCzvsl6rsbKwn96ylI5QGXuUHbBGAWhU9Ro3K4NqdZGT\n" +
    "4nl2RLT/bKktlVBNc2bJHWUevuftOe4QDQKBgELL71JYzQ/EijGYneqIUYxyMZEN+Ye1bNZs18ao\n" +
    "NnWkFl5jrjUu5KrWbSR8a0flvh9BP8VUDTCYaQLJjX7VMo9BVHAenfCmjUCLcm7N20mQvUtIuhmV\n" +
    "eCpGYuuZXIUqAs0dakS11ODGtnCRJlacuHhM6WAXP0VXFLO6G6SOccChAoGAC0MmZk7bjNfUblmW\n" +
    "jpkrRklxXR5AhJMFgUyYiadhuq8jwwT2Y9c0LavsSXlVCOx+OUXYAmFiVIAYJw2Ocd/2RvaG5r1b\n" +
    "jLhPLqlVyVU2sSd7MK8mcIbGtHZIi2YCoVBrKl0MSx5e+626VF62LFUvC0nj2RRi8lgbO9NScYJj\n" +
    "vekCgYBVv8WS1b/UGgXhUsmiDLWVDnelLPkDrWU2sr7FOLdo4byBdDEMi9ioXZLIzYtEYX0lzO8U\n" +
    "wMTVtU8Qla8RucoLDGPEd3hsjRJtTVptFhMx20w612gQ5GJ9nYKMq/4+EaIE3XfMRIX9J75LDAs3\n" +
    "fTf/+UP/j0qNMdTLDN5VS8Hddw==";

    String aString="ak47";
    try {
      //byte[] encrya=RSAUtils.encryptData(aString.getBytes(),RSAUtil.string2PublicKey(KeyUtil.app_public_key));
    //String encrya="bzKmZwcpLjChC0ZlL/kiTpDSWibJU43zXgKI3UjeYIzP08CYc1Oc9fmS0NeKrCo63Tj3Smg2DGBDi5WUNvb3KwQJV0Uogup6ouEqV9wc7LLbzocUAhxMA06QmA8LjrKcVquGHYPPNc2EWEA6u+pVEsc5/MvspKD6BGVEf9lAc66mPw+SXgugz788KIXUiruIF/7dV5GEUb6oDX2z4AtVRqaQ/qUUVeSjqx8AmKbvyH/aenGCZoSRrNLoI6C5rU+tqkfmSfYYXNbobs6f9SH5IJS6EKgI91SFGaZ9bCrtzlqBAp8xMt024szrtN2h9V3dGTFIoSMaFhK6q5uCyVcWcyV3BIkEmMSWdAuprYwV4MjI2uJ3x8S0Dgmib15MbZYOJqUBmGSZp5o2Dc7lINONmjbqzD/AE4v624B7kbjzNBb1yekPb5w+O5V70FRDWM9ukt13KyEMZEJt8FkWYC9yKydmW1++jLX+P2an/PUeWfbUYvacwAkeJqfhdscUQOfLJzq408vbJfhLL/2wrmdx1usGdFHRMv22MXxlHXOtbY+17rdhe/Bs8JqhZViHSqae3rK8Qli9gohE/le16sfOGYuKh3u03J1ek9qYozxkfRyPMVy7QfJEH9FZgk3AXLGp+9FNec8kOHyN27k8FwwjUrPBbOJx2JrPTQE9qEsPihRVBNNSWaJY1NSjri5Pp2gnXiVRCYeHd1+bGb0Gmr2pMcLEY02vyqnRgPOuQvGKj+z06GDRbxUc+Cy5KWffwHKXhwEwsnqBig0t2bv8A5q1QGnX+QkH8p6W8MbJj0iPABvWPGWevEdfhP8qjnfWCdgk8iuv3xHFMaUvqA1DfdbCrp5BG4X+tnWokUAuYcc2tnqOh3nMWs65Bfor5DKF8Ahkbu7wxFTJ7WqN+Y4JTTwj9jT33RBGumOlvtqgUB02V/CAqnDmc31E2SCFL5CZLMle/Lv4jrFRyhHGlOb8e4ePKF3x9YHUJn7bK8f36lwbNRkh4RBwSk4+zJZDcDhwbGTxBXq36R13SSzdm08qABJYlMV0SlBEPMZ1pVfAqS2YJAELG91H6VB1Ei47rGPbTrK5bJ/GaXnlelaKQyzyMU5gW5DIEMIS/O5AoVhNV4hEGIPf3Q5XUelsmIaObNHxFnJwGJTRwG9rGR7u8ChKYYG9maFzAJhIeOjjZ5RPO+E9wC86yZ0UxBlSRsKvU41YjpsHw+lcJPNd5nPbMvaJZTaQyLGVIhwZUyHNqZt3u9AnQh4nThqUMr+GwJ5HTpolzg8GSusIx1m2gp8LKtA59tPI10AJm7tjZLsfb/uw0vOJQwDFDk0Z5HF9bujw9RIUvoNMCzuBXO8vOq3A7/p2lpOZ6g==";//RSAUtils.encryptData(aString.getBytes(),RSAUtil.string2PublicKey(KeyUtil.SERVER_PUBLIC_KEY));//RSAUtil.publicEncrypt(aString.getBytes(),RSAUtil.string2PublicKey(KeyUtil.APP_PUBLIC_KEY));
      /*String eString=new String(encrya);
      System.out.println("--"+Base64Utils.encode(encrya));*/
   //  byte[] decrya=RSAUtils.decryptData(encrya, RSAUtil.string2PrivateKey(KeyUtil.app_private_key));//RSAUtil.privateDecrypt(encrya, RSAUtil.string2PrivateKey(KeyUtil.APP_PRIVATE_KEY));
      //byte[] decrya=RSAUtils.decryptData(encrya, RSAUtil.string2PublicKey(key));
    //  String sString=new String(decrya);
    //  System.out.println(sString);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
