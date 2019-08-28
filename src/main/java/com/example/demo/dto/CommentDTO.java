package com.example.demo.dto;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;

    private Long parentId;

    private Integer commentTpye;

    private Long commentator;

    private Long gmtCreate;

    private Long gmtModified;

    private Long likeCount;

    private String commentContent;

    private User user;
}
