package model;

public abstract class WorkoutPlan {
    private String workoutType;
    private int duration;
    private boolean completed;
    private String workoutName;

    public WorkoutPlan(String workoutType, int duration, String workoutName) {
        this.workoutType = workoutType;
        this.duration = duration;
        this.completed = false;
        this.workoutName = workoutName;
    }

    public String getWorkoutType() {
        return workoutType;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public double calculateCaloriesBurned() {
        return duration * 5;
    }

    public abstract void showInfo();
}
