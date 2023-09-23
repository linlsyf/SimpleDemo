package com.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExceptionLogService {
//    @Autowired
//    private ExceptionLogDao exceptionLogDao;
@Autowired
JdbcService jdbcService;
    public void insertExceptionLog(ExceptionLog exceptionLog) {
//         先查询数据库中是否有该条错误日志
//        ExceptionLog temp = exceptionLogDao.getExceptionLogByParam(
//                exceptionLog.getPath(),
//                exceptionLog.getMethodName(),
//                exceptionLog.getExceptionType()
//        );

        Map msgMap=new HashMap();
        msgMap.put("type","ExceptionLogService");

        String  content=exceptionLog.toString();
        msgMap.put("content",content);
        String title="ExceptionLogService error";
        msgMap.put("title",title);
        msgMap.put("name",title);
        jdbcService.saveLog(msgMap,null);
//
//        // 插入数据
//        exceptionLogDao.insertExceptionLog(exceptionLog);


    }
}
