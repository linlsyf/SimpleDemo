package com.publish.groservice.dao;

import com.base.BaseBussinessDao;


public class GroDao extends BaseBussinessDao {

//
////    public   ResponseMsg  add(String inputString) throws IOException {
////
////       return insert(inputString);
////    }
//    public   ResponseMsg  update(Map params) throws IOException {
//        boolean flag=false;
//        return  super.update(params);
//    }
//    public static  String  remove(ExamBean user) throws IOException {
////        boolean flag=false;
////        SqlSession sqlSession = SqlSessionFactoryUtil.getSession();
////        FavourBeanMapper studentMapper = sqlSession.getMapper(FavourBeanMapper.class);
////
////        int count=studentMapper.delete(user.getId());
////        sqlSession.commit();
////
////        // 释放资源
////        sqlSession.close();
////        if (count>0){
////            flag=true;
////        }
////
////        ResponseMsg responseMsg=new ResponseMsg();
////        responseMsg.setSuccess(flag);
////        String result=JSON.toJSONString(responseMsg);
////        return  result;
//        String result="";
//        return  result;
//    }
//    public   ResponseMsg   list() throws IOException {
//
//          return listAll(ExamBean.class);
////          return listAll(ExamCon.FAVOUR_BASE,FavourBean.class);
////        boolean flag=false;
////
////        String courseFile= BaseDao.getSqlFilePath(DaoCon.USER_BASE,BaseDao.LIST_TYPE);//instance 需要初始化
////        Map<String, Object> map = new HashMap<String, Object>();
////        List<FavourBean>  list=  JdbcTemplateEng.query(courseFile, FavourBean.class,map);
////        ResponseMsg  responseMsg=new ResponseMsg();
////               if (null!=list){
////                   flag=true;
////                   responseMsg.setSuccess(true);
////                   responseMsg.setData(JSONObject.toJSONString(list));
////               }else {
////                   responseMsg.setSuccess(false);
////               }
////        return  responseMsg;
//
//    }
//
//    public   ResponseMsg   search(Map params) throws IOException {
//
//        return   searchPage(params,ExamBean.class);
//
//    }
//    public   ResponseMsg   searchRecord(Map params) throws IOException {
//        String  fileName="SearchRecord.sql";
//        return  searchPageByName(params,ExamBean.class,fileName);
//
//
//
//    }
//    public   ResponseMsg   typeList(Map params) throws IOException {
//        String  fileName="TypeList.sql";
//        return  searchPageByName(params,ExamBean.class,fileName);
//
//
//
//    }
//    public   ResponseMsg   radomExam(Map params) throws IOException {
//        String  fileName="RadomSearch.sql";
//        return  searchPageByName(params,ExamBean.class,fileName);
//
//
//
//    }
//    public   ResponseMsg   updateUserExam(Map params) throws IOException {
//        String  fileName="UpdateUserExam.sql";
//        return  searchPageByName(params,ExamBean.class,fileName);
//
//
//
//    }
//    public   ResponseMsg   createUserExamRecord(Map params) throws IOException {
//        String  fileName="CreateUserExam.sql";
//
//       return    insertByName(params,fileName);
//
//    }
//    public  ResponseMsg get(String id) throws IOException {
//
//
//
//         return  super.get(id,ExamBean.class);
//    }
//
//
//    public String delete(String[] ids) {
//          return  deleteByIds(ids);
//    }
}