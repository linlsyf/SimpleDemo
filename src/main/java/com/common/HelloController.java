package com.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
 public class HelloController {
    @Autowired
    JdbcService jdbcService;

    @GetMapping("/hello")
    public List<QueryMapBean> hello() {
//        String className="com.common.QueryMapBean";
////        String className="com.base.BaseBean";
////        String className="com.base.BaseBean";
//        Class  instanClass=null;
//        try {
//            instanClass=Class.forName(className);
//
//        }catch (Exception e){
//
//            return  new ArrayList<>();
//        }
        List<QueryMapBean> datalist=(List<QueryMapBean>)jdbcService.groovyquery("select id,content  from ERROR_LOG",new HashMap(),QueryMapBean.class);

        return datalist;
    }
}


