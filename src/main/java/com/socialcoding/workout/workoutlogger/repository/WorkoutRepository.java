package com.socialcoding.workout.workoutlogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.socialcoding.workout.workoutlogger.entity.Workout;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    Page<Workout> findByUserId(int userId, Pageable pageable);
    Optional<Workout> findByIdAndUserId(int id, int userId);
    boolean existsByIdAndUserId(int id, int userId);
}
