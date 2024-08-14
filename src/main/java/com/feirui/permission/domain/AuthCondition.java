package com.feirui.permission.domain;

import lombok.Data;

import java.util.List;

@Data
public class AuthCondition {
    private String companyId;
    //默认权限类型
    private List<SysUserAuth> defaultAuths;
    //自定义权限
    private List<SysUserAuth> definitionAuths;
}
