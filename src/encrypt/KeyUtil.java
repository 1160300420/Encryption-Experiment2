package encrypt;

import jdk.internal.org.objectweb.asm.commons.StaticInitMerger;

public class KeyUtil {
//服务端的RSA公钥(Base64编码)
  public final static String SERVER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApkixN3Dc6BLzb/V74VpxRXsSIu9AabGm"
                                              + "K4xfcPiIqub0JS99a+P6XAOGuiMT2W4p1C8U9MZDRgHjUOrKGcc5ve9uT+U90LiAgwG58YdrklOT"
                                              + "wlGvo6Xh4HQLRXMNoGsn6jLGdOV1RIVfWQ5EWfEB1+5v86QarLyfLIJ4ujVQfafEJ4dCwmoNSJk8"
                                              + "xqVBAW9tDZlNOOgaZPJuEXVIFEEjIZCkFkFxkomwVNdp79Xewrj0mCybCDVy6Mcx3jOxY0gGwbGg"
                                              + "S3YQxDbOpqYna8rcmf6CVJ2GA75sCU61Y8Of244CR5Rwkspbr1Pbf4UNSbVbpxzI08z1jrJvCVYW"
                                              + "NQLMwwIDAQAB";
  
  //服务端的RSA私钥(Base64编码)
  public final static String SERVER_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCmSLE3cNzoEvNv9XvhWnFFexIi"
                                              + "70BpsaYrjF9w+Iiq5vQlL31r4/pcA4a6IxPZbinULxT0xkNGAeNQ6soZxzm9725P5T3QuICDAbnx"
                                              + "h2uSU5PCUa+jpeHgdAtFcw2gayfqMsZ05XVEhV9ZDkRZ8QHX7m/zpBqsvJ8sgni6NVB9p8Qnh0LC"
                                              + "ag1ImTzGpUEBb20NmU046Bpk8m4RdUgUQSMhkKQWQXGSibBU12nv1d7CuPSYLJsINXLoxzHeM7Fj"
                                              + "SAbBsaBLdhDENs6mpidrytyZ/oJUnYYDvmwJTrVjw5/bjgJHlHCSyluvU9t/hQ1JtVunHMjTzPWO"
                                              + "sm8JVhY1AszDAgMBAAECggEAFTOHhO4a/GwOJeRC20TQ1G8QrOucZt2DtmG7eYf2xPOVhXg8oZj7"
                                              + "vuekMe9vBHYLV0Z5gYwV38M13IdTJV5FenYgtocgDpC3sfxyXN1LVejaGhiYMGFiH2AsX7p/rkh7"
                                              + "Wl0G+LiY7xeiRJSRGnakKYf5NjNiQ0v5b49jHTrW/G5G579xHOC0EXJezgKKflD+XXhpBjVCMfzk"
                                              + "Y8tK+ZT5XnUf2U+I7o9u6G3Y3wky2ajR0GyBigLGwI0mVqyA4ie3ggSiMxotfcDwP1HpV/fK//DP"
                                              + "pUgBiUgspFt1NtVcNDxeQlqw3UYakfUn5j3inA4C+fZwAe7VNwZkW2uaCl7HIQKBgQDXE49dGomU"
                                              + "rvIXeXt/muUw5sy81sufYxUdGm9slfr8DhyEr0KKdrnKFdLtRPdr0mHnp0mlihE+rQQyQg67qys3"
                                              + "c4C4TcLq6PHmtkd6K0+rUDYM2BNltjgd42UA86tchZ5Qu1u7dqxmnExYUb/kXbNk/euOcv1iNkdV"
                                              + "Gq61AZVhfwKBgQDF7G+4q92AQTjYk1dT0Us5oAkGj4GRs5XMeJB+0jhWRhyNvgXRuKkhMgEjbSog"
                                              + "AnywQfD93xDw2nZw2TVFqSS5cGCR+KnwTkQcA2vE2IbduDQRTT9JUQas3EJhZ1Cp+xyXfAQorCQW"
                                              + "5TSNfb0LskTnV+aJaM/Yxb32NzPlXksuvQKBgDn9FhxeOVYTTUazBG9FTiI/OFh5+XDCAEFWjVBT"
                                              + "p9Yp39qOfnxiwnkQJUy/2Y4CrU8ONbciYL/rWkRKtzo2TnKm+7+1h6ZapE42O1NfNh3UhJ417BTy"
                                              + "anL0ipkVGdDaXfMacQM8XgNUhOkTMY/bC7FhHQ/NRTAjvlvd09kN0j71AoGAHKaoWZRPgTRv1TIn"
                                              + "DxQaDqJzDAcUG5JimfHOAP3Pd/W4RnB+iShxG0QQ1B8GXRHfGOjCyQ1Ud3k4cgePZaEhltKEuDzF"
                                              + "5Op/g4qfPCSYCVqT9vk2sxdOnxFXbqA1FhYqwmcKdxTMOKA/ZkgQaLQKs26PCc8pX1josc617Xsj"
                                              + "6QUCgYA3sHC9I8fan7FAneJvE1Fsc1ZATMuo/yNA8WlASg+OPgeCgIv5AHAvkKqj3ZYapnafmHJj"
                                              + "6L6jUL9dFwKqMncAdVOYI/V0oVoa8wgCHQ6b4S55dWMYhd3hSg8+8mUdxw6oOFL7rpRYduX1KxKc"
                                              + "8Y4OuB7RsQgdKlT/sEBDEFdS/w==";
/*//客户端的RSA公钥(Base64编码)
  public final static String APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApkixN3Dc6BLzb/V74VpxRXsSIu9AabGm"
                                              + "K4xfcPiIqub0JS99a+P6XAOGuiMT2W4p1C8U9MZDRgHjUOrKGcc5ve9uT+U90LiAgwG58YdrklOT"
                                              + "wlGvo6Xh4HQLRXMNoGsn6jLGdOV1RIVfWQ5EWfEB1+5v86QarLyfLIJ4ujVQfafEJ4dCwmoNSJk8"
                                              + "xqVBAW9tDZlNOOgaZPJuEXVIFEEjIZCkFkFxkomwVNdp79Xewrj0mCybCDVy6Mcx3jOxY0gGwbGg"
                                              + "S3YQxDbOpqYna8rcmf6CVJ2GA75sCU61Y8Of244CR5Rwkspbr1Pbf4UNSbVbpxzI08z1jrJvCVYW"
                                              + "NQLMwwIDAQAB";*/
//客户端的RSA私钥(Base64编码)
  public final static String APP_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCmSLE3cNzoEvNv9XvhWnFFexIi"
      + "70BpsaYrjF9w+Iiq5vQlL31r4/pcA4a6IxPZbinULxT0xkNGAeNQ6soZxzm9725P5T3QuICDAbnx"
      + "h2uSU5PCUa+jpeHgdAtFcw2gayfqMsZ05XVEhV9ZDkRZ8QHX7m/zpBqsvJ8sgni6NVB9p8Qnh0LC"
      + "ag1ImTzGpUEBb20NmU046Bpk8m4RdUgUQSMhkKQWQXGSibBU12nv1d7CuPSYLJsINXLoxzHeM7Fj"
      + "SAbBsaBLdhDENs6mpidrytyZ/oJUnYYDvmwJTrVjw5/bjgJHlHCSyluvU9t/hQ1JtVunHMjTzPWO"
      + "sm8JVhY1AszDAgMBAAECggEAFTOHhO4a/GwOJeRC20TQ1G8QrOucZt2DtmG7eYf2xPOVhXg8oZj7"
      + "vuekMe9vBHYLV0Z5gYwV38M13IdTJV5FenYgtocgDpC3sfxyXN1LVejaGhiYMGFiH2AsX7p/rkh7"
      + "Wl0G+LiY7xeiRJSRGnakKYf5NjNiQ0v5b49jHTrW/G5G579xHOC0EXJezgKKflD+XXhpBjVCMfzk"
      + "Y8tK+ZT5XnUf2U+I7o9u6G3Y3wky2ajR0GyBigLGwI0mVqyA4ie3ggSiMxotfcDwP1HpV/fK//DP"
      + "pUgBiUgspFt1NtVcNDxeQlqw3UYakfUn5j3inA4C+fZwAe7VNwZkW2uaCl7HIQKBgQDXE49dGomU"
      + "rvIXeXt/muUw5sy81sufYxUdGm9slfr8DhyEr0KKdrnKFdLtRPdr0mHnp0mlihE+rQQyQg67qys3"
      + "c4C4TcLq6PHmtkd6K0+rUDYM2BNltjgd42UA86tchZ5Qu1u7dqxmnExYUb/kXbNk/euOcv1iNkdV"
      + "Gq61AZVhfwKBgQDF7G+4q92AQTjYk1dT0Us5oAkGj4GRs5XMeJB+0jhWRhyNvgXRuKkhMgEjbSog"
      + "AnywQfD93xDw2nZw2TVFqSS5cGCR+KnwTkQcA2vE2IbduDQRTT9JUQas3EJhZ1Cp+xyXfAQorCQW"
      + "5TSNfb0LskTnV+aJaM/Yxb32NzPlXksuvQKBgDn9FhxeOVYTTUazBG9FTiI/OFh5+XDCAEFWjVBT"
      + "p9Yp39qOfnxiwnkQJUy/2Y4CrU8ONbciYL/rWkRKtzo2TnKm+7+1h6ZapE42O1NfNh3UhJ417BTy"
      + "anL0ipkVGdDaXfMacQM8XgNUhOkTMY/bC7FhHQ/NRTAjvlvd09kN0j71AoGAHKaoWZRPgTRv1TIn"
      + "DxQaDqJzDAcUG5JimfHOAP3Pd/W4RnB+iShxG0QQ1B8GXRHfGOjCyQ1Ud3k4cgePZaEhltKEuDzF"
      + "5Op/g4qfPCSYCVqT9vk2sxdOnxFXbqA1FhYqwmcKdxTMOKA/ZkgQaLQKs26PCc8pX1josc617Xsj"
      + "6QUCgYA3sHC9I8fan7FAneJvE1Fsc1ZATMuo/yNA8WlASg+OPgeCgIv5AHAvkKqj3ZYapnafmHJj"
      + "6L6jUL9dFwKqMncAdVOYI/V0oVoa8wgCHQ6b4S55dWMYhd3hSg8+8mUdxw6oOFL7rpRYduX1KxKc"
      + "8Y4OuB7RsQgdKlT/sEBDEFdS/w==";
  public static final String BANK_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiY01/xHo08U9nrSlgEqaD8c9K+Y9L46V\n" +
        "D3UekpFNDYFIrMa8tihiqzuLCZG2xX3ezBE8BmR4SJfoA56dQ/DApPJsbFh4RBzKvb/AHd26fV5i\n" +
        "Z3Lb2QNU+WldgXH+AeoGxvGAE5m9BMDWhMQaeBcqzvLxwHFzcnEJadcVGpJbYw52UwX0qc9CW6Zn\n" +
        "OSipz3GSDzuqp48UWRTutyqf/Y9DfzGqTLxS8L/oMS7tpnP99Ybe7fP1+f8HdtCr4sn1c+anrWCM\n" +
        "Z/WcdglpCgRzOg2hOSW+meLnbKgJD/ZKtI/gzYwl5jR/FrW3P6fb925/XKoW+jO6QYtsCPL3taTJ\n" +
        "HF7I7QIDAQAB";
  public static String APP_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEApqf3qq/W9f0rvyfrtTdxEPOPkEO4p2n7\r\n" + 
      "sDWMmUH+Sk5fmk6nBkH3KsYI+xNV7z373OAvd8nF3O+xnT+oD9VJD4SxEIgaEBn0Gv5xLE8Q/hjL\r\n" + 
      "tFaDMtc6vYBh/zSEv/QdaOzIXun7UzorqBHwqd2XFAlgdDX5EJj8rq5x+72mZ91PVn9h+7tmgdhk\r\n" + 
      "vuX6bsPSg6Wfvr4Yk57z2QW28kE/hR/9ZfV+y4Se9+/7WMCCb7DlWQDd31HFVdyUFZtHZzkquCpJ\r\n" + 
      "SPVPfWA1QKYl4IXucWIoMKhLjf0XAWpuUYrOEXCIS9rSv+LIdiiE32Klp8JZnfqcLihhmV/f1awx\r\n" + 
      "yvyojwIDAQAB";
  public static String app_private_key="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCmp/eqr9b1/Su/J+u1N3EQ84+Q\r\n" + 
      "Q7inafuwNYyZQf5KTl+aTqcGQfcqxgj7E1XvPfvc4C93ycXc77GdP6gP1UkPhLEQiBoQGfQa/nEs\r\n" + 
      "TxD+GMu0VoMy1zq9gGH/NIS/9B1o7Mhe6ftTOiuoEfCp3ZcUCWB0NfkQmPyurnH7vaZn3U9Wf2H7\r\n" + 
      "u2aB2GS+5fpuw9KDpZ++vhiTnvPZBbbyQT+FH/1l9X7LhJ737/tYwIJvsOVZAN3fUcVV3JQVm0dn\r\n" + 
      "OSq4KklI9U99YDVApiXghe5xYigwqEuN/RcBam5Ris4RcIhL2tK/4sh2KITfYqWnwlmd+pwuKGGZ\r\n" + 
      "X9/VrDHK/KiPAgMBAAECggEAZPeUY/O+Xy/wBhLQiey58pQtITD2OW9LTflTYjKKNkh/QURviqoC\r\n" + 
      "bgamwD55rMU3xxyHhaJX3r/VMrizQExuxBkXETXz3Fds/cGznS2V1Ov+1hYnzHB7JP4X4P7XglgK\r\n" + 
      "TVxPFLnbSxQOCPg9fxxCAKChsCZW7AhT5hzH78V5gKioJjcWVtpBU+Y2cfrXkWyXPmFsNmvVrG7+\r\n" + 
      "6w95uqyu5osDMonGB56t1TAHdw+agx8NZzUpdaSQM6kSJjCSiY7N/hLAQBGlSDLdZBy561tJqfeA\r\n" + 
      "NUxcPICAVceWZJkFQmo4ReKYh5somCj9xtovGKctT0J274CyINJ5UhK8Vx4WoQKBgQDc2j/fga60\r\n" + 
      "8dqwLcdCQ9u57vMWJ6czIsFZrQwpSg6AKDD4KLRdG3vGJJldqgCnMEXfkKrPuef1DUA9MIeor51P\r\n" + 
      "M6b0LH9DopuPhUiSZyAIoNBoT8HHAyKFB2uWD3GKYN/VInuYV7huxOKqaF2mu9vuoT/1biG+Yu3Z\r\n" + 
      "FUQCAeimvwKBgQDBLbPW2Yv/1AqccgI7CCWYS0tQlqGgqcLNz1Ireq0Fh7738oO80dJUe+XF7Xoq\r\n" + 
      "6LOI6UKZRCYOqy9/4v1LO8ZjcHGFsdNkRVUTPmKtBntkyoiKkktxurWOTlsRxOdoC21vCuUvrUdb\r\n" + 
      "ShbEb0USZozRCUcKAQAR41N/DYuMfujCMQKBgAPmInyKqI/vOSIlHMBxvD5TFp2Mg6omce1oXa+Y\r\n" + 
      "7BqdGwg2h8ChDJVZ7g82Qh6xfnpM7ocOTCcKXdj2s15qA0fzrH3hDzodc8ub2cUj5u/gDu3Ygp5F\r\n" + 
      "0uUwVVjn2uRLg4gavL3axpnGBg5mG0knReG3nQ5zLEtVKcfMVVAHwpi3AoGABOhlVkMGpIQ9lMXs\r\n" + 
      "upU1Z04aZry89Hj7owDuZH5kYOyW9HOr1yM5hb30E3g6D1cEIKlUcNJjaSH+PfCFYODu5F3Z9HyV\r\n" + 
      "E0oTpD6EgULV5bSjjWm0EvqcXDYvaB8pheIscJLFSdXMJ0yVkX4GMjZ6w2DJ9j4aQ1oUJ/kvH22l\r\n" + 
      "+jECgYBWAm6EA81MEqoEpUt3mH7KuEfNxNCaXZFhS1p3wHSoaciynW7CWHyELLYPoFdQ2yKnZt8D\r\n" + 
      "7Znaos0DY0wkOO/OKFz/QsPUSAbCS4VjCFmA0sew2zT6EwWVhKMfhgHIC4ffgipduht2CXko4Xnw\r\n" + 
      "/hs9e4QbxUnFfGP/OTCk1TVskg==";
  public final static String CA_PUBLIC_KEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAiibKudYiLMyazBShN9OH+nnxqWs2r+VT\r\n" + 
      "Z0ftHyqe7tnNYlZtkeEvwI6EeqAWfPySLIofEmV3yVRHq6D8E+g3rG2j8xibxCpX6YVSXlNfWY5+\r\n" + 
      "azBiC0FdtKKpRSrfK2ia+Gs2J3vPRqDBL8aoeYsYW2QnRkP2+Haqeg6Wo2RHH801WJUaj9PoYL54\r\n" + 
      "ajKGt6Ed8PoJVFRgof/mPYFuV3PiUIeu5eUuO5t7PCiidWc3bPfzm5OVccxGB1Wo9FdC4I5jON59\r\n" + 
      "/IkWL7Y3aj//NbghjBRsKIavfsQDxGk4d/Wj0HqZCLB6Sgb+GXtoxDhGqIQlFFf5hCw4i/qfQY5t\r\n" + 
      "X2CHlQIDAQAB";
}
