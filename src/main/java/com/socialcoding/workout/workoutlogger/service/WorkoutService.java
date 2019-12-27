package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Workout;

import java.util.List;

public interface WorkoutService {

        Workout findWorkoutById(int workoutId);

        Workout findWorkoutByIdAndUserId(int workoutId, int userId);

        List<Workout> findWorkoutsByUserId(int userId);

        void saveWorkout(Workout workout);

        void deleteWorkoutById(int id);

        boolean existsById(int id);

        boolean existsByIdAndUserId(int workoutId, int userId);

}
