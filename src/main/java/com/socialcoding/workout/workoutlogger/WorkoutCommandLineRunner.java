package com.socialcoding.workout.workoutlogger;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.socialcoding.workout.workoutlogger.entity.Exercise;
import com.socialcoding.workout.workoutlogger.entity.Workout;
import com.socialcoding.workout.workoutlogger.repository.ExerciseRepository;
import com.socialcoding.workout.workoutlogger.repository.WorkoutRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.socialcoding.workout.workoutlogger.entity.User;
import com.socialcoding.workout.workoutlogger.repository.UserRepository;

@Component
public class WorkoutCommandLineRunner implements CommandLineRunner{

    private static final Logger log =
            LoggerFactory.getLogger(WorkoutCommandLineRunner.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public void run(String... arg0) throws Exception {
        User user = new User("coder", "password123");
        userRepository.save(user);
        log.info("New User is created : " + user);

        Date date = new Date(2019 - 1900, 8, 20);
        Workout workout = new Workout(user, date, 60, "Best workout ever 11/10");
        workoutRepository.save(workout);
        log.info("New Workout is created : " + workout);

        Exercise exercise = new Exercise("Bench", 5, 5, 110);
        exercise.setWorkout(workout);
        exerciseRepository.save(exercise);

        Optional<User> userWithIdOne = userRepository.findById(1);
        log.info("User is retrieved : " + userWithIdOne);

        List<User> users = userRepository.findAll();
        log.info("All Users : " + users);

        List<Workout> workouts = workoutRepository.findAll();
        log.info("All Workouts : " + workouts);

        Optional<Exercise> exerciseWithIdOne = exerciseRepository.findByIdAndWorkoutId(1, 1);
        log.info("Exercise with id 1 and workout id 1: " + exerciseWithIdOne);

    }

}