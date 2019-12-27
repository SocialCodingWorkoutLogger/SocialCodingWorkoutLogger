package com.socialcoding.workout.workoutlogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.socialcoding.workout.workoutlogger.entity.Exercise;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{
    List<Exercise> findByWorkoutId(int workoutId);
    Optional<Exercise> findByIdAndWorkoutId(int id, int workoutId);
}
