package com.maapuu.mereca.background.shop.bean;

/**
 * 图文混排bean  用于测试
 */

public class ImgTxtBean {
    int type;
    String content;

    public ImgTxtBean() {
    }

    public ImgTxtBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
