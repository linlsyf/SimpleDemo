package com.common;

public class QueryMapBean {

    private byte[] content;
    private String contentStr;

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        if (null!=content){
            contentStr=new String(content);
        }
        this.content = content;
    }

    public String getContentStr() {

        return contentStr;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }

//    @Override
//    public Object put(String s, Object o) {
//        return super.put(s, o);
//    }
//
//    @Override
//    public Object get(Object o) {
//
//       Object result=   super.get(o);
//        if (result instanceof  byte[]){
//            result=  new String((byte[]) result);
//        }
//        return result;
//    }
}
