package com.common;

import com.alibaba.fastjson.JSON;
import com.common.mapper.LogMapper;
import com.ds.CloumnBean;
import com.spring.response.ResponseMsg;
import freemarker.template.Configuration;
import freemarker.template.Template;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCountCallbackHandler;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.StringUtils;
import utils.TimeAreaUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class JdbcService {
   @Autowired
     JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    LogMapper logMapper;

    public static final  String MethodName="method";
    public static final  String GROOVYSTRINGS="groovy";
    public static final  String PARAMS="params";
    public static final  String DATA="data";
//    public  void  test(){
//        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
//        Date date = new Date(System.currentTimeMillis());
//        String  datatime=formatter.format(date);
////        init(jdbcTemplate);
//        List<Map<String, Object>> datalist=groovyquery("select *  from user", new HashMap());
//
//       int  da=datalist.size();
//    }



    public   void query(String tableName ) {



        String sql = "select * from "+tableName;
        RowCountCallbackHandler rcch = new RowCountCallbackHandler();
        jdbcTemplate.query(sql, rcch);
//
//
//
//             System.out.println("column name :" + rcch.getColumnNames()[i]);
//            System.out.println("column type :" + rcch.getColumnTypes()[i]);
    }
    /**
     * 调用springframework 中的JdbcTemplate
     * 传入模板查询
     */
    public  <T> List<T> query(  String courseFile ,Class<T> mappedClass,  Map<String, Object> map) {
        File sqlFile=new File(courseFile);
        String templateString = utils.ZStringUtils.getFileString(sqlFile);

        StringWriter result = new StringWriter();
        Template t = null;
        String sql="";
        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            t.process(map, result);
            sql=result.toString();
            System.out.print("exe sql="+sql);

            List<T> records= jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<T>(mappedClass));

            return records;
        } catch (Exception e) {
            e.printStackTrace();
            if (!map.containsKey("typeerror")){
                Map errMap=new HashMap();
                errMap.put("type","list");
                saveLog(errMap,e);
            }
        }
        return new ArrayList<>();
    }
    public  List<Map<String, Object>> queryMap(  String courseFile ,  Map<String, Object> map) {
        File sqlFile=new File(courseFile);
        String templateString = utils.ZStringUtils.getFileString(sqlFile);

        StringWriter result = new StringWriter();
        Template t = null;
        String sql="";
        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            t.process(map, result);
            sql=result.toString();
            System.out.print("exe sql="+sql);

            List<Map<String, Object>> records= jdbcTemplate.queryForList(sql);

            return records;
        } catch (Exception e) {
            e.printStackTrace();
            if (!map.containsKey("typeerror")){
                Map errMap=new HashMap();
                errMap.put("type","list");
                saveLog(errMap,e);
            }
        }
        return new ArrayList<>();
    }

    public   Object invokeJavaMethod(Map  exeMap ) {
        GroovyClassLoader classLoader = new GroovyClassLoader();
        String classStr=(String) exeMap.remove(GROOVYSTRINGS);
        Class groovyClass = classLoader.parseClass(classStr);
        Object resultMap=new Object();
        try {
            GroovyObject groovyObject = (GroovyObject) groovyClass.newInstance();

            HashMap inputMap=new HashMap();
            inputMap.putAll(exeMap);

            Object result=   groovyObject.invokeMethod((String) exeMap.get(MethodName), inputMap );
            resultMap=result;


        } catch (Exception e) {
            resultMap= e.getMessage();
            e.printStackTrace();

            saveLog(exeMap,e);
        }
        return  resultMap;

    }

    public  Object sql(String sql, Map  params ) {
        String method=(String)params.get("method");
        if (method.contains("select") | method.contains("get")|| method.contains("SELECT")| method.contains("search")) {
            Class  instanClass=null;
               if (params.containsKey("className")){
                   String className=(String)params.get("className");
                   try {
                       instanClass=Class.forName(className);

                   }catch (Exception e){

                       return  new ArrayList<>();
                   }

               }
            return (Object)groovyquery(sql, params,instanClass);
        } else {
            return groovyExe(sql, params);
        }

    }

    /**
     * 调用springframework 中的JdbcTemplate
     * 传入模板执行更新等操作
     */
    public  int groovyExe(String templateString ,   Map mapInput) {
        StringWriter result = new StringWriter();
        Template t = null;
        String sql="";

        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            t.process(mapInput, result);
            sql=result.toString();
            System.out.println(" jdbc_exe_sql=="+sql);
        } catch (Exception e) {
//            if (!mapInput.containsKey("typeerror")){
//                Map errMap=mapInput;
//                errMap.put("type","exec");
//                saveLog(errMap,e);
//            }
            e.printStackTrace();
        }
        int resultNum=-1;
        //开启新事务
        DefaultTransactionDefinition dte= new DefaultTransactionDefinition();
        //设置隔离级别
        dte.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(dte);
        try {
            resultNum= jdbcTemplate.update(sql);
            transactionManager.commit(status);
        } catch (Exception e) {
            //出现异常回滚事务，以免出现脏数据，数据不完整的问题
            transactionManager.rollback(status);
            Map errMap=mapInput;
            errMap.put("type","exec");
            saveLog(errMap,e);
            return -1;
        }
