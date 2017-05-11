package com.laba3.controllers;

import com.laba3.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by root on 04.05.17.
 */

@Controller
public class LoginController {

    final static Logger logger = Logger.getLogger(LoginController.class);

    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping
//    @RequestMapping("/")
//    public String base(HttpServletRequest req, HttpServletResponse resp, Model model){
//        System.out.println("Session user = " + req.getParameter("userId"));
//        return "login";
//    }



    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String forwardLogin(Model model){
        return "login";
    }

    @RequestMapping(value = "/login" ,method = RequestMethod.POST)
    public String input(@RequestParam(value = "login", required = true) String login,
                        @RequestParam(value = "password", required = true) String password,
                        HttpServletResponse response){
//        String login = req.getParameter("login");
//        String password = req.getParameter("password");
//        HttpSession httpSession = req.getSession();
//        String action = req.getParameter("action");
//        String out = "";

//        if(action != null && !action.isEmpty()){
//            if(action.equals("exit")){
////                httpSession.invalidate();
////                try {
////                    req.getRequestDispatcher("/home.jsp").forward(req,resp);
////                } catch (ServletException e) {
////                    logger.info(e);
////                } catch (IOException e) {
////                    logger.info(e);
////                }
//                    return "home";
//            }
//        } else {
//            System.out.println(userService.authentication(login, password));
            if (userService.authentication(login, password) != null &&
                    !userService.authentication(login, password).getLogin().equals("admin")) {

//                req.getSession().setAttribute("userLogin", login);
                logger.debug("user: " + login + " logged");
//                try {
//                    resp.sendRedirect(req.getContextPath() + "/listUser");
//                } catch (IOException e) {
//                    logger.info(e);
                return "redirect:/listUser";}

             else if (userService.authentication(login, password) != null &&
                    userService.authentication(login, password).getLogin().equals("admin")) {

//                req.getSession().setAttribute("userLogin", login);
                logger.debug("user: " + login + " logged");
//                try {
//                    resp.sendRedirect(req.getContextPath() + "/admin");
//                } catch (IOException e) {
//                    logger.info(e);
//                }
                return "redirect:/admin";

            } else {
//                try {
//                    req.getRequestDispatcher("error.jsp").forward(req, resp);
//                } catch (ServletException e) {
//                    logger.info(e);
//                } catch (IOException e) {
//                    logger.info(e);
//                }
                return "error";
            }
        }
    }






