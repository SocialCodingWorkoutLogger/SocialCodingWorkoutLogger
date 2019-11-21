package com.socialcoding.workout.workoutlogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.socialcoding.workout.workoutlogger.entity.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {

}
