package com.feirui.permission.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Around("@annotation(MenuPermission)")
    public Object menuPermission(ProceedingJoinPoint joinPoint) {
        return null;
    }

    @Before("@annotation(AuthPermission)")
    public void authPermission(JoinPoint joinPoint) {

    }

}
