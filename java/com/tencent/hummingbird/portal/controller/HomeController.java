package com.tencent.hummingbird.portal.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: 玛卡巴卡萌♡
 * Date: 2023-06-24
 * Time: 15:17
 */
@RestController
public class HomeController {

    private GrantedAuthority STUDENT=new SimpleGrantedAuthority("ROLE_STUDENT");
    private GrantedAuthority TEACHER=new SimpleGrantedAuthority("ROLE_TEACHER");
    @GetMapping("/index.html")
    public ModelAndView index(@AuthenticationPrincipal UserDetails user){
        //会自动跳转templates下的index.html
        //System.out.println(123);
        //自动添加ROLE_
        if (user.getAuthorities().contains(STUDENT)){
            System.out.println(123);
            return new ModelAndView("index");
        }else if (user.getAuthorities().contains(TEACHER)){
            System.out.println(12);
            return new ModelAndView("index_teacher");
        }
        return null;
    }
    /*@GetMapping("/index_teacher.html")
    public ModelAndView index_teacher(){
        System.out.println(456);
        return new ModelAndView("index_teacher");
    }*/
}
