package com.socialcoding.workout.workoutlogger.controller;

import com.socialcoding.workout.workoutlogger.entity.User;
import com.socialcoding.workout.workoutlogger.exception.CustomErrorType;
import com.socialcoding.workout.workoutlogger.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class UserController {

    private static final Logger logger =
            LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}")
    public ResponseEntity<?> getUser(@PathVariable("userId") int userId) {
        logger.info("Fetching User with id {}", userId);
        User user = userService.findUserById(userId);
        if (user == null) {
            logger.error("User with id {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("User with id " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder uriBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.findByUsername(user.getUsername()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/user/{userId}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable("userId") int userId, @RequestBody User user) {
        logger.info("Updating User with id {}", userId);

        User currentUser = userService.findUserById(userId);

        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + userId + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        user.setId(userId);
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") int userId) {
        logger.info("Deleting User with id {}", userId);

        User user = userService.findUserById(userId);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + userId + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(userId);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
