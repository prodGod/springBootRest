package com.example.springbootrest.controller;

import com.example.springbootrest.model.User;

import com.example.springbootrest.service.RoleService;
import com.example.springbootrest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/v1")
public class  AdminRestController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminRestController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostMapping("/registration")
    public ResponseEntity<User> addUser(@RequestBody User userForm) {

        userForm.setRoles(Stream.of(roleService.getByName("ROLE_USER")).collect(Collectors.toSet()));
        userService.update(userForm);

        return new ResponseEntity<>(userForm, HttpStatus.OK);
    }

    @GetMapping("/all_users")
    public ResponseEntity<List<User>> allUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public User getUserByID(@PathVariable long id) {
        return userService.findById(id);
    }

    @PostMapping("/new_user")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        userService.update(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.update(user.getId(), user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteById(id);
    }


}
