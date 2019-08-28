package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private String commentContent;
    private Integer commentType;
}
