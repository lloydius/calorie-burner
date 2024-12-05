package model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private int age;
    private double weight;
    private double height;
    private List<WorkoutPlan> workouts;
    private double totalCaloriesBurned;

    public User(String name, String password, int age, double weight, double height) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.workouts = new ArrayList<>();
        this.totalCaloriesBurned = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public double getHeight() {
        return height;
    }

    public double calculateBMI() {
        return weight / Math.pow(height / 100, 2);
    }

    public void addWorkout(WorkoutPlan workout) {
        workouts.add(workout);
    }

    public List<WorkoutPlan> getWorkouts() {
        return workouts;
    }

    public void updateCaloriesBurned(double calories) {
        totalCaloriesBurned += calories;
    }

    public double getTotalCaloriesBurned() {
        return totalCaloriesBurned;

    }
}
