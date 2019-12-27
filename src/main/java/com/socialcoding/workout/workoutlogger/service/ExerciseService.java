package com.socialcoding.workout.workoutlogger.service;

import com.socialcoding.workout.workoutlogger.entity.Exercise;

import java.util.List;

public interface ExerciseService {

    Exercise findExerciseByIdAndWorkoutAndUserIds(int exerciseId, int workoutId, int userId);

    List<Exercise> findExercisesByWorkoutAndUserIds(int workoutId, int userId);

    void saveExercise(Exercise exercise);

    void deleteExerciseById(int id);
}