//        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
//        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transStatus = this.transactionManager.getTransaction(transDefinition);
////以下为JdbcTempalte的更新操作（省略具体代码）
//        resultNum= getInstance().template.update(sql);
////最后手动提交事务，可通过try{}catch(){} 进行异常回滚this.transactionManager.rollback(transStatus);
//        this.transactionManager.commit(transStatus);
        return resultNum;
    }
    public   Object groovyquery( String templateString , Map mapInput,Class className) {
        StringWriter result = new StringWriter();

        Template t = null;
        String sql="";
        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            mapInput=  parserData(mapInput);

            t.process(mapInput, result);
            sql=result.toString();
            System.out.print("exe sql="+sql);

            Object records;
//            List<Map<String, Object>> records=jdbcTemplate.queryForList(sql);
             if (null!=className){
               records=jdbcTemplate.query(sql,  BeanPropertyRowMapper.newInstance(className),null);
             }else{
                 List<Map<String, Object>> resultList=jdbcTemplate.queryForList(sql);
                   if (resultList.size()>0){
                       resultList=addValue(resultList);
                   }


                 records=resultList;


             }

            return records;
        } catch (Exception e) {
            e.printStackTrace();
            if (!mapInput.containsKey("typeerror")){
                Map errMap=new HashMap();
                errMap.put("type","list");
                saveLog(errMap,e);
            }
        }
        return new Object();
    }

     private List<Map<String, Object>>  addValue( List<Map<String, Object>> resultList){
         Map<String, Object>  checkMap=resultList.get(0);
         List<String >  keyByteList=new ArrayList<>();
         for ( String key: checkMap.keySet()){
             if ( checkMap.get(key)   instanceof  byte[]){
                 keyByteList.add(key);
             }

         }

         if (keyByteList.size()>0){
             for (Map<String, Object> itemMap:resultList ) {
                 for (String   changeKey:keyByteList ) {
                     byte[]  valueByte=( byte[])itemMap.get(changeKey);
                     try{
                         itemMap.put(changeKey+"Str",new String(valueByte,"UTF-8"));
                     }catch (Exception e){
                         System.out.println(e.getMessage());
                     }
                 }

             }

         }

         return  resultList;
     }

    /**
     * 更新数据
     */
    public   ResponseMsg  updateByFile( String templateString,Map params) throws IOException {
        boolean flag=false;
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        String  datatime=formatter.format(date);

        params.put("updateTime", datatime);
        params=parserData(params);
        int count=  exec(templateString, params);
        String msg="更新成功";
        if (count>0){
            flag=true;
        }else{
            msg="更新失败";
        }
        ResponseMsg responseMsg=new ResponseMsg();
        responseMsg.setSuccess(flag);
        responseMsg.setMsg(msg);
        return  responseMsg;
    }
    /**
     * 调用springframework 中的JdbcTemplate
     * 传入模板执行更新等操作
     */
    public  int exec(String templateString , Map<String, Object> map) {
//        String templateString = ZStringUtils.getFileString(sqlFile);
        StringWriter result = new StringWriter();
        Template t = null;
        String sql="";
        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            t.process(map, result);
            sql=result.toString();
            System.out.println(" jdbc_exe_sql=="+sql);
        } catch (Exception e) {
            if (!map.containsKey("typeerror")){
                Map errMap=map;
                errMap.put("type","exec");
                saveLog(errMap,e);
            }
            e.printStackTrace();
        }
        int resultNum=-1;
        //开启新事务
        DefaultTransactionDefinition dte= new DefaultTransactionDefinition();
        //设置隔离级别
        dte.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(dte);
        try {
            resultNum= jdbcTemplate.update(sql);
            transactionManager.commit(status);
        } catch (Exception e) {
            //出现异常回滚事务，以免出现脏数据，数据不完整的问题
            transactionManager.rollback(status);
            Map errMap=map;
            errMap.put("type","exec");
            saveLog(errMap,e);
            return -1;
        }
