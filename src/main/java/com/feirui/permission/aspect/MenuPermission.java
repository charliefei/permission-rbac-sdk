package com.feirui.permission.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MenuPermission {

    // 角色权限,按钮权限,纵向越权
    String[] permission();
}
