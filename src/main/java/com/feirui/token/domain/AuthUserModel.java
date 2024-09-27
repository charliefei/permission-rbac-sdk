package com.feirui.token.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Accessors(chain = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthUserModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 唯一约束
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 企业编号
     */
    private String companyId;

    /**
     * 工号、指纹编号
     */
    private String cardNo;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 是否被锁
     */
    private Integer lockStatus;

    /**
     * 是否冻结 0 正常 1 冻结
     */
    private Integer freezeStatus;

    /**
     * 是否管理员
     */
    private Integer adminType = 0;

    /**
     * email
     */
    private String email;

    /**
     * 0:未登陆，1：已登陆
     */
    private Integer firstLoginStatus;

    /**
     * 输错密码次数
     */
    private Integer pwdWrongTimes;

    /**
     * 密码解锁时间
     */
    private Date pwdUnlockTime;

    /**
     * 密码过期时间
     */
    private Date pwdExpireTime;

    /**
     * 登录次数
     */
    private Integer loginCount;

    /**
     * 密码加密方式：MD5; SM3
     */
    private String encryptMode;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 企业号
     */
    private String companyCode;

    /**
     * 存活时间
     */
    private Long accessTime;

    /**
     * 厂商注册码
     */
    private String userKey;

    /**
     * 头像
     */
    private String photo;

    /**
     * 厂商0华为1小米2vivo3oppo4Apple
     */
    private Integer manufacturer;

    /**
     * 部门管理范围(管理员权限)
     */
    private Integer departmentScope;

    /**
     * 最近登录时间
     */
    private Date recentLoginDate;

    /**
     * 部门ID
     */
    private String departmentId;

    /**
     * 部门名称
     */
    private String departmentName;

    /**
     * 印控台是否登录
     */
    private Integer consoleLoginStatus;

    /**
     * 游客 1 非游客 2
     */
    private Integer touristFlag;

    /**
     * 部门编号
     */
    private String orgNo;

    /**
     * token安全随机数，防止不同系统间ID重复，导致token复用
     */
    private String uuid;
}
