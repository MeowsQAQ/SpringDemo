package com.example.demo.exception;

public enum CustomizeErrorCode implements  ICustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题已经不存在了，要不换个试试");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
