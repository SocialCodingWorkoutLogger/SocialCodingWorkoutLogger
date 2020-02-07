package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Exercise;
import com.socialcoding.workout.workoutlogger.entity.Workout;
import com.socialcoding.workout.workoutlogger.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service("exerciseService")
public class ExerciseServiceImpl {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private WorkoutServiceImpl workoutService;

    public Exercise findExerciseByIdAndWorkoutAndUserIds(int exerciseId, int workoutId, int userId) {
        if (!workoutService.existsByIdAndUserId(workoutId, userId)) {
            return null;
        }

        Optional found = exerciseRepository.findByIdAndWorkoutId(exerciseId, workoutId);
        if(found.isPresent()) {
            Exercise exercise = (Exercise) found.get();
            return exercise;
        }
        return null;
    }

    public Page<Exercise> findExercisesByWorkoutAndUserIds(int workoutId, int userId, Pageable pageable) {
        if (!workoutService.existsByIdAndUserId(workoutId, userId)) {
            return Page.empty();
        }

        Page<Exercise> exercises = exerciseRepository.findByWorkoutId(workoutId, pageable);
        return exercises;
    }

    public void saveExercise(Exercise exercise) {
        exerciseRepository.save(exercise);
    }

    public void deleteExerciseById(int id) {
        exerciseRepository.deleteById(id);
    }
}
