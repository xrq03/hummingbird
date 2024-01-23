package com.tencent.hummingbird.portal.service;

import com.tencent.hummingbird.portal.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tencent.hummingbird.portal.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
public interface ICommentService extends IService<Comment> {
    Comment saveComment(CommentVo commentVo,String username);
}
