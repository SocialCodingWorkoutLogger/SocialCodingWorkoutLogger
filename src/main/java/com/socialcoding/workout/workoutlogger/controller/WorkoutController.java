package com.socialcoding.workout.workoutlogger.controller;

import com.socialcoding.workout.workoutlogger.entity.Workout;
import com.socialcoding.workout.workoutlogger.exception.CustomErrorType;
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
import java.util.List;

@RestController
public class WorkoutController {
        private static final Logger logger =
                LoggerFactory.getLogger(WorkoutController.class);

        @Autowired
        WorkoutServiceImpl workoutService;

        @GetMapping(value = "/workout")
        public ResponseEntity<List<Workout>> listWorkouts() {
            List<Workout> workouts = workoutService.findAllWorkouts();
            if (workouts.isEmpty()) {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<List<Workout>>(workouts, HttpStatus.OK);
        }

        @GetMapping(value = "/workout/{id}")
        public ResponseEntity<?> getWorkout(@PathVariable("id") int id) {
            logger.info("Fetching Workout with id {}", id);
            Workout workout = workoutService.findWorkoutById(id);
            if (workout == null) {
                logger.error("Workout with id {} not found.", id);
                return new ResponseEntity(new CustomErrorType("Workout with id " + id
                        + " not found"), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<Workout>(workout, HttpStatus.OK);
        }

        @PostMapping(value = "/workout")
        public ResponseEntity<?> createWorkout(@RequestBody Workout workout, UriComponentsBuilder uriBuilder) {
            logger.info("Creating Workout : {}", workout);

//            if (workoutService.findByWorkout(user.getUsername()) != null) {
//                logger.error("Unable to create. A User with name {} already exist", user.getUsername());
//                return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
//                        user.getUsername() + " already exist."),HttpStatus.CONFLICT);
//            }
            workoutService.saveWorkout(workout);

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(uriBuilder.path("/workout/{id}").buildAndExpand(workout.getId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }

        @PutMapping(value = "/workout/{id}")
        public ResponseEntity<?> updateWorkout(@PathVariable("id") int id, @RequestBody Workout workout) {
            logger.info("Updating Workout with id {}", id);

            Workout currentWorkout = workoutService.findWorkoutById(id);

            if (currentWorkout == null) {
                logger.error("Unable to update. Workout with id {} not found.", id);
                return new ResponseEntity(new CustomErrorType("Unable to update. Workout with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
            }

            workout.setId(id);
            workoutService.saveWorkout(workout);
            return new ResponseEntity<Workout>(workout, HttpStatus.OK);
        }

        @DeleteMapping(value = "/workout/{id}")
        public ResponseEntity<?> deleteWorkout(@PathVariable("id") int id) {
            logger.info("Deleting Workout with id {}", id);

            Workout workout = workoutService.findWorkoutById(id);
            if (workout == null) {
                logger.error("Unable to delete. Workout with id {} not found.", id);
                return new ResponseEntity(new CustomErrorType("Unable to delete. Workout with id " + id + " not found."),
                        HttpStatus.NOT_FOUND);
            }
            workoutService.deleteWorkoutById(id);
            return new ResponseEntity<Work>(HttpStatus.NO_CONTENT);
        }
}

