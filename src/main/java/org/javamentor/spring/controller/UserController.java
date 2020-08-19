package org.javamentor.spring.controller;

import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping(value = {"info", "/"})
    public ModelAndView index(@AuthenticationPrincipal User user) {
        System.out.println("Called UserController ");
        System.out.println(user);
        ModelAndView mav= new ModelAndView("/user/info");
        mav.addObject("user", user);
        return mav;

    }

}

