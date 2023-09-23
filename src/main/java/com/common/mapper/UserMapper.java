package com.common.mapper;

import com.common.LoginUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public LoginUser  get(LoginUser user);
    public void  save(LoginUser user);
}
