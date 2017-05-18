package com.laba3.controllers;

import com.laba3.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by root on 04.05.17.
 */

@Controller
@PreAuthorize("permitAll()")
public class LoginController {

    final static Logger logger = Logger.getLogger(LoginController.class);
    private final UserService userService;


    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String forwardLogin(Model model) {

        return "login";
    }

}





