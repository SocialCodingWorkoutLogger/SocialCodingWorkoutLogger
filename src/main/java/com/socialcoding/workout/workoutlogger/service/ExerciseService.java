package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Exercise;

import java.awt.print.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;

public interface ExerciseService {

    Exercise findExerciseByIdAndWorkoutAndUserIds(int exerciseId, int workoutId, int userId);

    Page<Exercise> findExercisesByWorkoutAndUserIds(int workoutId, int userId, Pageable pageable);

    void saveExercise(Exercise exercise);

    void deleteExerciseById(int id);
}
