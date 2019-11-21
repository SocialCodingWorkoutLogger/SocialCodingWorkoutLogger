package com.socialcoding.workout.workoutlogger.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany
    private List<Exercise> exercises;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column
    private int durationMinutes;

    @Column
    private String notes;

    protected Workout() {}

    public Workout(User user, Date date) {
        this.user = user;
        this.date = date;
    }

    public Workout(User user, Date date, int durationMinutes) {
        this(user, date);
        this.durationMinutes = durationMinutes;
    }

    public Workout(User user, Date date, String notes) {
        this(user, date);
        this.notes = notes;
    }

    public Workout(User user, Date date, int durationMinutes, String notes) {
        this(user, date);
        this.durationMinutes = durationMinutes;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public Date getDate() {
        return date;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public String getNotes() {
        return notes;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", user=" + user +
                ", date=" + date +
                ", durationMinutes=" + durationMinutes +
                ", notes='" + notes + '\'' +
                '}';
    }
}