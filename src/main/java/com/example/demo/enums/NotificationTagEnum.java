package com.example.demo.enums;

public enum NotificationTagEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");

    private int type;
    private String name;

    NotificationTagEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public  static String nameOfType(int type) {
        for (NotificationTagEnum notificationTagEnum : NotificationTagEnum.values()) {
            if (notificationTagEnum.getType() == type) {
                return notificationTagEnum.getName();
             }
        }
        return "";
    }
}

