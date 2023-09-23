package com.task;

import com.publish.groservice.service.GroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: simple-demo
 * @description:
 * @author: CaoTing
 * @date: 2019/5/23
 **/
@Component("deafultTask")
public class DeafultTask {
    @Autowired
    GroService dictService;
    public void taskWithParams(String param1, Integer param2) {
        System.out.println("这是有参示例任务：" + param1 + param2);
    }

    public void taskMap(java.util.HashMap exeMap) throws Exception  {
       List<String>  keysList=new ArrayList<>(exeMap.keySet()) ;

        for (String  key: keysList ) {
            System.out.println("这是有参示例任务：" + exeMap.get(key));
        }
        dictService.exeUpdate(exeMap,null);



    }

    public void taskNoParams() {
        System.out.println("执行无参示例任务");
    }
}