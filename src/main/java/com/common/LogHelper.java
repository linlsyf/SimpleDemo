package com.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
  public class LogHelper {
    public  static Object  instance=new LogHelper();
        @Autowired
        public  JdbcService jdbcService;
    
}
