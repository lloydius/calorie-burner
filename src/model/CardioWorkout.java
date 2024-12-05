package model;

public class CardioWorkout extends WorkoutPlan {
    private double distance;
    private String exerciseName;

    public CardioWorkout(int duration, double distance, String exerciseName) {
        super("Cardio", duration, exerciseName);
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    @Override
    public double calculateCaloriesBurned() {
        return super.calculateCaloriesBurned() + distance * 5;
    }

    @Override
    public void showInfo() {
        System.out.println("Exercise: " + exerciseName);
        System.out.println("Distance: " + distance + " km");
        System.out.printf("Calories Burned: %.2f kcal\n", calculateCaloriesBurned());
    }
}
