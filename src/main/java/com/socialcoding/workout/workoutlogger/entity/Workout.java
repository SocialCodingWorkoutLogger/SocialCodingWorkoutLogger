package com.socialcoding.workout.workoutlogger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

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
                ", date=" + date +
                ", durationMinutes=" + durationMinutes +
                ", notes='" + notes + '\'' +
                '}';
    }
}