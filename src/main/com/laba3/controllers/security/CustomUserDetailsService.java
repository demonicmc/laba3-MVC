package com.laba3.controllers.security;


import com.laba3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 16.05.17.
 */
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private UserService userService;
    private PasswordEncoder encoder;

    @Autowired
    public void setUserService(UserService userService) {this.userService = userService;}

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }


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

//    @Override
//    public Authentication authenticate(Authentication authentication)
//            throws AuthenticationException {
//
//        User user = userService.getByLogin(authentication.getName());
//        String name = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        if (name.equals(user.getLogin()) &&
//                encoder.matches(password, user.getPassword()) && user.getRole_id() == 2) {
//            List<GrantedAuthority> grantedAuths = new ArrayList<>();
//            grantedAuths.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
//            return auth;
//        } else if (name.equals(user.getLogin()) &&
//                encoder.matches(password, user.getPassword()) && user.getRole_id() == 1){
//                List<GrantedAuthority> grantedAuths = new ArrayList<>();
//                grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
//                Authentication auth =
//                        new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
//            return auth;
//        } return null;

//        User user = userService.getByLogin(authentication.getName());
//        if (user == null)
//            throw new UsernameNotFoundException("user not found");
//
//        String password = authentication.getCredentials().toString();
//        if (encoder.matches(password, user.getPassword())) {
//            org.springframework.security.core.userdetails.User springUser = buildUser(user);
//            return new UsernamePasswordAuthenticationToken()
//
//
//        }



//    @Override
//    public boolean supports(Class<?> authentication) {
//        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//    }

//    private static Map<String, UserDetails> userRepository = new HashMap<String, UserDetails>();
//
//    static {
//        GrantedAuthority authorityAdmin = new GrantedAuthorityImpl("ROLE_ADMIN");
//        GrantedAuthority authorityUser = new GrantedAuthorityImpl("ROLE_USER");
//
//        // Create user with authorities
//        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
//        authorities.add(authorityAdmin);
//        authorities.add(authorityUser);
//        String username = "walid";
//        UserDetails user = new UserDetailsImpl(username, "111", authorities);
//        userRepository.put(username, user);
//    }
//
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDetails matchingUser = userRepository.get(username);
//        if (matchingUser == null) {
//            throw new UsernameNotFoundException("Username or password incorrect!");
//        }
//        return matchingUser;
//    }
}

