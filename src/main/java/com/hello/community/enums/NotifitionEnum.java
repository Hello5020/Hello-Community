package com.hello.community.enums;

public enum NotifitionEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论")
    ;
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    NotifitionEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }
}
