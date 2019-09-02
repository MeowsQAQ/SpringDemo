package com.example.demo.service;

import com.example.demo.dto.CommentDTO;
import com.example.demo.enums.CommentTypeEnum;
import com.example.demo.enums.NotificationStatusEnum;
import com.example.demo.enums.NotificationTagEnum;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private ExtQuestionMapper extQuestionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ExtCommentMapper extCommentMapper;
    @Autowired
    private NotificationMapper notificationMapper;
    @Transactional
    public void  insert(Comment comment, User commentator){
        if(comment.getParentId()==null ||comment.getParentId()==0){
            throw  new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getCommentType()==null || !CommentTypeEnum.isExist(comment.getCommentType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getCommentType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbcomment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if (dbcomment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbcomment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment= new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1L);
            extCommentMapper.incCommentCount(parentComment);
            //创建通知
            createNotify(comment, dbcomment.getCommentator(), NotificationTagEnum.REPLY_COMMENT, commentator.getName(), question.getTitle(), question.getId());

        }else{
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            //问题问题
            commentMapper.insert(comment);
            question.setCommentCount(1);
            extQuestionMapper.incCommentCount(question);
            //创建通知
            createNotify(comment,question.getCreator(), NotificationTagEnum.REPLY_QUESTION,commentator.getName(),question.getTitle(),question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, NotificationTagEnum notifivitionType, String notifierName, String ourterTitle, Long outerid) {
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notifivitionType.getType());
        notification.setOuterid(outerid);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(ourterTitle);
        notificationMapper.insert(notification);
    }

    public List<CommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria()
                .andParentIdEqualTo(id)
                .andCommentTypeEqualTo(type.getType());
        //倒序排
        commentExample.setOrderByClause("gmt_create desc");
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds = new ArrayList();
        userIds.addAll(commentators);


        // 获取评论人并转换为 Map
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andIdIn(userIds);
        List<User> users = userMapper.selectByExample(userExample);
        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));


        // 转换 comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
