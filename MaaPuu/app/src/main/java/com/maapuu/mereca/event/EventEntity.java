package com.maapuu.mereca.event;

/**
 * Created by joan on 2016/5/04.
 * 模板
 */
public class EventEntity {
    private int type; //0购物车
    private int position;
    private String msg;

    private String tag;//用于标记事件来自哪个类（消息来源）
    private Object value;//对象，需要什么类型，再强转即可(传递变量为对象，很少用)

    public EventEntity(int type, int position, String msg) {
        this.type = type;
        this.position = position;
        this.msg = msg;
    }

    public EventEntity(String msg, String tag) {
        this.msg = msg;
        this.tag = tag;
    }

    public EventEntity() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
