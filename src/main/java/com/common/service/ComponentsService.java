package com.common.service;

import com.common.JdbcService;
import com.common.SysComponents;
import com.common.mapper.ComponentsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ComponentsService {
    @Autowired
    JdbcService jdbcService;
    @Autowired
    public ComponentsMapper componentsMapper;
    public int update(SysComponents components, service.Ztoken ztoken) throws Exception {
//        String templateString = ZStringUtils.getJarFileString("sql/UpdateComponents.sql");
          if (StringUtils.isEmpty(components.getContent())){
              return -1;
          }
        return componentsMapper.update(components);


    }
    public SysComponents get(String id, service.Ztoken ztoken) throws Exception {
//        String templateString = ZStringUtils.getJarFileString("sql/UpdateComponents.sql");

        return componentsMapper.get(id);


    }
}
