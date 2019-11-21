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

    @GetMapping(value = "/user")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") int id) {
        logger.info("Fetching User with id {}", id);
        User user = userService.findUserById(id);
        if (user == null) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @PostMapping(value = "/user")
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder uriBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.findByUsername(user.getUsername()) != null) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {
        logger.info("Updating User with id {}", id);

        User currentUser = userService.findUserById(id);

        if (currentUser == null) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }

        user.setId(id);
        userService.saveUser(user);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @DeleteMapping(value = "/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        logger.info("Deleting User with id {}", id);

        User user = userService.findUserById(id);
        if (user == null) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
