package com.gy.gyutil;
import java.security.MessageDigest;

/**
 * 采用MD5加密解密
 */
public class MD5Util {

    /***
     * MD5加码 生成32位md5码
     */
    public static String string2MD5(String inStr){
        MessageDigest md5 = null;
        try{
            md5 = MessageDigest.getInstance("MD5");
        }catch (Exception e){
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
     * 根据自己的规则进行MD5加密
     */
    public static String MD5Test(String inStr){
        String tietong = "tie&tong&";
        String finalStr="";
        if(inStr!=null){
            finalStr = string2MD5( tietong+inStr);
        }else{
            finalStr = string2MD5(tietong);
        }
        return finalStr;
    }

    // 测试
    public static void main(String args[]) {
        String s1 = "companyCode=tietong&dataSource=03050&timestamp=2019-08-27 12:00:00&dataType=pricelist&brand=华为";
        System.out.println("使用工具类进行加密的为 "+MD5Test(s1));

    }
}