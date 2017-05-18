package com.laba3.controllers;

import com.laba3.pojo.User;
import com.laba3.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by root on 04.05.17.
 */
@Controller
@PreAuthorize("hasRole('ROLE_USER') || hasRole('ROLE_ADMIN')")
public class ListController {

    private final UserService userService;


    final static Logger logger = Logger.getLogger(ListController.class);

    @Autowired
    public ListController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/listUser", method = RequestMethod.GET)
    public String showList(HttpServletRequest req, HttpServletResponse resp, Model model){

        List<User> users = null;
        try {
            users = userService.getAllUser();
        } catch (ClassNotFoundException e) {
            logger.info(e);
        }

        if (users == null){

                  return "login";
        }
        else {
            req.setAttribute("users", users);
            return "listUser";
        }
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String showListAdmin(HttpServletRequest req, HttpServletResponse resp, Model model){

        List<User> users = null;
        try {
            users = userService.getAllUser();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if (users == null)
            return "login";
        else {
            req.setAttribute("users", users);

           return "admin";
        }

    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }

}
