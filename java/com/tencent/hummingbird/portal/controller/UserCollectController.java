package com.tencent.hummingbird.portal.controller;


import com.tencent.hummingbird.portal.pojo.Question;
import com.tencent.hummingbird.portal.pojo.UserCollect;
import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.IUserService;
import com.tencent.hummingbird.portal.vo.CollectionVo;
import com.tencent.hummingbird.portal.vo.QuestionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ytsky
 * @since 2023-04-09
 */
@RestController
@RequestMapping("/portal/userCollect")
public class UserCollectController {
    @Autowired
    IUserService userService;
    @GetMapping("/collections")
    public R<List<CollectionVo>> getQuestions(@AuthenticationPrincipal UserDetails user){
        List<CollectionVo> ids = userService.getIds(user.getUsername());
        return R.ok(ids);
    }
}
