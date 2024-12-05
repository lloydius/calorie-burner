package model;

public class StrengthTraining extends WorkoutPlan {
    private int reps;
    private String muscleGroup;
    private String exerciseName;

    public StrengthTraining(int duration, int reps, String muscleGroup, String exerciseName) {
        super("Strength Training", duration, exerciseName);
        this.reps = reps;
        this.muscleGroup = muscleGroup;
    }

    public int getReps() {
        return reps;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    @Override
    public double calculateCaloriesBurned() {
        return super.calculateCaloriesBurned() + reps * 0.2;
    }

    @Override
    public void showInfo() {
        System.out.println("Muscle Group: " + muscleGroup);
        System.out.println("Exercise: " + exerciseName);
        System.out.println("Repetitions: " + reps);
        System.out.printf("Calories Burned: %.2f kcal\n", calculateCaloriesBurned());
    }
}
