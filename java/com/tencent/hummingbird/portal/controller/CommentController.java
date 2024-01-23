package com.tencent.hummingbird.portal.controller;


import com.tencent.hummingbird.portal.pojo.Comment;
import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.ICommentService;
import com.tencent.hummingbird.portal.vo.CommentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;
    @PostMapping("/add")
    //                             验证  notnull……                     //错误信息调用
    public R<Comment> postComment(@Validated CommentVo commentVo, BindingResult result,@AuthenticationPrincipal UserDetails userDetails){
        if (result.hasErrors()){
            String msg=result.getFieldError().getDefaultMessage();
            return R.unproecsableEntity(msg);
        }
        Comment comment=commentService.saveComment(commentVo,userDetails.getUsername());
        return R.ok(comment);
    }

}
