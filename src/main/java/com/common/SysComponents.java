package com.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter//get方法
@Setter//set方法
public class SysComponents {
    private String id;
    private String title;
    @JsonIgnore
    private byte[] content;
    private String contentStr;
//
    public String getContentStr() {
        if (null!=content){
            contentStr=new String(content);
        }
        return contentStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setContentStr(String contentStr) {
        this.contentStr = contentStr;
    }
}
