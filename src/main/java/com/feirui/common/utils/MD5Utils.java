package com.feirui.common.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
public class MD5Utils {

    /**
     * 全局数组
     */
    private static final String[] STR_DIGITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 功能描述: MD5加密
     *
     * @Author 瑞尔
     * @Date 9:26 2023/3/14
     **/
    public static String getMd5Code(String strObj) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // md.digest() 该函数返回值为存放哈希值结果的byte数组
            return byteToString(md.digest(strObj.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5 失败 >>>>>>>>>>", e);
        }
        return null;
    }

    /**
     * 功能描述: 转换字节数组为16进制字串
     *
     * @Author 瑞尔
     * @Date 9:28 2023/3/14
     **/
    private static String byteToString(byte[] bByte) {
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < bByte.length; i++) {
            sBuffer.append(byteToArrayString(bByte[i]));
        }
        return sBuffer.toString();
    }

    /**
     * 功能描述: 返回形式为数字跟字符串
     *
     * @Author 瑞尔
     * @Date 9:28 2023/3/14
     **/
    private static String byteToArrayString(byte bByte) {
        int iRet = bByte;
        if (iRet < 0) {
            iRet += 256;
        }
        int iD1 = iRet / 16;
        int iD2 = iRet % 16;
        return STR_DIGITS[iD1] + STR_DIGITS[iD2];
    }

    /**
     * 根据企业号获取加密串，发短信处需要
     *
     * @param companyCode 企业号
     * @return md5加密串
     * @throws NoSuchAlgorithmException
     */
    public static String getAppKey(String companyCode) throws NoSuchAlgorithmException {
        return getMd5Code(getMd5Code(companyCode + "-qjCode"));
    }


    /**
     * 根据六位随机数字，生成六位口令
     *
     * @return java.lang.String
     * @author feixiang
     * @since V1.0 feixiang 2022-05-16 10:34 初版设定
     */
    public static String getDynamicNumber(String code) {
        if (!StrUtil.isNumeric(code)) {
            return null;
        }
        int num = Integer.parseInt(code) % 10;
        if (num < 5) {
            num += 5;
        }
        try {
            for (int i = 0; i < num; i++) {
                code = getMd5Code(code).substring(num, num + 6);
            }
            return code;
        } catch (Exception e) {
            log.error("getDynamicNumber失败 >>>> code: {}", code, e);
        }
        return null;
    }
}
