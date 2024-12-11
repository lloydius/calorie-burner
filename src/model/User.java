package model;

import java.util.List;

public interface User {
    void setName(String name);

    void setPassword(String password);

    void setAge(int age);

    void setWeight(double weight);

    void setHeight(double height);

    String getName();

    String getPassword();

    boolean validatePassword(String password);

    int getAge();

    double getWeight();

    double getHeight();

    double calculateBMI();

    void addWorkout(WorkoutPlan workout);

    List<WorkoutPlan> getWorkouts();

    void updateCaloriesBurned(double calories);

    double getTotalCaloriesBurned();

    String getMembershipInfo();

    void setMembershipStatus(String status);
}
