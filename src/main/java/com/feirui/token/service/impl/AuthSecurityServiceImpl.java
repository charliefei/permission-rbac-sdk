package com.feirui.token.service.impl;

import com.feirui.common.constant.RedisConstant;
import com.feirui.common.json.JSON;
import com.feirui.common.redis.RedisUtils;
import com.feirui.common.utils.QunjeObjectUtils;
import com.feirui.common.utils.RsaHelper;
import com.feirui.token.domain.AuthUserModel;
import com.feirui.token.service.AuthSecurityService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 10:26 周五
 */
@Component
public class AuthSecurityServiceImpl implements AuthSecurityService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public AuthUserModel getUserByToken(String token, String privateKey) {
        AuthUserModel res = null;
        AuthUserModel authUserModel = JSON.parseObject(RsaHelper.decipher(token, privateKey, RsaHelper.deSegmentSize)
                , AuthUserModel.class);
        if (authUserModel != null) {
            Object user = redisUtils.get(RedisConstant.COMMON_USER_HEADER + authUserModel.getId());
            // redis缓存人员
            if (QunjeObjectUtils.isNotEmpty(user)) {
                res = JSON.convertObject(user, AuthUserModel.class);
            }
            if (res == null) {
                // 数据库查询人员
            }
            if (res != null) {
                // 查询是否管理员权限
                res.setDepartmentScope(0);
                res.setAccessTime(authUserModel.getAccessTime());
            }
        }
        return res;
    }
}
