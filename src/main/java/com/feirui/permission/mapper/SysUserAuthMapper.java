package com.feirui.permission.mapper;

import com.feirui.permission.domain.entity.SysUserAuth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Copyright (C), 群杰物联
 *
 * @author charliefei
 * @version V1.0
 * @description 描述信息
 * @date 2024/10/09 10:15 周三
 */
public interface SysUserAuthMapper {
    String selectAuthIdByUserId(@Param("userId") String userId);

    List<SysUserAuth> selectAuthByUserId(@Param("userId") String userId);
}
