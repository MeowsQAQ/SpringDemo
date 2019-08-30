package com.example.demo.mapper;

import com.example.demo.model.Comment;
import com.example.demo.model.CommentExample;
import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface ExtCommentMapper {
    int incCommentCount(Comment record);
}