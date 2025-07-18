package com.giuliopastore.BankApp.controllers;

import com.giuliopastore.BankApp.entities.user.User;
import com.giuliopastore.BankApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{uid}")
    public User getUserByUid(@PathVariable String uid) {
        return userService.getUserByUid(uid);
    }

    @PatchMapping("/{uid}")
    public User updateUser(@PathVariable String uid, @RequestBody User user) {
        return userService.updateUser(uid, user);
    }

    @DeleteMapping("/{uid}")
    public void deleteUser(@PathVariable String uid) {
        userService.deleteUser(uid);
    }
}
