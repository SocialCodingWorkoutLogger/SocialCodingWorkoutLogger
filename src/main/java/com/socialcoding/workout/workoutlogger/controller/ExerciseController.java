package com.socialcoding.workout.workoutlogger.controller;

import com.socialcoding.workout.workoutlogger.entity.Exercise;
import com.socialcoding.workout.workoutlogger.entity.User;
import com.socialcoding.workout.workoutlogger.entity.Workout;
import com.socialcoding.workout.workoutlogger.exception.CustomErrorType;
import com.socialcoding.workout.workoutlogger.service.ExerciseServiceImpl;
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

@RestController
public class ExerciseController {
    private static final Logger logger =
            LoggerFactory.getLogger(ExerciseController.class);

    @Autowired
    private ExerciseServiceImpl exerciseService;

    @Autowired
    private WorkoutServiceImpl workoutService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping(value = "/users/{userId}/workouts/{workoutId}/exercises")
    public ResponseEntity<List<Exercise>> listExercises(@PathVariable("userId") int userId,
                                                      @PathVariable("workoutId") int workoutId) {
        logger.info("Fetching Exercises with userId {} and workoutId {}", userId, workoutId);

        if(!userService.existsById(userId)) {
            logger.error("User with userId {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("User with userId " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        if(!workoutService.existsByIdAndUserId(workoutId, userId)) {
            logger.error("Workout with workoutId {} and userId {} not found.", workoutId, userId);
            return new ResponseEntity(new CustomErrorType("Workout with workoutId " + workoutId
                    + " and userId " + userId + " not found"), HttpStatus.NOT_FOUND);
        }

        List<Exercise> exercises = exerciseService.findExercisesByWorkoutAndUserIds(workoutId, userId);
        if (exercises.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Exercise>>(exercises, HttpStatus.OK);
    }

    @GetMapping(value = "/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<?> getExercise(@PathVariable("userId") int userId,
                                        @PathVariable("workoutId") int workoutId,
                                        @PathVariable("exerciseId") int exerciseId) {
        logger.info("Fetching Exercise with userId {}, workoutId {}, and exerciseId {}",
                userId, workoutId, exerciseId);

        if(!userService.existsById(userId)) {
            logger.error("User with userId {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("User with userId " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        if(!workoutService.existsByIdAndUserId(workoutId, userId)) {
            logger.error("Workout with workoutId {} and userId {} not found.", workoutId, userId);
            return new ResponseEntity(new CustomErrorType("Workout with workoutId " + workoutId
                    + " and userId " + userId + " not found"), HttpStatus.NOT_FOUND);
        }

        Exercise exercise = exerciseService.findExerciseByIdAndWorkoutAndUserIds(exerciseId, workoutId, userId);
        if (exercise == null) {
            logger.error("Exercise with userId {}, workoutId {}, and exerciseId {} not found.",
                    userId, workoutId, exerciseId);
            return new ResponseEntity(new CustomErrorType("Exercise with userId " + userId
                    + ", workoutId " + workoutId + ", and exerciseId " + exerciseId + " not found"),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
    }

    @PostMapping(value = "/users/{userId}/workouts/{workoutId}/exercises")
    public ResponseEntity<?> createWorkout(@PathVariable("userId") int userId,
                                           @PathVariable("workoutId") int workoutId,
                                           @RequestBody Exercise exercise,
                                           UriComponentsBuilder uriBuilder) {
        logger.info("Creating Exercise : {}", exercise);

        if(!userService.existsById(userId)) {
            logger.error("User with userId {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("User with userId " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        if(!workoutService.existsByIdAndUserId(workoutId, userId)) {
            logger.error("Workout with workoutId {} and userId {} not found.", workoutId, userId);
            return new ResponseEntity(new CustomErrorType("Workout with workoutId " + workoutId
                    + " and userId " + userId + " not found"), HttpStatus.NOT_FOUND);
        }

        exercise.setWorkout(workoutService.findWorkoutByIdAndUserId(workoutId, userId));
        exerciseService.saveExercise(exercise);

        HttpHeaders headers = new HttpHeaders();

        Map<String, Integer> urlParams = new HashMap<String, Integer>();
        urlParams.put("userId", userId);
        urlParams.put("workoutId", workoutId);
        urlParams.put("exerciseId", exercise.getId());

        headers.setLocation(uriBuilder.path("/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}").
                buildAndExpand(urlParams).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = "/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<?> updateWorkout(@PathVariable("userId") int userId,
                                           @PathVariable("workoutId") int workoutId,
                                           @PathVariable("exerciseId") int exerciseId,
                                           @RequestBody Exercise exercise) {
        logger.info("Updating Exercise with userId {}, workoutId {}, and exerciseId {}",
                userId, workoutId, exerciseId);

        if(!userService.existsById(userId)) {
            logger.error("User with userId {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("Unable to Update. User with userId " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        if(!workoutService.existsByIdAndUserId(workoutId, userId)) {
            logger.error("Workout with workoutId {} and userId {} not found.", workoutId, userId);
            return new ResponseEntity(new CustomErrorType("Unable to Update. Workout with workoutId " + workoutId
                    + " and userId " + userId + " not found"), HttpStatus.NOT_FOUND);
        }

        Exercise currentExercise = exerciseService
                .findExerciseByIdAndWorkoutAndUserIds(exerciseId, workoutId, userId);
        if (currentExercise == null) {
            logger.error("Unable to update. Exercise with userId {}, workoutId {}, and exerciseId {} not found.",
                    userId, workoutId, exerciseId);
            return new ResponseEntity(new CustomErrorType("Unable to Update. Exercise with userId " + userId
                    + ", workoutId " + workoutId + ", and exerciseId " + exerciseId + " not found"),
                    HttpStatus.NOT_FOUND);
        }

        exercise.setId(exerciseId);
        Workout workout = workoutService.findWorkoutByIdAndUserId(workoutId, userId);
        exercise.setWorkout(workout);

        exerciseService.saveExercise(exercise);
        return new ResponseEntity<Exercise>(exercise, HttpStatus.OK);
    }

    @DeleteMapping(value = "/users/{userId}/workouts/{workoutId}/exercises/{exerciseId}")
    public ResponseEntity<?> deleteWorkout(@PathVariable("userId") int userId,
                                           @PathVariable("workoutId") int workoutId,
                                           @PathVariable("exerciseId") int exerciseId) {
        logger.info("Deleting Workout with userId {} and workoutId {}", userId, workoutId);

        if(!userService.existsById(userId)) {
            logger.error("User with userId {} not found.", userId);
            return new ResponseEntity(new CustomErrorType("Unable to Delete. User with userId " + userId
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        if(!workoutService.existsByIdAndUserId(workoutId, userId)) {
            logger.error("Workout with workoutId {} and userId {} not found.", workoutId, userId);
            return new ResponseEntity(new CustomErrorType("Unable to Delete. Workout with workoutId " + workoutId
                    + " and userId " + userId + " not found"), HttpStatus.NOT_FOUND);
        }

        Exercise exercise = exerciseService
                .findExerciseByIdAndWorkoutAndUserIds(exerciseId, workoutId, userId);
        if (exercise == null) {
            logger.error("Unable to delete. Exercise with userId {}, workoutId {}, and exerciseId {} not found.",
                    userId, workoutId, exerciseId);
            return new ResponseEntity(new CustomErrorType("Unable to Delete. Exercise with userId " + userId
                    + ", workoutId " + workoutId + ", and exerciseId " + exerciseId + " not found"),
                    HttpStatus.NOT_FOUND);
        }

        exerciseService.deleteExerciseById(exerciseId);
        return new ResponseEntity<Work>(HttpStatus.NO_CONTENT);
    }
}
