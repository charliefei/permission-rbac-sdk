package com.feirui.permission.aspect;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthPermission {

    // 权限为本人时,源表别名,默认为s
    String alias() default "s";

    // 权限为本人时,源表人员列名称,默认为creator_id
    String column() default "creator_id";

}
