package model;

import java.util.ArrayList;
import java.util.List;

public class PremiumUser implements User {

    private String name;
    private String password;
    private int age;
    private double weight;
    private double height;
    private List<WorkoutPlan> workouts;
    private double totalCaloriesBurned;
    private String membershipId;
    private String membershipStatus;

    public PremiumUser(String name, String password, int age, double weight, double height, String membershipId) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.weight = weight;
        this.height = height;
        this.workouts = new ArrayList<>();
        this.totalCaloriesBurned = 0;
        this.membershipId = membershipId;
        this.membershipStatus = "Active";
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public double getWeight() {
        return weight;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double calculateBMI() {
        return weight / Math.pow(height / 100, 2);
    }

    @Override
    public void addWorkout(WorkoutPlan workout) {
        workouts.add(workout);
    }

    @Override
    public List<WorkoutPlan> getWorkouts() {
        return workouts;
    }

    @Override
    public void updateCaloriesBurned(double calories) {
        totalCaloriesBurned += calories;
    }

    @Override
    public double getTotalCaloriesBurned() {
        return totalCaloriesBurned;
    }

    @Override
    public String getMembershipInfo() {
        return "Premium User: Membership ID = " + membershipId + ", Status = " + membershipStatus;
    }

    @Override
    public void setMembershipStatus(String status) {
        this.membershipStatus = status;
    }

    public String getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(String membershipId) {
        this.membershipId = membershipId;
    }
}
