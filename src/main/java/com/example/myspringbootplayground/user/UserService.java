package com.example.myspringbootplayground.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public User createUser(String name, String email) {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user = userRepository.save(user);
        return user;
    }

    @Transactional
    public User updateUser(Long id, String name, String email) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(name);
        user.setEmail(email);
        user = userRepository.save(user);
        return user;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}
