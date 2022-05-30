package com.hello.community.enums;

public enum NotifitionEnum {
    REPLY_QUESTION(1,"回复了你的问题"),
    REPLY_COMMENT(2,"回复了你的评论")
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

    public static String nameOfType(int type){
        for (NotifitionEnum notifition:
             NotifitionEnum.values()) {
            if (notifition.getType() == type) {
                return notifition.getName();
            }
        }
        return "";
    }
}
