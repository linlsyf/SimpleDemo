package com.publish.groservice.service;

import com.common.JdbcService;
import com.common.SysComponents;
import com.common.mapper.ComponentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.Ztoken;

import java.util.List;
import java.util.Map;

/**
 * 文章管理服务
 */
@Service
public class GroService {
    @Autowired
   public  JdbcService jdbcService;
    @Autowired
   public ComponentsMapper componentsMapper;
    public  Object  exeUpdate( Map params, Ztoken ztoken )throws Exception  {
        String  classString="";
        Map  searchContentMap=params;
        searchContentMap.putAll(params);

        String title=(String)searchContentMap.get("serviceName");
        List<SysComponents> result= componentsMapper.searchByTitle(title);
                if (result.size()>0){
                    SysComponents baseBean=result.get(0);
                    classString=baseBean.getContentStr();
                }

        params.put("groovy",classString);
        return jdbcService.invokeJavaMethod(params);

    }





}
