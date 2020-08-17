package org.javamentor.spring.controller;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.RoleEnum;
import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private static boolean isInit = false;

    @Autowired
    public UserService userService;

    public UserController() {
    }

    @GetMapping("/index")
    public String indexPage() {

        return "index";
    }

    @GetMapping("/start")
    public String startMVCApp(ModelMap modelMap) {
        if (!isInit) {
            insertDataToDatabase();
            isInit = true;
        }
        System.out.println("=========== I'm in startMVCApp method() ========");
        List<User> userList = userService.usersList();
        System.out.println("\n====== List: ======");
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i));
            Set<Role> roleSet = userList.get(i).getRoles();
            for (Role thisRole: roleSet ) {
                System.out.println("    Role: " + thisRole);
            }
        }
        modelMap.addAttribute("listUsers", userList);
        return "start";
    }

    @GetMapping("/create")
    public String newUserForm(Map<String, Object> model) {
        model.put("user", new User());
        return "create_user";
    }

    @GetMapping("/find")
    public String findUserByIdForm(Map<String, Object> model) {
        return "find_form";
    }

    @GetMapping("/findResult")
    public ModelAndView findUserResultForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        User user = userService.readUser(id);
        ModelAndView mav = new ModelAndView("find_result_form");
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping(value = "/save")
    public String saveUser(@ModelAttribute("user") User user) {
        userService.createNewUser(user);
        return "redirect:/user/start";
    }

    @GetMapping("/edit")
    public ModelAndView updateUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        ModelAndView mav = new ModelAndView("update_user");
        User user = userService.readUser(id);
        mav.addObject("user", user);
        return mav;
    }

    @PostMapping(value = "/update")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.updateUser(user);
        return "redirect:/user/start";
    }

    @GetMapping("/delete")
    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
        userService.deleteUser(id);
        return "redirect:/user/start";
    }

    private void insertDataToDatabase() {
        System.out.println("Inserting data ....");

        Role roleAdmin = new Role(RoleEnum.ADMIN);
        Role roleUser = new Role(RoleEnum.ROLE_USER);
        Role roleAnon = new Role(RoleEnum.ROLE_ANONYMOUS);

        Set<Role> set1 = new HashSet<>();
        set1.add(roleAdmin);
        set1.add(roleUser);

        Set<Role>  set2 = new HashSet<>();
        set2.add(roleAdmin);

        Set<Role> set3 = new HashSet<>();
        set3.add(roleUser);


        User john = new User("Elton", "Pass1", 1960,  set1);
        User jackson = new User("Michael", "password", 1965, set3);
        User jagger = new User("Mick", "pass", 1956, set3);
        User marlo = new User("Marlon", "pass", 1955, set3);
        User tiger = new User("test", "test", 1970, set2);


        userService.createNewUser(marlo);
        userService.createNewUser(john);
        userService.createNewUser(jackson);
        userService.createNewUser(jagger);
        userService.createNewUser(marlo);
        userService.createNewUser(tiger);

        List<User> userList = userService.usersList();
        System.out.println("\n====== List: ======");
        for (int i = 0; i < userList.size(); i++) {
            System.out.println(userList.get(i));
            Set<Role> roleSet = userList.get(i).getRoles();
            for (Role thisRole: roleSet ) {
                System.out.println("    Role: " + thisRole);
            }
        }

    }
}

