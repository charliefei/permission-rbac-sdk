package com.feirui.token.service.impl;

import cn.hutool.json.JSONUtil;
import com.feirui.common.constant.RedisConstant;
import com.feirui.common.redis.RedisUtils;
import com.feirui.common.utils.MD5Utils;
import com.feirui.common.utils.RsaHelper;
import com.feirui.token.domain.AuthUserModel;
import com.feirui.token.service.SysSettingService;
import com.feirui.token.service.UserSecurityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 11:09 周五
 */
@Component
@Slf4j
public class UserSecurityServiceImpl implements UserSecurityService {
    @Resource
    private SysSettingService sysSettingService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public String getNewTokenWithRedis(AuthUserModel user, String loginType) {
        // token安全随机数，防止不同系统间ID重复，导致token复用
        user.setUuid(UUID.randomUUID().toString());
        Long tokenTime = 60 * 60L;
        long currentTime = System.currentTimeMillis();
        // 用于踢下线token跨服务用于不踢下线token
        user.setAccessTime(currentTime + tokenTime * 1000);
        // 用户不可同时登录相同账号，否则已登录账号下线
        String token = RsaHelper.encipher(JSONUtil.toJsonStr(user), sysSettingService.getPublicKey(), RsaHelper.enSegmentSize);
        log.info("用户登录，新token:{},来源：{}", token, loginType);
        // 设置token过期时间，用户踢下线
        redisUtils.setEX(RedisConstant.COMMON_USER_TOKEN + user.getId() + "_" + loginType,
                token,
                // 根据类型获取令牌过期原理
                tokenTime,
                TimeUnit.SECONDS);
        // token缓存，用于踢下线token跨服务用于不踢下线token
        assert token != null;
        redisUtils.setEX(RedisConstant.COMMON_TOKEN + MD5Utils.getMd5Code(token),
                user.getId() + "_" + loginType + "_" + token,
                tokenTime,
                TimeUnit.SECONDS);
        return token;
    }

    @Override
    public String getNewTokenCoexist(AuthUserModel user, String loginType) {
        // 用户可重复登录，不踢下线
        long tokenTime = 60 * 60L;
        long currentTime = System.currentTimeMillis();
        user.setAccessTime(currentTime + tokenTime * 1000);
        String token = RsaHelper.encipher(JSONUtil.toJsonStr(user), sysSettingService.getPublicKey(), RsaHelper.enSegmentSize);
        // token，redis缓存，不做踢下线处理
        assert token != null;
        redisUtils.setEX(RedisConstant.COMMON_TOKEN + MD5Utils.getMd5Code(token),
                user.getId() + "_" + loginType + "_" + token,
                tokenTime,
                TimeUnit.SECONDS);
        log.info("用户登录，新token:{},来源：{}", token, loginType);
        return token;
    }
}
