package com.example.demo.hello;

import com.example.demo.dto.CommentCreateDTO;
import com.example.demo.dto.ResultDTO;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                          HttpServletRequest request){

        User user = (User)request.getSession().getAttribute("user");
        if (user ==null) {
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setCommentContent(commentCreateDTO.getCommentContent());
        comment.setCommentType(commentCreateDTO.getCommentType());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment);
        return ResultDTO.okof();
    }
}
