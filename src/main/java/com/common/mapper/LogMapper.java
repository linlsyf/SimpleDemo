package com.common.mapper;

import com.common.ExceptionLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LogMapper {

    public ExceptionLog  get(String id);
    public void  save(ExceptionLog exceptionLog);
}
