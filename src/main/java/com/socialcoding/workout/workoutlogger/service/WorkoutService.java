package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Workout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface WorkoutService {

        Workout findWorkoutById(int workoutId);

        Workout findWorkoutByIdAndUserId(int workoutId, int userId);

        Page<Workout> findWorkoutsByUserId(int userId, Pageable pageable);

        void saveWorkout(Workout workout);

        void deleteWorkoutById(int id);

        boolean existsById(int id);

        boolean existsByIdAndUserId(int workoutId, int userId);

}
