package com.feirui.authentication.service;

/**
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/09/27 10:30 周五
 */
public interface SysSettingService {
    String getSecretKeyByName(String keyName);

    String getPrivateKey();

    String getPublicKey();
}
