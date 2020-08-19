package org.javamentor.spring.controller;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.javamentor.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/")
public class StartController {

    private static boolean isInit = true;

    @Autowired
    private UserService userService;

    @GetMapping("hello")
    public String printWelcome(ModelMap model, Principal principal) {
        System.out.println("Start controller called...");
        System.out.println("Principal = " + principal.getName());

        List<String> messages = new ArrayList<>();
        messages.add("Hello!");
        messages.add("I'm Spring MVC-SECURITY application");
        messages.add("5.2.0 version by sep'19 ");
        model.addAttribute("messages", messages);

        if (!isInit) {
            insertDataToDatabase();
            isInit = true;
        }
        return "hello";
    }

//    @GetMapping("login")
//    public String loginPage() {
//        return "login";
//    }

    @GetMapping("log-out-ok")
    public String goToExit() {
        return  "logged_out_form";
    }

    private void insertDataToDatabase() {
        System.out.println("\nInserting data ....");

        Role roleUser = new Role(1L, "ROLE_USER");
        Role roleAdmin = new Role(2L, "ROLE_ADMIN");

        Set<Role> bothSet = new HashSet<Role>();
        bothSet.add(roleUser);
        bothSet.add(roleAdmin);

        Set<Role>  admSet = new HashSet<>();
        admSet.add(roleAdmin);

        Set<Role> userSet = new HashSet<>();
        userSet.add(roleUser);

        User marlo = new User("admin", "admin", 1955, admSet);
        User john = new User("user", "user", 1960, userSet);
        User jackson = new User("boss", "boss", 1965, bothSet);
        User jagger = new User("Ben", "Johnson", 1956, admSet);
        User tiger = new User("Laura", "Crawford", 1970, bothSet);

        userService.createNewUser(marlo);
        userService.createNewUser(john);
        userService.createNewUser(jackson);
        userService.createNewUser(jagger);
        userService.createNewUser(tiger);
    }
}
