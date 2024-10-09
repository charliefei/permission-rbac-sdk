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

    // *********************************************************

    /**
     * 无权限
     */
    public static final String NO_AUTH = "-1";

    /**
     * 所有权限
     */
    public static final String ALL_AUTH = "0";

    /**
     * 本人权限
     */
    public static final String OWN_AUTH = "1";

    /**
     * 条件权限
     */
    public static final String CONDITION_AUTH = "2";

    // *********************************************************

    /**
     * 默认部门类型
     */
    public static final String DEFAULT_DEPART = "1";

    /**
     * 自定义部门
     */
    public static final String DEFINITION_DEPART = "2";

    // *********************************************************

    /**
     * 不包含下级部门及公司
     */
    public static final int NOT_CONTAINS_LOWER = 0;

    /**
     * 包含下级部门及公司
     */
    public static final int CONTAINS_LOWER = 1;

    // *********************************************************

    /**
     * 管理员
     */
    public static final int IS_ADMIN = 1;
}
