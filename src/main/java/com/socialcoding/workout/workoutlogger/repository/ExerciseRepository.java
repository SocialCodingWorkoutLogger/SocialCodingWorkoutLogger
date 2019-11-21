package com.socialcoding.workout.workoutlogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialcoding.workout.workoutlogger.entity.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Integer>{

}
