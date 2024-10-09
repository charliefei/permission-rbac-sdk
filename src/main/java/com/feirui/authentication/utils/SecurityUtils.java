package com.feirui.authentication.utils;

import com.feirui.common.constant.TokenConstant;
import com.feirui.authentication.domain.AuthUserModel;
import com.feirui.authentication.service.AuthSecurityService;
import com.feirui.authentication.service.SysSettingService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 10:25 周五
 */
@Component
public class SecurityUtils {
    private static AuthSecurityService authSecurityService;
    private static SysSettingService sysSettingService;

    public SecurityUtils(AuthSecurityService authSecurityService, SysSettingService sysSettingService) {
        SecurityUtils.authSecurityService = authSecurityService;
        SecurityUtils.sysSettingService = sysSettingService;
    }

    /**
     * 功能描述: 根据token获取用户信息
     */
    public static AuthUserModel getUserByToken() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        return authSecurityService.getUserByToken(request.getHeader(TokenConstant.TOKEN), sysSettingService.getPrivateKey());
    }
}
