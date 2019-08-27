package com.example.demo.exception;

public enum CustomizeErrorCode implements  ICustomizeErrorCode{

    QUESTION_NOT_FOUND(2001,"你找的问题已经不存在了，要不换个试试"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NOT_LOGIN(2003,"未登录，请登陆后重试"),
    STS_ERROR(2004,"出错了嗷，稍后再试一次8"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或者不存在"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在");


    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
