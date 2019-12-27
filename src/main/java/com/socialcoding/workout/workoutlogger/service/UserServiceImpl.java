package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.User;
import com.socialcoding.workout.workoutlogger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl {
    @Autowired
    private UserRepository userRepository;

    public User findUserById(int id) {
        Optional found = userRepository.findById(id);
        if(found.isPresent()) {
            User user = (User) found.get();
            return user;
        }
        return null;
    }

    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            return user;
        }
        return null;
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public boolean existsById(int id) {
        return userRepository.existsById(id);
    }
}
