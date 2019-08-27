package com.example.demo.dto;

import lombok.Data;

@Data
public class CommentDTO {
    private Long parentId;
    private String commentContent;
    private Integer commentType;
}
