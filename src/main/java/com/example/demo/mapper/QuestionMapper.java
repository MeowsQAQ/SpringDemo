package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,descri,gmt_create,gmt_modified,creator,tag) values (#{title},#{desc},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);
}
