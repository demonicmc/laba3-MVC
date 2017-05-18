package com.laba3.controllers.security;


import com.laba3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16.05.17.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {this.userService = userService;}


    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {

        com.laba3.pojo.User userIn = null;
        try {
            userIn =userService.getByLogin(login);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<SimpleGrantedAuthority> authList = new ArrayList<>();
        if(userIn.getRole_id() != 0) {
            authList.add(new SimpleGrantedAuthority("ROLE_USER"));
            if (userIn.getRole_id() == 2) {
                authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }
        UserDetails user = new User(login, userIn.getPassword(),
                true, true, true,
                true, authList);

        return user;
    }

}

