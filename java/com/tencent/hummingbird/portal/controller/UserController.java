package com.tencent.hummingbird.portal.controller;


import com.tencent.hummingbird.portal.pojo.User;
import com.tencent.hummingbird.portal.r.R;
import com.tencent.hummingbird.portal.service.IAnswerService;
import com.tencent.hummingbird.portal.service.IUserService;
import com.tencent.hummingbird.portal.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/portal/user")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping("/get")
    @PreAuthorize("hasAnyAuthority('/v1')")
    public User get(Integer id){
        User user=userService.getById(id);
        return user;
    }

    @GetMapping("/get1/{sid}")
    @PreAuthorize("hasAnyAuthority('/v2')")
    public User get1(@PathVariable("sid") Integer id){
        User user=userService.getById(id);
        return user;
    }

    //     /master
    @GetMapping("/teachers")
    public R<List<User>> gets(){
        return R.ok(userService.getMasters());
    }

    @GetMapping("/me")
    public R<UserVo> me(@AuthenticationPrincipal UserDetails user){
        UserVo userVo = userService.getCurrentUserVo(user.getUsername());
        return R.ok(userVo);
    }
}
