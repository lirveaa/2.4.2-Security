package org.javamentor.spring.controller;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    public UserService userService;

    @GetMapping("/start")
    public String startAdmin(ModelMap modelMap) {
        List<User> userList = userService.usersList();
        modelMap.addAttribute("listUsers", userList);
        return "/admin/start";
    }


    @GetMapping(value = "/new")
    public ModelAndView addNewUserForm(ModelAndView modelAndView) {
        System.out.println("Add new user form (GET)");
        getNewModelAndView(modelAndView);
        modelAndView.addObject("isAdmin", false);
        modelAndView.addObject("isUser", true);
        modelAndView.setViewName("admin/add_new");
        return modelAndView;
    }

    @PostMapping("/new")
    public String newUser(@RequestParam(name="isAdmin", required = false) boolean isAdmin,
                          @RequestParam(name="isUser", required = false) boolean isUser,
                          @ModelAttribute User user) {
        System.out.println("Add user form (Post)");
        System.out.println(user);
        System.out.println("POST  isAdmin=" + isAdmin + " isUser=" + isUser);
        Set<Role> rolesToAdd = new HashSet<>();
        if (isUser) {
           rolesToAdd.add(new Role(1L, "ROLE_USER"));
        }
        if (isAdmin) {
            rolesToAdd.add(new Role(2L, "ROLE_ADMIN"));
        }
        user.setRoles(rolesToAdd);
        userService.createNewUser(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/edit")
    public ModelAndView editForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        ModelAndView mav = new ModelAndView("admin/update");
        User user = userService.readUser(id);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam(name = "id", defaultValue = "1") long id,
                @ModelAttribute User user) {
        Set<Role>  setOldRoles = userService.readUser(id).getRoles();
        user.setRoles(setOldRoles);
        userService.updateUser(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/start";
    }

    @GetMapping("/search")
    public String findUserByIdForm() {
        return "admin/search_form";
    }

    @PostMapping("/searchResult")
    public ModelAndView findUserResultForm(@RequestParam(name = "id", defaultValue = "1") long id,
                                           ModelAndView mav) {
        User user = userService.readUser(id);
        System.out.println("Search result. id = " + id);
        System.out.println(user);
        mav.setViewName("admin/search_result_form");
        mav.addObject("user", user);
        return mav;
    }

    private void getNewModelAndView(ModelAndView modelAndView) {
        User user = new User();
        user.setLogin("somebody");
        user.setPassword("password");
        System.out.println(user);
        List<Role> listRoles = userService.rolesList();
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("user", user);
    }

}
