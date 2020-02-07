package com.socialcoding.workout.workoutlogger.controller;

import com.socialcoding.workout.workoutlogger.entity.User;
import com.socialcoding.workout.workoutlogger.entity.Workout;
import com.socialcoding.workout.workoutlogger.exception.CustomErrorType;
import com.socialcoding.workout.workoutlogger.service.UserServiceImpl;
import com.socialcoding.workout.workoutlogger.service.WorkoutServiceImpl;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@RestController
public class WorkoutController {
        private static final Logger logger =
                LoggerFactory.getLogger(WorkoutController.class);

        @Autowired
        WorkoutServiceImpl workoutService;

        @Autowired
        UserServiceImpl userService;

        @GetMapping(value = "/users/{userId}/workouts")
        public ResponseEntity<Page<Workout>> listWorkouts(@PathVariable("userId") int userId,
                                                          Pageable pageable) {
            Page<Workout> workouts = workoutService.findWorkoutsByUserId(userId, pageable);
            if (workouts.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<Page<Workout>>(workouts, HttpStatus.OK);
        }

        @GetMapping(value = "/users/{userId}/workouts/{workoutId}")
        public ResponseEntity<?> getWorkout(@PathVariable("userId") int userId,
                                            @PathVariable("workoutId") int workoutId) {
            logger.info("Fetching Workout with userId {} and workoutId {}", userId, workoutId);
            Workout workout = workoutService.findWorkoutByIdAndUserId(workoutId, userId);
            if (workout == null) {
                logger.error("Workout with userId {} and workoutId {} not found.", userId, workoutId);
                return new ResponseEntity(new CustomErrorType("Workout with userId " + userId
                        + " and workoutId " + workoutId + " not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Workout>(workout, HttpStatus.OK);
        }

        @PostMapping(value = "/users/{userId}/workouts")
        public ResponseEntity<?> createWorkout(@PathVariable("userId") int userId,
                                               @RequestBody Workout workout,
                                               UriComponentsBuilder uriBuilder) {
            User user = userService.findUserById(userId);
            if (user == null) {
                logger.error("User with id {} not found.", userId);

                return new ResponseEntity(new CustomErrorType("User with id " + userId
                        + " not found"), HttpStatus.NOT_FOUND);
            }

            logger.info("Creating Workout : {}", workout);

            workout.setUser(user);
            workoutService.saveWorkout(workout);

            HttpHeaders headers = new HttpHeaders();

            Map<String, Integer> urlParams = new HashMap<String, Integer>();
            urlParams.put("userId", user.getId());
            urlParams.put("workoutId", workout.getId());

            headers.setLocation(uriBuilder.path("/users/{userId}/workouts/{workoutId}").buildAndExpand(urlParams).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }

        @PutMapping(value = "/users/{userId}/workouts/{workoutId}")
        public ResponseEntity<?> updateWorkout(@PathVariable("userId") int userId,
                                               @PathVariable("workoutId") int workoutId,
                                               @RequestBody Workout workout) {
            logger.info("Updating Workout with userId {} and workoutId {}", userId, workoutId);

            Workout currentWorkout = workoutService.findWorkoutByIdAndUserId(workoutId, userId);
            if (currentWorkout == null) {
                logger.error("Unable to update. Workout with userId {} and workoutId {} not found.", userId, workoutId);
                return new ResponseEntity(new CustomErrorType("Unable to update. Workout with userId " + userId
                        + " and workoutId " + workoutId + " not found"), HttpStatus.NOT_FOUND);
            }

            workout.setId(workoutId);
            User user = userService.findUserById(userId);
            workout.setUser(user);

            workoutService.saveWorkout(workout);
            return new ResponseEntity<Workout>(workout, HttpStatus.OK);
        }

        @DeleteMapping(value = "/users/{userId}/workouts/{workoutId}")
        public ResponseEntity<?> deleteWorkout(@PathVariable("userId") int userId,
                                               @PathVariable("workoutId") int workoutId) {
            logger.info("Deleting Workout with userId {} and workoutId {}", userId, workoutId);

            Workout workout = workoutService.findWorkoutByIdAndUserId(workoutId, userId);
            if (workout == null) {
                logger.error("Unable to delete. Workout with userId {} and workoutId {} not found.", userId, workoutId);
                return new ResponseEntity(new CustomErrorType("Unable to delete. Workout with userId " + userId
                        + " and workoutId " + workoutId + " not found"), HttpStatus.NOT_FOUND);
            }
            workoutService.deleteWorkoutById(workoutId);
            return new ResponseEntity<Work>(HttpStatus.NO_CONTENT);
        }
}

