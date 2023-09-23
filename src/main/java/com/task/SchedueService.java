package com.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
public class SchedueService {
    @Autowired
    CronTaskRegistrar cronTaskRegistrar;
    public void exe( Map exeMap) throws Exception {
        String cronExpression=(String)exeMap.remove("cron");
          if (null==cronExpression){
              throw new Exception("缺少执行表达值");
          }
        SchedulingRunnable task = new SchedulingRunnable("deafultTask", "taskMap", exeMap);
        cronTaskRegistrar.addCronTask(task, cronExpression);


    }
}
