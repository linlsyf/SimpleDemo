package com.common;

import com.common.service.ComponentsService;
import com.spring.response.MBYViewModel;
import com.spring.response.ResponseMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.Ztoken;

@RestController
 public class ComponentsController {
    @Autowired
    ComponentsService componentsService ;
    @PostMapping("/api/v1/components/update" )
    public MBYViewModel update(@RequestBody  SysComponents  components) throws Exception  {
    service.Ztoken ztoken =new Ztoken();
//    String  content=(String)components.getContentStr();
        if (null!=components.getContentStr()){
            components.setContent(components.getContentStr().getBytes());
        }
        int reusltCode= componentsService.update(components,ztoken);
        ResponseMsg reuslt= new ResponseMsg();
        if (reusltCode>0){
            reuslt.setSuccess(true);
        }else{
            reuslt.setSuccess(false);
        }
        return reuslt;
    }
    @GetMapping("/api/v1/components/get")
    public MBYViewModel get( String  id, service.Ztoken ztoken) throws Exception  {
//    public MBYViewModel update(@RequestParam Map params, service.Ztoken ztoken) throws Exception  {

        SysComponents  compsons= componentsService.get(id,ztoken);
        ResponseMsg reuslt= new ResponseMsg();
        reuslt.setData(compsons);

        return reuslt;
    }
}


