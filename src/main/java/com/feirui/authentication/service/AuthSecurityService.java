package com.feirui.authentication.service;

import com.feirui.authentication.domain.AuthUserModel;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 10:26 周五
 */
public interface AuthSecurityService {
    AuthUserModel getUserByToken(String token, String privateKey);
}
