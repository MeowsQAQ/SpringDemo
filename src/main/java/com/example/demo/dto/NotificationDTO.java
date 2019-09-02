package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class NotificationDTO {

    private Long id;

    private Long gmtCreate;

    private Integer status;

    private String notifierName;

    private Long notifier;

    private String outerTitle;

    private Long outerid;

    private String typeName;

    private Integer type;


}
