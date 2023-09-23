package com.common;

import com.spring.response.MBYViewModel;
import com.spring.response.ResultRespnseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserController {
    @Autowired
    UserManagerService userService ;

    @GetMapping("/api/v1/user/checkIsLogin")
    public MBYViewModel checkIsLogin(@RequestParam Map params) throws Exception {

         return userService.checkIsLogin(params);

    }


    @GetMapping("/api/v1/user/login")
    public MBYViewModel login(String loginId,String pwd) throws Exception {

//        Map params=new HashMap();
//        params.put("loginId",loginId);
//        params.put("pwd",pwd);

        Object result=   userService.login(loginId,pwd);
        return ResultRespnseUtils.getResponseMsg(null,result);
    }

    @GetMapping("/user/logout")
    public MBYViewModel logout(@RequestParam Map params) throws Exception {

        return userService.logout(params);

    }

@GetMapping("/user/isonline")
public MBYViewModel isonline(@RequestParam Map params)  {
        return userService.checkIsLogin(params);
    }


//    @RequestMapping(value = "/list", produces = MediaTypes.JSON_UTF_8)
//    @ResponseBody
//    public MBYViewModel list( ) throws Exception {
//
//        String result= userService.list();
//        MBYViewModel mbyViewModel=new MBYResponseViewModel("200",result);
//        return mbyViewModel;
//}
//    @RequestMapping(value = "/get" ,produces = MediaTypes.JSON_UTF_8)
//    @ResponseBody
//    public MBYViewModel get(@RequestParam Map params, Ztoken ztoken) throws Exception {
//
////        User order=  JSON.parseObject(msg, User.class);
//         return userService.get(params.get("id").toString());
//
//    }
////    @RequestMapping(value = "/add" ,produces = MediaTypes.JSON_UTF_8)
////    @ResponseBody
////    public MBYViewModel add(@RequestParam Map params) throws Exception  {
////        String msg=(String) params.get("msg");
////
////        String reuslt= userService.add(msg);
////        MBYViewModel mbyViewModel=new MBYResponseViewModel("200",reuslt);
////
////        return mbyViewModel;
////    }
//    @RequestMapping(value = "/register" ,produces = MediaTypes.JSON_UTF_8)
//    @ResponseBody
//    public MBYViewModel register(@RequestParam Map params) throws Exception {
//
//       if (!params.containsKey("type")||!params.containsKey("loginId")||!params.containsKey("pwd")){
//
//                     MBYViewModel mbyViewModel=new MBYResponseViewModel("300","账号密码或者注册类型为空");
//                     return mbyViewModel;
//                 }
//        return   userService.register(params);
//    }
//
//    @RequestMapping(value = "/search", produces = MediaTypes.JSON_UTF_8)
//    @ResponseBody
//    public MBYViewModel search(@RequestParam Map params, Ztoken ztoken ) throws Exception {
//
//        Object result= userService.Search(params,ztoken);
//        return ResultRespnseUtils.getResponseMsg(null,result);
//
//    }
////    @RequestMapping(value = "/searchRole", produces = MediaTypes.JSON_UTF_8)
////    @ResponseBody
////    public MBYViewModel searchRole(@RequestParam Map params, Ztoken ztoken ) throws Exception {
////
////        return userService.SearchRole(params,ztoken);
////
////    }
//    @RequestMapping(value = "/update" ,produces = MediaTypes.JSON_UTF_8)
//    @ResponseBody
//    public ResponseMsg update(@RequestParam Map params) throws Exception {
//
//
//        return   userService.updateUser(params);
//    }
//
//
//    @RequestMapping(value = "/remove" ,produces = MediaTypes.JSON_UTF_8)
//    @ResponseBody
//    public MBYViewModel remove(@RequestParam Map params) throws Exception {
//        String msg=(String) params.get("msg");
//        User user=  JSON.parseObject(msg, User.class);
//        String result= UserDao.remove(user);
//        MBYViewModel mbyViewModel=new MBYResponseViewModel("200",result);
//
//        return mbyViewModel;
//    }
////    @RequestMapping(value = "/checkUserExit" ,produces = MediaTypes.JSON_UTF_8)
////    @ResponseBody
////    public MBYViewModel checkUserExit(@RequestParam Map params) throws Exception  {
////        String msg=(String) params.get("msg");
////        User order=  JSON.parseObject(msg, User.class);
////         boolean isExit=  dictService.checkUserExit(order);
////
////        return MbyRespnseUtils.get("",isExit);
////    }


}