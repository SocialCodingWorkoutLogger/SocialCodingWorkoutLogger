package com.socialcoding.workout.workoutlogger.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialcoding.workout.workoutlogger.entity.User;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUsername(@Param("username") String username);

}
