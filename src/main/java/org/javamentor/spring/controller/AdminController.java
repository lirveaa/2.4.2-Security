package org.javamentor.spring.controller;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

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

    @GetMapping(value = "/add")
    public ModelAndView addUserForm(ModelAndView modelAndView) {
        System.out.println("Add user form");
        User user = new User();
        System.out.println(user);
        List<Role> listRoles = userService.rolesList();
        modelAndView.addObject("roles", listRoles);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("admin/create_del_later");
        return modelAndView;
    }


//    @GetMapping("/add")
//    public String newUserForm(Map<String, Object> model) {
//        model.put("user", new User());
//        return "create_user";
//    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user) {
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

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/admin/start";
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.createNewUser(user);
        return "redirect:/admin/start";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/start";
    }

//    @GetMapping("/add")
//    public String newUserForm(Map<String, Object> model) {
//        model.put("user", new User());
//        return "admin/add";
//    }


//    @GetMapping("/edit")
//    public String editPage(Long id, ModelMap modelMap) {
//        User user = userService.readUser(id);
//        System.out.println("Edit from AdminController ");
//        System.out.println("User by id " + id);
//        System.out.println(user);
//        modelMap.addAttribute("user", user);
//        return "/admin/edit";
//    }

}
