package com.xyyy.shop.toolUtil;

import java.security.MessageDigest;
import java.util.Random;
  
/** 
 * 采用MD5加密解密 
 * @author tfq 
 * @datetime 2011-10-13 
 */  
public class MD5Util {  
	
	
	/*
	 *  对外统一提供加密算法
	 */
	public static String encryptStr(String strEnc){
		//String strTmp = convertMD5(string2MD5(strEnc));
		String strTmp = string2MD5(strEnc);
		strTmp = getStringRandom(2)+ strTmp + getStringRandom(3);  //2位随机数+密码+3位随机数
		return strTmp;
	} 
	
	/*
	 *  对外统一提供解密算法
	 */
	public static String decryptStr(String strDec) throws Exception{
		if(strDec==null||strDec.equals("")||strDec.length()<5){
			throw new Exception("密码不符合系统要求！");
		}
	    String strTmp = strDec.substring(2, strDec.length()-3);
	    //System.out.println("strTmp1==>"+strTmp);
	    strTmp = convertMD5((strTmp));
	    //System.out.println("strTmp2==>"+strTmp);
		return strTmp;
	} 
		
    
    /***  
     * MD5加码 生成32位md5码  
     */  
    public static String string2MD5(String inStr){  
        MessageDigest md5 = null;  
        try{  
            md5 = MessageDigest.getInstance("MD5");  
        }catch (Exception e){  
            System.out.println(e.toString());  
            e.printStackTrace();  
            return "";  
        }  
        char[] charArray = inStr.toCharArray();  
        byte[] byteArray = new byte[charArray.length];  
  
        for (int i = 0; i < charArray.length; i++)  
            byteArray[i] = (byte) charArray[i];  
        byte[] md5Bytes = md5.digest(byteArray);  
        StringBuffer hexValue = new StringBuffer();  
        for (int i = 0; i < md5Bytes.length; i++){  
            int val = ((int) md5Bytes[i]) & 0xff;  
            if (val < 16)  
                hexValue.append("0");  
            hexValue.append(Integer.toHexString(val));  
        }  
        return hexValue.toString();  
  
    }  
  
    /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
    private static String convertMD5(String inStr){  
  
        char[] a = inStr.toCharArray();  
        for (int i = 0; i < a.length; i++){  
            a[i] = (char) (a[i] ^ 't');  
        }  
        String s = new String(a);  
        return s;  
    }  
    
    
    /** 
     * 生产随机数字和字母 
     */ 
    public static String getStringRandom(int length) {     
        String val = "";  
        Random random = new Random();  
          
        //参数length，表示生成几位随机数  
        for(int i = 0; i < length; i++) {  
              
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";  
            //输出字母还是数字  
            if( "char".equalsIgnoreCase(charOrNum) ) {  
                //输出是大写字母还是小写字母  
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;  
                val += (char)(random.nextInt(26) + temp);  
            } else if( "num".equalsIgnoreCase(charOrNum) ) {  
                val += String.valueOf(random.nextInt(10));  
            }  
        }  
        return val;  
    }
  
    // 测试主函数  
	public static void main(String[] args) { 
        String s = new String("tangfuqiang");  
        System.out.println("第一次加密解密："); 
        System.out.println("原始：" + s);  
        System.out.println("MD5后：" + string2MD5(s));  
        System.out.println("加密的：" + convertMD5(s));  
        System.out.println("解密的：" + convertMD5(convertMD5(s)));  
        System.out.println("第二次加密解密："); 
        System.out.println("原始：" + s);
        System.out.println("加密的：" + encryptStr(s));  
        try {
			System.out.println("解密的：" + decryptStr(encryptStr(s)));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }  

    
    
//可变MD5加密算法    
	   /** 
	    * 获得MD5加密密码的方法 
	     */  
	    public static String getMD5ofStr(String origString) {  
	        String origMD5 = null;  
	        try {  
	            MessageDigest md5 = MessageDigest.getInstance("MD5");  
	            byte[] result = md5.digest(origString.getBytes());  
	            origMD5 = byteArray2HexStr(result);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return origMD5;  
	    }  
	    /** 
	     * 处理字节数组得到MD5密码的方法 
	     */  
	    private static String byteArray2HexStr(byte[] bs) {  
	        StringBuffer sb = new StringBuffer();  
	        for (byte b : bs) {  
	            sb.append(byte2HexStr(b));  
	        }  
	        return sb.toString();  
	    }  
	    /** 
	     * 字节标准移位转十六进制方法 
	     */  
	    private static String byte2HexStr(byte b) {  
	        String hexStr = null;  
	        int n = b;  
	        if (n < 0) {  
	            //若需要自定义加密,请修改这个移位算法即可  
	            n = b & 0x7F + 128;  
	        }  
	        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);  
	        return hexStr.toUpperCase();  
	    }  
	    /** 
	     * 提供一个MD5多次加密方法 
	     */  
	    public static String getMD5ofStr(String origString, int times) {  
	        String md5 = getMD5ofStr(origString);  
	        for (int i = 0; i < times - 1; i++) {  
	            md5 = getMD5ofStr(md5);  
	        }  
	        return getMD5ofStr(md5);  
	    }  
	    /** 
	     * 密码验证方法 
	     */  
	    public static boolean verifyPassword(String inputStr, String MD5Code) {  
	        return getMD5ofStr(inputStr).equals(MD5Code);  
	    }  
	  
	    /** 
	     * 重载一个多次加密时的密码验证方法 
	     */  
	    public static boolean verifyPassword(String inputStr, String MD5Code, int times) {  
	        return getMD5ofStr(inputStr, times).equals(MD5Code);  
	    }  
	    /** 
	     * 提供一个测试的主函数 
	     */  
//	    public static void main(String[] args) {  
//	        System.out.println("123:" + getMD5ofStr("123"));  
//	        System.out.println("123456789:" + getMD5ofStr("123456789"));  
//	        System.out.println("sarin:" + getMD5ofStr("sarin"));  
//	        System.out.println("123:" + getMD5ofStr("123", 4));  
//	    }  
//	
	
	
}  

