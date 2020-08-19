package org.javamentor.spring.controller;

import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @GetMapping(value = {"infoCall", "/"})
    public ModelAndView index(Principal principal, ModelAndView mav) {
        User user = userService.getUser(principal.getName());
        mav.addObject("user", user);
        mav.setViewName("/user/info");
        return mav;
    }
}

