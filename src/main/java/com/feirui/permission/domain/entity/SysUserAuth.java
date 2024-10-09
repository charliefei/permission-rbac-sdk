package com.feirui.permission.domain.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserAuth implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 权限ID 默认六种或者自定义部门ID
     */
    private String authId;

    /**
     * 1默认权限类型 2自定义部门
     */
    private String authType;

    /**
     * 企业ID
     */
    private String companyId;

    /**
     * 组织代码 A001-B001
     */
    private String orgNo;

    /**
     * 0不包含下级 1包含下级
     */
    private Integer isContainsLower;
}
