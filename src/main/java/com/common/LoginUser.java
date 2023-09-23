package com.common;

import lombok.Getter;
import lombok.Setter;

@Getter//get方法
@Setter//set方法
public class LoginUser {
    private String id;
    private String name;// 用户姓名
    private String sex;// 性别
    private String registerId;// 注册登录id
    private String isAdmin="0";// 登录名称
    private String headImageName="";// 登录名称
    private String pwd="";// 登录名称
    private String loginId="";// 登录名称
}
