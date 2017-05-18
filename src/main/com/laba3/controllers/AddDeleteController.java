package com.laba3.controllers;

import com.laba3.pojo.User;
import com.laba3.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by root on 05.05.17.
 */

@Controller
public class AddDeleteController {

    private PasswordEncoder encoder;

    private final UserService userService;

    final static Logger logger = Logger.getLogger(AddDeleteController.class);

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public AddDeleteController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String deleteGet(){
        return "admin";
    }


    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteUser(@RequestParam(value = "delete", required = true)
                                         String delete, HttpServletResponse resp){


        try {
            if (delete != null && !delete.equals("")) {

                int idUser = Integer.parseInt(delete);
                userService.deleteByIdUser(idUser);

                resp.sendRedirect("/admin");

            } else
                resp.getWriter().print("error");
        } catch (Exception e){
            logger.info(e);
        }

    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String add(){
        return "add";
    }

    @PreAuthorize(value = "hasRole('ROLE_ADMIN')")

    @RequestMapping(value ="/add" , method = RequestMethod.POST)
    public void addUser(@RequestParam(value = "action", required = true) String action,
                        @RequestParam(value = "login", required = true) String login,
                        @RequestParam(value = "password",required = true) String password,
                        @RequestParam(value = "email", required = true) String email,
                        HttpServletResponse resp, HttpServletRequest req){

        try {

            User user = new User();
            user.setLogin(login);
            user.setPassword(encoder.encode(password));
            user.setMail(email);


            logger.info(user.toString());

            if(action != null && !action.isEmpty()){
                if(action.equals("добавить")){
                    if(userService.addUser(user) == -1) {
                        try {
                            resp.getWriter().print("error");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            resp.sendRedirect("/admin");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            else
            {
                if(userService.addUser(user) == -1) {
                    try {
                        resp.getWriter().print("error");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        resp.sendRedirect("/");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (NullPointerException e){
            logger.info(e);
        }

    }

    @PreAuthorize("hasRole('ROLE_ANONYMOUS') ||  isAuthenticated()")
    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String register(){
        return "registration";
    }


    @PreAuthorize("hasRole('ROLE_ANONYMOUS') ||  isAuthenticated()")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public void registration(@RequestParam(value = "action", required = true) String action,
                               @RequestParam(value = "login", required = true) String login,
                               @RequestParam(value = "password",required = true) String password,
                               @RequestParam(value = "email", required = true) String email,
                               HttpServletResponse resp, HttpServletRequest req){

        try {

            User user = new User();
            user.setLogin(login);
            user.setPassword(encoder.encode(password));
            user.setMail(email);


            logger.info(user.toString());

            if(action != null && !action.isEmpty()){
                if(action.equals("Регистрация")){
                    if(userService.addUser(user) == -1) {
                        try {
                            resp.getWriter().print("error");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            resp.sendRedirect("/success");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (NullPointerException e){
            logger.info(e);
        }


    }
    @PreAuthorize("hasRole('ROLE_ANONYMOUS') ||  isAuthenticated()")
    @RequestMapping(value = "/success", method = RequestMethod.GET)
    public String success(){
        return "success";
    }

}
