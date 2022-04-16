package com.hello.community.enums;

public enum CommentTypeEnum {
    Question(1),Comment(2);

    CommentTypeEnum(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnum commentType:
             CommentTypeEnum.values()) {
            if (commentType.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }

    private Integer type;
}
