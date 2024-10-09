package com.feirui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.feirui.**.mapper")
public class PermissionRbacSdkApplication {
    public static void main(String[] args) {
        SpringApplication.run(PermissionRbacSdkApplication.class, args);
    }
}
