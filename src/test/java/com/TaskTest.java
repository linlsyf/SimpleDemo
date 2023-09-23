package com;

import com.Sync.SyncRunList;
import com.TypeMap.QueryGrantTypeService;
import com.task.CronTaskRegistrar;
import com.task.SchedueService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.task.TaskExecutor;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;


/**

 **/
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApplication.class)
public class TaskTest {

    @Autowired
    CronTaskRegistrar cronTaskRegistrar;
    @Autowired
    SchedueService scheService;
    @Autowired
    TaskExecutor taskExecutor;
    @Autowired
    private QueryGrantTypeService queryGrantTypeService;

    @Test
    public void testSwitch() throws Exception {
        String    result=  queryGrantTypeService.getResult("购物券");

        System.out.println("==========="+result);
    }
//    @Test
//    public void testTask() throws InterruptedException {
//        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskNoParams", null);
//        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");
//
//        // 便于观察
//        Thread.sleep(3000000);
//    }
//
//    @Test
//    public void testHaveParamsTask() throws InterruptedException {
//        Map exeMap=new HashMap();
//        exeMap.put("id","testid");
//        exeMap.put("name","testname");
//
//        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskMap", exeMap);
////        SchedulingRunnable task = new SchedulingRunnable("demoTask", "taskWithParams", "haha", 23);
//        cronTaskRegistrar.addCronTask(task, "0/10 * * * * ?");
//
//        // 便于观察
//        Thread.sleep(1000000);
//    }
    @Test
    public void testScheService() throws Exception {
        Map exeMap=new HashMap();
        exeMap.put("serviceName","blogService");
        exeMap.put("method","search");
        exeMap.put("cron","0/10 * * * * ?");
        scheService.exe(exeMap);

        // 便于观察
        Thread.sleep(1000000);
    }
    @Test
    public void testRunnable() throws Exception {
//        Map exeMap=new HashMap();
//        exeMap.put("serviceName","blogService");
//        exeMap.put("method","search");
//        exeMap.put("cron","0/10 * * * * ?");
//        scheService.exe(exeMap);
//
//        // 便于观察
//        Thread.sleep(1000000);

        List<String>  dateList=new ArrayList<>();
        CountDownLatch doSgnl=new CountDownLatch(dateList.size());
        Map<String, Object> argsObject=new HashMap<>();
        SyncRunList<String>  stringSyncRunList=new SyncRunList<String>(dateList, argsObject) {
            @Override
            protected void afterRun(SyncRunList<String>.SyncRunnb runnb) {

                doSgnl.countDown();

            }

            @Override
            protected void runItem(String item) {

                try {

                    System.out.println("==正在打印====="+item);
                }catch (Exception e){

//                    erorFlag.set(true)
                }

            }
        };

        stringSyncRunList.executeTasks(taskExecutor,dateList.size());
        doSgnl.await();


    }
}