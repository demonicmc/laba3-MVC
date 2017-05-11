package com.laba3.controllers;

import com.laba3.pojo.User;
import com.laba3.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by root on 04.05.17.
 */
@Controller
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
//            try {
////                req.getRequestDispatcher("/login.jsp").forward(req, resp);
//            } catch (ServletException e) {
//                logger.info(e);
//            } catch (IOException e) {
//                logger.info(e);
//            }
                  return "login";
        }
        else {
            req.setAttribute("users", users);

//            try {
//                servletContext.getRequestDispatcher("/listUser.jsp")
//                        .forward(req, resp);
//            } catch (ServletException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

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


    @RequestMapping(value = "/exit", method = RequestMethod.POST)
    public void exitPost(@RequestParam(value = "action", required = true) String action,
                           HttpServletRequest req, HttpServletResponse resp){


        if(action != null && !action.isEmpty()){
            if(action.equals("exit")){
                try {
                    req.getRequestDispatcher("/home.jsp").forward(req,resp);
                } catch (ServletException e) {
                    logger.info(e);
                } catch (IOException e) {
                    logger.info(e);
                }

            }
        }

    }
}
