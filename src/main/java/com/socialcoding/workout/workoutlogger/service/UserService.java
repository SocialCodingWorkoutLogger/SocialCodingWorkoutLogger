package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.User;

import java.util.List;

public interface UserService {

    User findUserById(int id);

    User findByUsername(String name);

    void saveUser(User user);

    void deleteUserById(int id);

    List<User> findAllUsers();

    boolean existsById(int id);

}