//        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
//        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transStatus = this.transactionManager.getTransaction(transDefinition);
////以下为JdbcTempalte的更新操作（省略具体代码）
//        resultNum= getInstance().template.update(sql);
////最后手动提交事务，可通过try{}catch(){} 进行异常回滚this.transactionManager.rollback(transStatus);
//        this.transactionManager.commit(transStatus);
        return resultNum;
    }
    /**
     * 替换掉特殊符号
     */
    public String replaceSys(String sql){

        if (sql.contains(">=")){
            sql=sql.replace(">=","&gt;=");
//            sql=sql.replace(">=","<![CDATA[ >= ]]>");
        }
        if (sql.contains("<=")){
            sql=sql.replace("<=","&lt;=");
        }

        return  sql;
    }
    public   void saveLog(Map msgMap,Exception e){
         if (null==e){
             return ;
         }
        String errorContent=e.getMessage();
        String title="";
        if (null!=e&&null!=errorContent){
            if (errorContent.length()>20){
                title=errorContent.substring(0,20);
            }else{
                title=errorContent;
            }
            errorContent=errorContent.replace("'", "\"");
            msgMap.put("content", errorContent);

            msgMap.put("title",title);
            msgMap.put("name",title);
        }

//        msgMap.put("id", UUID.randomUUID()+"" );
        msgMap.put("createtime", TimeAreaUtils.getTimeNow() );
        msgMap.put("typeerror", "savelog" );
//        msgMap= parserData(msgMap);

        ExceptionLog  log=new ExceptionLog();
        log.setId(UUID.randomUUID()+"");
        log.setTitle(title);
        if (!StringUtils.isEmpty(errorContent)){
            log.setContent(errorContent.getBytes());
        }
        log.setCreatetime(TimeAreaUtils.getTimeNow());
        logMapper.save(log);
    }
    /**
     * 调用springframework 中的JdbcTemplate
     * 传入模板获取
     */
    public  <T> T get(String courseFile , Class<T> mappedClass, String id) {
        File sqlFile=new File(courseFile);
        String templateString = utils.ZStringUtils.getFileString(sqlFile);

        StringWriter result = new StringWriter();
        Template t = null;
        String sql="";
        Map  getMap=new HashMap();
        getMap.put("id","'"+id+"'");
        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            t.process(getMap, result);
            sql=result.toString();
            System.out.print("exe sql="+sql);
        } catch (Exception e) {
            e.printStackTrace();
//            if (!map.containsKey("typeerror")){
            Map errMap=new HashMap();
            errMap.put("type","list");
            saveLog(errMap,e);
//            }
        }
        List<T>    resultList= jdbcTemplate.query(sql, new Object[]{}, new BeanPropertyRowMapper<T>(mappedClass));
        T  reusltData=null;
        if (resultList.size()>0){
            reusltData= resultList.get(0);
        }

        return   reusltData;
        //        return template.query(sql, new Object[]{}, new BeanPropertyRowMapper<T>(mappedClass));
    }

    public  int groovyUpdate(  Map mapInput) {

        StringWriter result = new StringWriter();
        Template t = null;

        String templateString ="";

        String tableName=(String)mapInput.get("tableName");
        String sqlColumn = "select * from "+tableName;

        Map  createData=(Map)mapInput.get("data");


        RowCountCallbackHandler rcch = new RowCountCallbackHandler();
        jdbcTemplate.query(sqlColumn, rcch);
        String[]  columnNames=rcch.getColumnNames();
        int[]  columnTypes=rcch.getColumnTypes();

        int hint=0;
        List<CloumnBean>  createConfigList=new ArrayList();
        for (int i=0;i<rcch.getColumnNames().length;i++  ) {
            String  columnName=columnNames[i];
            if("id".equals(columnName)){
                continue;
            }
            if(createData.containsKey(columnName)){
                CloumnBean columnNameMap=new CloumnBean();
                columnNameMap.setName(columnName);
                columnNameMap.setType(columnTypes[i]);
                createConfigList.add(columnNameMap);
            }

        }
        String sql = "UPDATE "+tableName+" set ";

        for (int i=0;i<createConfigList.size();i++  ) {
            CloumnBean columnNameMap=createConfigList.get(i);
            String  name=columnNameMap.getName();
            if (i!=0&i!=createConfigList.size()){
                sql=sql+",";
            }
            sql=sql+name+"="+ "'"+createData.get(name)+"'";



        }

        sql=sql+" where id='"+createData.get("id")+"' ";
        try {
            Reader reader = new StringReader(sql);
            t = new Template("test", reader, new Configuration());
            t.process(createData, result);
            sql=result.toString();
            System.out.println(" jdbc_exe_sql=="+sql);
        } catch (Exception e) {
//            if (!createData.containsKey("typeerror")){
//                Map errMap=createData;
//                errMap.put("type","exec");
//                saveLog(errMap,e);
//            }
            e.printStackTrace();
        }
        int resultNum=-1;
        //开启新事务
//        DataSourceTransactionManager transactionManager = ioc.getBean(
//                "transactionManager", DataSourceTransactionManager.class);//
        DefaultTransactionDefinition dte= new DefaultTransactionDefinition();
        //设置隔离级别
        dte.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(dte);
        try {
            resultNum= jdbcTemplate.update(sql);
            transactionManager.commit(status);
        } catch (Exception e) {
            //出现异常回滚事务，以免出现脏数据，数据不完整的问题
            transactionManager.rollback(status);
            Map errMap=new HashMap();
            errMap.put("type","exec");
            saveLog(errMap,e);
            return -1;
        }
//        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
//        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transStatus = this.transactionManager.getTransaction(transDefinition);
////以下为JdbcTempalte的更新操作（省略具体代码）
//        resultNum= template.update(sql);
////最后手动提交事务，可通过try{}catch(){} 进行异常回滚this.transactionManager.rollback(transStatus);
//        this.transactionManager.commit(transStatus);
        return resultNum;
    }




    /**
     * 调用springframework 中的JdbcTemplate
     * 传入模板执行更新等操作
     *
     */
    public  Map groovyCreate(  Map mapInput) throws Exception {

        Map resultMap=new HashMap();
        StringWriter result = new StringWriter();
        Template t = null;

        String templateString ="";

        String tableName=(String)mapInput.get("tableName");
        String sqlColumn = "select * from "+tableName;

        Object  createDataObject=mapInput.get("data");
        if(null==createDataObject){
            throw new Exception("data 为空");
        }
        Map  createData;

        if (createDataObject instanceof Map){
            createData=(Map)mapInput.get("data");
        }else{
            createData=(Map) JSON.parseObject(createDataObject.toString(),Map.class);

        }
        createData.remove("method");
        createData.remove("serviceName");
        RowCountCallbackHandler rcch = new RowCountCallbackHandler();
        jdbcTemplate.query(sqlColumn, rcch);
        List<String>  columnList=new ArrayList( createData.keySet());
        String sql = "INSERT INTO "+tableName+"(";

        for (String  columnKey:columnList) {
            sql=sql+columnKey+",";
        }
        sql=sql+"id  ) VALUES (";
        for (String  columnKey:columnList) {
            Object  columnValue=createData.get(columnKey);

            if (columnValue instanceof String ){
                sql=sql+ "'"+columnValue+"',";
            }else{
                sql=sql+ columnValue+",";

            }

        }
        String  id= UUID.randomUUID().toString();
        sql=sql+"'"+id+"') ";

        try {
            Reader reader = new StringReader(sql);
            t = new Template("test", reader, new Configuration());
            t.process(createData, result);
            sql=result.toString();
            System.out.println(" jdbc_exe_sql=="+sql);
        } catch (Exception e) {
//            if (!createData.containsKey("typeerror")){
//                Map errMap=createData;
//                errMap.put("type","exec");
//                saveLog(errMap,e);
//            }
            e.printStackTrace();
        }
        int resultNum=-1;
        //开启新事务
//        DataSourceTransactionManager transactionManager = ioc.getBean(
//                "transactionManager", DataSourceTransactionManager.class);//
        DefaultTransactionDefinition dte= new DefaultTransactionDefinition();
        //设置隔离级别
        dte.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(dte);
        try {
            resultNum= jdbcTemplate.update(sql);
            transactionManager.commit(status);
        } catch (Exception e) {
            //出现异常回滚事务，以免出现脏数据，数据不完整的问题
            transactionManager.rollback(status);
            Map errMap=new HashMap();
            errMap.put("type","exec");
            saveLog(errMap,e);
            resultMap.put("error",e.getMessage());
            return  resultMap;
        }
//        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
//        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transStatus = this.transactionManager.getTransaction(transDefinition);
////以下为JdbcTempalte的更新操作（省略具体代码）
//        resultNum= template.update(sql);
////最后手动提交事务，可通过try{}catch(){} 进行异常回滚this.transactionManager.rollback(transStatus);
//        this.transactionManager.commit(transStatus);
        return  resultMap;
    }
    /**
     * 记录日志
     */
    public int execLogError(String templateString , Map<String, Object> map) {
        StringWriter result = new StringWriter();
        Template t = null;
        String sql="";
        try {
            Reader reader = new StringReader(templateString);
            t = new Template("test", reader, new Configuration());
            t.process(map, result);
            sql=result.toString();

            System.out.println(" jdbc_exe_sql=="+sql);

        } catch (Exception e) {
//            if (!map.containsKey("typeerror")){
//                Map errMap=map;
//                errMap.put("type","exec");
//                saveLog(errMap,e);
//
//            }
            e.printStackTrace();
        }

        int resultNum=-1;

//开启新事务
//        DataSourceTransactionManager transactionManager = ioc.getBean(
//                "transactionManager", DataSourceTransactionManager.class);//
        DefaultTransactionDefinition dte= new DefaultTransactionDefinition();
        //设置隔离级别
        dte.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus status = transactionManager.getTransaction(dte);
        try {
            resultNum= jdbcTemplate.update(sql);
            transactionManager.commit(status);
        } catch (Exception e) {
            //出现异常回滚事务，以免出现脏数据，数据不完整的问题
            transactionManager.rollback(status);
//            Map errMap=map;
//            errMap.put("type","exec");
            //saveLog(errMap,e);
            return -1;
        }
//        DefaultTransactionDefinition transDefinition = new DefaultTransactionDefinition();
//        transDefinition.setPropagationBehavior(DefaultTransactionDefinition.PROPAGATION_REQUIRES_NEW);
//        TransactionStatus transStatus = this.transactionManager.getTransaction(transDefinition);
////以下为JdbcTempalte的更新操作（省略具体代码）
//        resultNum= template.update(sql);
////最后手动提交事务，可通过try{}catch(){} 进行异常回滚this.transactionManager.rollback(transStatus);
//        this.transactionManager.commit(transStatus);
        return resultNum;
    }

    /**
     * 转换map为查询语句
     */
    public  Map<String,Object>  parserData( Map<String,Object>  data){
        Iterator<String>  keys=data.keySet().iterator();
        Map  newData=new HashMap();
        while (keys.hasNext()){
            String  key=keys.next();
            String  value=data.get(key).toString();
            String newValue=value;
            if (!isNumeric1(value)){
//                     newValue= "\""+value+"\"";
                newValue= "'"+value+"'";
            }
                  if(null!=value){
            newData.put(key,newValue);
                  }

        }
        return  newData;
    }
    /**
     * 判断是否有数字
     */
    public boolean isNumeric1(String str) {
        Pattern pattern =Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

}
