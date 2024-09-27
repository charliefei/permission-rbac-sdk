package com.feirui.common.constant;

public class AuthTypeConstant {
    /**
     * 用户-部门权限：本人
     */
    public static final String USER_POWER_OWN = "0";
    /**
     * 用户-部门权限：本部门
     */
    public static final String USER_POWER_DEPART = "1";
    /**
     * 用户-部门权限：本部门及下属部门
     */
    public static final String USER_POWER_ALL_SON = "2";
    /**
     * 用户-部门权限：全部
     */
    public static final String USER_POWER_ALL = "3";
    /**
     * 用户-部门权限：本分公司及下属部门
     */
    public static final String USER_POWER_COMPANY = "4";
    /**
     * 用户-部门权限：本分公司及下属公司、部门
     */
    public static final String USER_POWER_ALL_COMPANY_SON = "5";
    /**
     * 自定义部门
     */
    public static final String USER_DEFINITION_DEPART = "-1";
}
