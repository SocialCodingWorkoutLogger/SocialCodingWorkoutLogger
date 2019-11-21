package com.socialcoding.workout.workoutlogger.entity;

import javax.persistence.*;

@Entity
public class Exercise {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Column(nullable =  false)
    private String name;

    @Column(nullable = false)
    private int sets;

    @Column(nullable = false)
    private int reps;

    @ManyToOne
    private Workout workout;

    protected Exercise() {}

    public Exercise(String name, int sets, int reps) {
        this.name = name;
        this.sets = sets;
        this.reps = reps;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getSets() {
        return sets;
    }

    public int getReps() {
        return reps;
    }

    public Workout getWorkouts() {
        return workout;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sets=" + sets +
                ", reps=" + reps +
                ", workout=" + workout +
                '}';
    }
}

