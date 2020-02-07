package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.User;
import com.socialcoding.workout.workoutlogger.entity.Workout;
import com.socialcoding.workout.workoutlogger.repository.UserRepository;
import com.socialcoding.workout.workoutlogger.repository.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service("workoutService")
public class WorkoutServiceImpl {
    @Autowired
    private WorkoutRepository workoutRepository;

    public Workout findWorkoutById(int id) {
        Optional found = workoutRepository.findById(id);
        if(found.isPresent()) {
            Workout workout = (Workout) found.get();
            return workout;
        }
        return null;
    }

    public Workout findWorkoutByIdAndUserId(int workoutId, int userId) {
        Optional found = workoutRepository.findByIdAndUserId(workoutId, userId);
        if(found.isPresent()) {
            Workout workout = (Workout) found.get();
            return workout;
        }
        return null;
    }

    public Page<Workout> findWorkoutsByUserId(int userId, Pageable pageable) {
        Page<Workout> workouts = workoutRepository.findByUserId(userId, pageable);
        return workouts;
    }

    public void saveWorkout(Workout workout) {
        workoutRepository.save(workout);
    }

    public void deleteWorkoutById(int id) {
        workoutRepository.deleteById(id);
    }

    public boolean existsById(int id) {
        return workoutRepository.existsById(id);
    }

    public boolean existsByIdAndUserId(int workoutId, int userId) {
        return workoutRepository.existsByIdAndUserId(workoutId, userId);
    }

}
