package com.hello.community.enums;

public enum NotifitionTypeEnum {
    UNREAD(0),
    READ(1);

    private int status;

    public int getStatus() {
        return status;
    }

    NotifitionTypeEnum(int status) {
        this.status = status;
    }
}
