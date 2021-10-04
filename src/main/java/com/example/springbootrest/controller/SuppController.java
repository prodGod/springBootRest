package com.example.springbootrest.controller;


import com.example.springbootrest.model.Role;
import com.example.springbootrest.model.User;

import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class SuppController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public SuppController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @GetMapping("/admin")
    public String findAll(Model model, ModelMap modelMap, Principal principal) {

        List<User> users = userService.findAll();
        model.addAttribute("users", users);
        modelMap.addAttribute("current_user", userService.findByLogin(principal.getName()));
        Set<Role> setOfAllRoles = roleService.findAll();
        model.addAttribute("setOfAllRoles", setOfAllRoles);
        model.addAttribute("roles", new ArrayList<>());

        return "admin/user-list";
    }


    @GetMapping("login")
    public String loginPage(ModelMap model) {

        if (roleService.findAll().isEmpty()) {
            roleService.add(new Role("ROLE_USER"));
            roleService.add(new Role("ROLE_ADMIN"));
        }
        if (userService.findByLogin("admin") == null) {
            Set<Role> adminRoles = Stream.of(roleService.getByName("ROLE_ADMIN"), roleService.getByName("ROLE_USER")).collect(Collectors.toSet());
            User admin = new User("admin", "admin", "admin", "admin", 22, "admin@mail.com", adminRoles);
            userService.update(admin);
        }

        return "login";
    }

    @PostMapping("/user/deleteAcc")
    public String deleteUser(Principal principal) {
        userService.deleteById(userService.findByLogin(principal.getName()).getId());
        return "redirect:/login";
    }

    @GetMapping("user")
    public String userPage(Principal principal, ModelMap modelMap) {
        modelMap.addAttribute("current_user", userService.findByLogin(principal.getName()));
        return "user";
    }

}
