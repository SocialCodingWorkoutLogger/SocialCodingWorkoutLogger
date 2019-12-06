package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Workout;
import org.springframework.stereotype.Service;

import java.util.List;

public interface WorkoutService {

        Workout findWorkoutById(int id);

        void saveWorkout(Workout workout);

        void deleteWorkoutById(int id);

        List<Workout> findAllWorkout();

}
