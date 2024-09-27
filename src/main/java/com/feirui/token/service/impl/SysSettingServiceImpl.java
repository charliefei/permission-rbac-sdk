package com.feirui.token.service.impl;

import com.feirui.common.constant.RedisConstant;
import com.feirui.common.redis.RedisUtils;
import com.feirui.common.utils.QunjeObjectUtils;
import com.feirui.token.service.SysSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 10:31 周五
 */
@Component
@Slf4j
public class SysSettingServiceImpl implements SysSettingService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public String getSecretKeyByName(String keyName) {
        log.info("获取系统秘钥，keyName={}", keyName);
        if (QunjeObjectUtils.isNotEmpty(redisUtils.get(keyName))) {
            return String.valueOf(redisUtils.get(keyName));
        }

        // 把系统秘钥都缓存到redis
        redisUtils.set(RedisConstant.SYS_PUBLIC_KEY, "public");
        redisUtils.set(RedisConstant.SYS_PRIVATE_KEY, "private");

        return QunjeObjectUtils.isNotEmpty(redisUtils.get(keyName)) ? String.valueOf(redisUtils.get(keyName)) : "";
    }

    @Override
    public String getPrivateKey() {
        return getSecretKeyByName(RedisConstant.SYS_PRIVATE_KEY);
    }

    @Override
    public String getPublicKey() {
        return getSecretKeyByName(RedisConstant.SYS_PUBLIC_KEY);
    }
}
