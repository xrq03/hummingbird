package com.tencent.hummingbird.portal.security;

import com.tencent.hummingbird.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: 玛卡巴卡萌♡
 * Date: 2023-04-09
 * Time: 11:29
 */
@Component
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    IUserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.getUserDetails(username);
    }
}
