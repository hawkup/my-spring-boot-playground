package com.example.myspringbootplayground.user;

import com.example.myspringbootplayground.user.request.CreateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public User addUser(@RequestBody CreateUserRequest createUserRequest) {
        return userService.createUser(createUserRequest.getName(), createUserRequest.getEmail());
    }

    @GetMapping
    public Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
