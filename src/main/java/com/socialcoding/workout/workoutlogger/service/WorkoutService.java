package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Workout;

import java.util.List;

public interface WorkoutService {

        Workout findWorkoutById(int id);

        Workout findWorkoutByIdAndUserId(int id);

        List<Workout> findWorkoutsByUserId();

        void saveWorkout(Workout workout);

        void deleteWorkoutById(int id);

}
