package com.tencent.hummingbird.portal.service.impl;

import com.tencent.hummingbird.portal.mapper.UserMapper;
import com.tencent.hummingbird.portal.pojo.Comment;
import com.tencent.hummingbird.portal.mapper.CommentMapper;
import com.tencent.hummingbird.portal.pojo.User;
import com.tencent.hummingbird.portal.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tencent.hummingbird.portal.service.ServiceException;
import com.tencent.hummingbird.portal.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @Override
    public Comment saveComment(CommentVo commentVo, String username) {
        User user = userMapper.findUserByUsername(username);
        Integer userId=user.getId();
        Integer answerId=commentVo.getAnswerId();
        String content = commentVo.getContent();
        Comment comment=new Comment()
                .setUserId(userId)
                .setContent(content)
                .setAnswerId(answerId)
                .setUserNickName(user.getNickname())
                .setCreatetime(LocalDateTime.now());
        int num=commentMapper.insert(comment);
        if (num!=1){
            throw ServiceException.busy();
        }
        return comment;
    }
}
