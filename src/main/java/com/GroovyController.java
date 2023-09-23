package com;

import com.publish.groservice.service.GroService;
import com.spring.response.MBYViewModel;
import com.spring.response.ResultRespnseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class GroovyController {
    @Autowired
    GroService dictService;
    @GetMapping("/api/v1/groovy/exe")
    public MBYViewModel exe(@RequestParam Map params, service.Ztoken ztoken ) throws Exception {
        Object result= dictService.exeUpdate(params,ztoken);

        return ResultRespnseUtils.getResponseMsg(null,result);
    }

}