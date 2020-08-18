package org.javamentor.spring.controller;

import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserService userService;

    @GetMapping("/edit")
    public String editPage(Long id, ModelMap modelMap) {
        User user = userService.readUser(id);
        System.out.println("Edit from AdminController ");
        System.out.println("User by id " + id);
        System.out.println(user);
        modelMap.addAttribute("user", user);
        return "redirect: /admin/edit_user";
    }

}
