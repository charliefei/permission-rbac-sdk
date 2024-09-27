package com.feirui.token.service;

import com.feirui.token.domain.AuthUserModel;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 11:09 周五
 */
public interface UserSecurityService {
    String getNewTokenWithRedis(AuthUserModel user, String loginType);

    String getNewTokenCoexist(AuthUserModel user, String loginType);
}
