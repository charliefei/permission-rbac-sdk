package com.feirui.permission.service;

import com.feirui.common.constant.AuthTypeConstant;
import com.feirui.common.constant.RedisConstant;
import com.feirui.common.redis.RedisUtils;
import com.feirui.permission.domain.dto.AuthCondition;
import com.feirui.permission.domain.entity.SysUserAuth;
import com.feirui.permission.mapper.SysUserAuthMapper;
import com.feirui.permission.service.strategy.AuthStrategyEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AuthorizationService implements ApplicationContextAware {

    private static SysUserAuthMapper sysUserAuthMapper;
    private static RedisUtils redisUtils;

    public static void dataPermission(AuthCondition authCondition) {
        List<SysUserAuth> userAuths = sysUserAuthMapper.selectAuthByUserId(authCondition.getUserId());
        if (userAuths != null && !userAuths.isEmpty()) {
            String authId = sysUserAuthMapper.selectAuthIdByUserId(authCondition.getUserId());
            authId = StringUtils.isNotEmpty(authId) ? authId : AuthTypeConstant.USER_DEFINITION_DEPART;
            if (!(StringUtils.isNotEmpty(authId) && Objects.equals(authId, AuthTypeConstant.USER_POWER_ALL))) {
                List<SysUserAuth> definitionAuths = userAuths.stream().filter(u -> u.getAuthType().equals(AuthTypeConstant.DEFINITION_DEPART))
                        .collect(Collectors.toList());
                // 设置自定义权限
                authCondition.setDefinitionAuths(definitionAuths);
            }
            AuthStrategyEnum.getAuthStrategy(authId).authExecute(authCondition);
            log.info("**************用户权限等级为:{}", authId);
        } else {
            // 未设置权限
            log.info("**************未设置权限:{}", authCondition.getUserId());
            redisUtils.hset(RedisConstant.AUTH_PERMISSION + authCondition.getLoginType() + "_" + authCondition.getUserId(), RedisConstant.AUTH, AuthTypeConstant.NO_AUTH);
        }
    }

    public static void menuPermission(String userId, String loginType) {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AuthorizationService.sysUserAuthMapper = applicationContext.getBean(SysUserAuthMapper.class);
        AuthorizationService.redisUtils = applicationContext.getBean(RedisUtils.class);
    }

}
