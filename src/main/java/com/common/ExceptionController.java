package com.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionController {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

    @Autowired
    private ExceptionLogService exceptionLogService;

//    @ExceptionHandler(value = NoHandlerFoundException.class)
//    public ModelAndView notHandlerFoundExceptionHandler(HttpServletRequest request, Exception e){
//        return new ModelAndView("redirect:/common/404");
//    }



    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request,Exception e){
        // 输出异常日志
        e.printStackTrace();
        LOGGER.error(e.getMessage());
        // 构建返回数据
        int code = 200;
        String message = "";
        String data = "";
        // 没有权限时得异常，也可以监听 by zero 的异常来代替没有权限时异常
//        if (e instanceof ArithmeticException) {
//            code = 403;
//            message = "";
//            data = "/common/403";
//        }else {
            code = 500;
            message = "服务器错误："+e.getMessage();
            data = "";
//        }
        // 保存异常信息到数据库
        insertExceptionLog(request,e);

        // 判断是否为接口
        // 包装数据返回不同的 modelAndView
        if (isInterface(request)) {
            // 是接口时，返回数据
            ModelAndView modelAndView = new ModelAndView(new MappingJackson2JsonView());
            modelAndView.addObject("code",code);
            modelAndView.addObject("message",message);
            modelAndView.addObject("data",data);
            return modelAndView;
        }else {
            // 不是接口时，直接放回页面
            return new ModelAndView("redirect:" + data);
        }
    }

    private boolean isInterface(HttpServletRequest request){
        // 通过debug调试可以看出request中含有该对象。
        HandlerMethod handlerMethod = (HandlerMethod)request.
                getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");
        // getBeanType()拿到发送请求得类模板（Class），getDeclaredAnnotationsByType(指定注解类模板)通过指定得注解，得到一个数组。
        RestController[] annotations1 = handlerMethod.getBeanType().getDeclaredAnnotationsByType(RestController.class);
        ResponseBody[] annotations2 = handlerMethod.getBeanType().getDeclaredAnnotationsByType(ResponseBody.class);
        ResponseBody[] annotations3 = handlerMethod.getMethod().getAnnotationsByType(ResponseBody.class);
        // 判断当类上含有@RestController或是@ResponseBody或是方法上有@ResponseBody时，则表明该异常是一个接口请求发生的
        return annotations1.length > 0 || annotations2.length>0 || annotations3.length>0?true:false;
    }

    // 保存异常信息
    private void insertExceptionLog(HttpServletRequest request,Exception e){
        ExceptionLog exceptionLog = new ExceptionLog();
        // 将数据封装到ExceptionLog中

        // Ip
        exceptionLog.setIp(request.getRemoteAddr());

        String url1 = request.getRequestURI();
        StringBuffer url2 = request.getRequestURL();

        // url
        exceptionLog.setPath(request.getServletPath());

        // 同上
        HandlerMethod handlerMethod = (HandlerMethod)request.
                getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingHandler");
        // 类名
        exceptionLog.setClassName(handlerMethod.getBeanType().getName());
        // 方法名
        exceptionLog.setMethodName(handlerMethod.getMethod().getName());
        // 异常的类型
        exceptionLog.setExceptionType(e.getClass().getName());
        // 异常消息
        exceptionLog.setExceptionMessage(e.getMessage());

        // 将信息插入数据库
        exceptionLogService.insertExceptionLog(exceptionLog);
    }
}
