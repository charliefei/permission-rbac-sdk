package com.feirui.permission.domain;

import lombok.Data;

@Data
public class MenuPermissionAuth {
    private String menuId;
    private String menuPermission;
    private String remark;
    private String menuName;
}
