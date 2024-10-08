package com.feirui.authorization.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PermissionAspect {

    @Around("@annotation(com.feirui.authorization.aspect.RequiredMenuPermission)")
    public Object menuPermission(ProceedingJoinPoint joinPoint) {
        return null;
    }

    @Before("@annotation(com.feirui.authorization.aspect.RequiredAuthPermission)")
    public void authPermission(JoinPoint joinPoint) {

    }

}
