package com.example.demo.mapper;

import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ExtQuestionMapper {
    int incView(Question record);
    int incCommentCount(Question record);
    List<Question> selectRelated(Question question);
}