package com.publish.groservice.dao.bean;

import com.base.BaseBean;

public class ExamBean extends BaseBean {

    private  String content;
    private  String result_a;
    private  String result_b;
    private  String result_c;
    private  String result_d;
    private  String result_right;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResult_a() {
        return result_a;
    }

    public void setResult_a(String result_a) {
        this.result_a = result_a;
    }

    public String getResult_b() {
        return result_b;
    }

    public void setResult_b(String result_b) {
        this.result_b = result_b;
    }

    public String getResult_c() {
        return result_c;
    }

    public void setResult_c(String result_c) {
        this.result_c = result_c;
    }

    public String getResult_d() {
        return result_d;
    }

    public void setResult_d(String result_d) {
        this.result_d = result_d;
    }

    public String getResult_right() {
        return result_right;
    }

    public void setResult_right(String result_right) {
        this.result_right = result_right;
    }
}
