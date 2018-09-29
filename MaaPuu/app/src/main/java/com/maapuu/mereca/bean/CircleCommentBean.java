package com.maapuu.mereca.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dell on 2018/3/29.
 */

public class CircleCommentBean implements Serializable {
    private String circle_comment_id;
    private String uid;
    private String nick_name;
    private String avatar;
    private String comment_praise_num;
    private String create_time;
    private String content;
    private String sub_comment_num;
    private int is_sub_praise;
    private String pid;
    private List<CircleCommentBean> sub_comments;
    private List<SubPraisesBean> sub_praises;

    public String getCircle_comment_id() {
        return circle_comment_id;
    }

    public void setCircle_comment_id(String circle_comment_id) {
        this.circle_comment_id = circle_comment_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getComment_praise_num() {
        return comment_praise_num;
    }

    public void setComment_praise_num(String comment_praise_num) {
        this.comment_praise_num = comment_praise_num;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSub_comment_num() {
        return sub_comment_num;
    }

    public void setSub_comment_num(String sub_comment_num) {
        this.sub_comment_num = sub_comment_num;
    }

    public int getIs_sub_praise() {
        return is_sub_praise;
    }

    public void setIs_sub_praise(int is_sub_praise) {
        this.is_sub_praise = is_sub_praise;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public List<CircleCommentBean> getSub_comments() {
        return sub_comments;
    }

    public void setSub_comments(List<CircleCommentBean> sub_comments) {
        this.sub_comments = sub_comments;
    }

    public List<SubPraisesBean> getSub_praises() {
        return sub_praises;
    }

    public void setSub_praises(List<SubPraisesBean> sub_praises) {
        this.sub_praises = sub_praises;
    }

    public static class SubPraisesBean{
        private String circle_comment_id;
        private String uid;
        private String nick_name;
        private String avatar;
        private String create_time;

        public String getCircle_comment_id() {
            return circle_comment_id;
        }

        public void setCircle_comment_id(String circle_comment_id) {
            this.circle_comment_id = circle_comment_id;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
