package com.feirui.authorization.domain.dto;

import com.feirui.authorization.domain.entity.SysUserAuth;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AuthCondition implements Serializable {
    /**
     * 用户Id
     */
    private String userId;

    /**
     * 默认部门Id
     */
    private String departmentId;

    /**
     * 企业Id
     */
    private String companyId;

    /**
     * 默认权限类型
     */
    private List<SysUserAuth> defaultAuths;

    /**
     * 自定义权限
     */
    private List<SysUserAuth> definitionAuths;

    /**
     * 登录类型, 权限分端
     */
    private String loginType;
}
