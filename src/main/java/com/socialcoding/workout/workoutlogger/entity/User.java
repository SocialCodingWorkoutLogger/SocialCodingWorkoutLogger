package com.socialcoding.workout.workoutlogger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable =  false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Workout> workouts = new ArrayList<>();

    protected User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
