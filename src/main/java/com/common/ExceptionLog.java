package com.common;

import lombok.Getter;
import lombok.Setter;

@Getter//get方法
@Setter//set方法
public class ExceptionLog  {
    private String id;
    private String ip;
    private String path;
    private String className;
    private String methodName;
    private String exceptionType;
    private String exceptionMessage;
    private String title;
    private  byte[] content;
    private  String contentStr;
    private String createtime;

}
