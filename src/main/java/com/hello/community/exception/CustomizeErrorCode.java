package com.hello.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode{


    QUESTION_NOT_FOUND(5001,"问题不存在,请重新选择!"),
    COMMENT_PARAM_NOT_FOUND(5002,"未选中评论目标!"),
    NO_LOGIN(5003,"登陆异常!"),
    TYPE_PARAMS_WRONG(5005,"评论类型错误!"),
    COMMENT_NOT_FOUND(5004,"操作评论不存在!"),
    COMMENT_IS_EMPTY(5006,"评论不能为空!"),
    READ_NOTIFICATION_FAIL(5007,"无法读取他人信息!"),
    NOTIFICATION_NOT_FOUND(5008,"通知离家出走了!");

    private Integer code;

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
