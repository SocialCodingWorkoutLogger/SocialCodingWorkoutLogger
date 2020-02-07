package com.socialcoding.workout.workoutlogger.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.socialcoding.workout.workoutlogger.entity.Exercise;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{
    Page<Exercise> findByWorkoutId(int workoutId, Pageable pageable);
    Optional<Exercise> findByIdAndWorkoutId(int id, int workoutId);
}
