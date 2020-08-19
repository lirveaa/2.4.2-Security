package org.javamentor.spring.controller;

import org.javamentor.spring.model.Role;
import org.javamentor.spring.model.User;
import org.javamentor.spring.service.RoleService;
import org.javamentor.spring.service.RoleServiceImpl;
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

    @Autowired
    public RoleService roleService;

    public UserController() {
    }

    @GetMapping("/index")
    public String indexPage() {

        return "index";
    }

//    @GetMapping("/start")
//    public String startMVCApp(ModelMap modelMap) {
//        List<User> userList = userService.usersList();
////        System.out.println("\n====== Called startMVCApp method() from List: ======");
////        for (int i = 0; i < userList.size(); i++) {
////            System.out.println(userList.get(i));
////            Set<Role> roleSet = userList.get(i).getRoles();
////            for (Role thisRole: roleSet ) {
////                System.out.println("    Role: " + thisRole);
////            }
////        }
//        modelMap.addAttribute("listUsers", userList);
//        return "start";
//    }

//    @GetMapping("/create")
//    public String newUserForm(Map<String, Object> model) {
//        model.put("user", new User());
//        return "create_user";
//    }

    @GetMapping("/userinfo")
    public String mainUserForm(Map<String, Object> model) {
        return "/user/user_page";
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
        return "redirect:/start";
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
        return "redirect:/start";
    }

//    @GetMapping("/delete")
//    public String deleteUserForm(@RequestParam(name = "id", defaultValue = "1") long id) {
//        userService.deleteUser(id);
//        return "redirect:/user/start";
//    }
//
//    private void insertDataToDatabase() {
//        System.out.println("\nInserting data ....");
//
//        Role roleUser = new Role(1L, "ROLE_USER");
//        Role roleAdmin = new Role(2L, "ROLE_ADMIN");
//
//
//        Set<Role> set1 = new HashSet<Role>();
//        set1.add(roleUser);
//        set1.add(roleAdmin);
//
//        Set<Role>  set2 = new HashSet<>();
//        set2.add(roleAdmin);
//
//        Set<Role> set3 = new HashSet<>();
//        set3.add(roleUser);
//
//
//        User marlo = new User("Marlon", "pass", 1955, set1);
//        User john = new User("Elton", "Pass1", 1960, set2);
//        User jackson = new User("Michael", "password", 1965, set3);
//        User jagger = new User("Mick", "pass", 1956, set3);
//        User tiger = new User("test", "test", 1970, set2);
//
//        marlo.getRoles().add(roleAdmin);
//
//
//        userService.createNewUser(marlo);
//        userService.createNewUser(john);
//        userService.createNewUser(jackson);
//        userService.createNewUser(jagger);
//        userService.createNewUser(marlo);
//        userService.createNewUser(tiger);
//
//        System.out.println("\n====== List: ======");
//        List<User> userList = userService.usersList();
//        for (User anUser: userList) {
//            System.out.println("--------------------------");
//            System.out.println("User id " + anUser.getId());
//            System.out.println(anUser);
//            System.out.println("============= Now trying to get roles... =============");
//            User aUser = userService.getUser(anUser.getLogin());
//            Set<Role> roleRes = aUser.getRoles();
//            for (Role itRole: roleRes) {
//                System.out.print("Role_id = " + itRole.getId() + " " + itRole.getRole());
//                System.out.println(" auth " + itRole.getAuthority());
//            }
//            System.out.println(" ================ End get Roles... ==============\n");
//        }
//
//    }
}

