package controller;

import model.*;

import java.io.*;
import java.util.Scanner;

public class WorkoutController {

    public void saveWorkoutsToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/data/" + user.getName() + "_workouts.txt", false))) { 
            for (WorkoutPlan workout : user.getWorkouts()) {
                String workoutData = workout.getWorkoutName() + ","
                        + workout.getDuration() + ","
                        + (workout instanceof StrengthTraining ? ((StrengthTraining) workout).getReps() : 0) + ","
                        + workout.isCompleted();
                writer.write(workoutData);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving workouts to file: " + e.getMessage());
        }
    }

    public void loadWorkoutsFromFile(User user) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader("src/data/" + user.getName() + "_workouts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] workoutDetails = line.split(",");
                String workoutName = workoutDetails[0];
                int duration = Integer.parseInt(workoutDetails[1]);
                int reps = Integer.parseInt(workoutDetails[2]);
                boolean isCompleted = Boolean.parseBoolean(workoutDetails[3]);

                WorkoutPlan workout;
                if (reps > 0) { 
                    workout = new StrengthTraining(duration, reps, "Unknown", workoutName);
                } else {
                    workout = new CardioWorkout(duration, 0, workoutName); 
                }
                workout.setCompleted(isCompleted);

                user.addWorkout(workout);
            }
        } catch (IOException e) {
            System.out.println("Error reading workouts from file: " + e.getMessage());
        }
    }

    public void addWorkout(User user, Scanner scanner) {
        if (user == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }

        System.out.println("\nChoose Workout Type:");
        System.out.println("1. Strength Training");
        System.out.println("2. Cardio Workout");
        System.out.print("Choose an option: ");
        int workoutChoice = scanner.nextInt();
        scanner.nextLine();

        WorkoutPlan workout = null;

        switch (workoutChoice) {
            case 1 -> {
                System.out.println("\nChoose Muscle Group:");
                System.out.println("1. Biceps");
                System.out.println("2. Triceps");
                System.out.println("3. Back");
                System.out.println("4. Abs");
                System.out.println("5. Chest");
                System.out.println("6. Shoulders");
                System.out.println("7. Legs");
                System.out.print("Choose an option: ");
                int muscleGroupChoice = scanner.nextInt();
                scanner.nextLine();
                String muscleGroup = switch (muscleGroupChoice) {
                    case 1 -> "Biceps";
                    case 2 -> "Triceps";
                    case 3 -> "Back";
                    case 4 -> "Abs";
                    case 5 -> "Chest";
                    case 6 -> "Shoulders";
                    case 7 -> "Legs";
                    default -> "Unknown";
                };

                System.out.println("\nChoose Exercise:");
                String exerciseName;
                switch (muscleGroup) {
                    case "Biceps" -> exerciseName = chooseExercise(scanner, "Bicep Curls", "Hammer Curls",
                            "Concentration Curls", "Cable Curls");
                    case "Triceps" -> exerciseName = chooseExercise(scanner, "Tricep Dips", "Tricep Extensions",
                            "Skull Crushers", "Close-Grip Bench Press");
                    case "Back" ->
                        exerciseName = chooseExercise(scanner, "Pull-Ups", "Rows", "Deadlifts", "Lat Pulldowns");
                    case "Abs" ->
                        exerciseName = chooseExercise(scanner, "Crunches", "Leg Raises", "Planks", "Russian Twists");
                    case "Chest" -> exerciseName = chooseExercise(scanner, "Bench Press", "Chest Fly", "Push-Ups",
                            "Incline Bench Press");
                    case "Shoulders" -> exerciseName = chooseExercise(scanner, "Shoulder Press", "Lateral Raises",
                            "Front Raises", "Face Pulls");
                    case "Legs" ->
                        exerciseName = chooseExercise(scanner, "Squats", "Lunges", "Leg Press", "Calf Raises");
                    default -> exerciseName = "Unknown";
                }

                System.out.print("Enter repetitions: ");
                int reps = scanner.nextInt();
                scanner.nextLine();
                workout = new StrengthTraining(0, reps, muscleGroup, exerciseName);
            }
            case 2 -> {
                System.out.println("\nChoose Cardio Exercise:");
                System.out.println("1. Running");
                System.out.println("2. Cycling");
                System.out.println("3. Swimming");
                System.out.println("4. Rowing");
                System.out.println("5. Jump Rope");
                System.out.println("6. Elliptical");
                System.out.println("7. Stair Climbing");
                System.out.println("8. Hiking");
                System.out.print("Choose an option: ");
                int cardioChoice = scanner.nextInt();
                scanner.nextLine();
                String exerciseName = switch (cardioChoice) {
                    case 1 -> "Running";
                    case 2 -> "Cycling";
                    case 3 -> "Swimming";
                    case 4 -> "Rowing";
                    case 5 -> "Jump Rope";
                    case 6 -> "Elliptical";
                    case 7 -> "Stair Climbing";
                    case 8 -> "Hiking";
                    default -> "Unknown";
                };

                System.out.print("Enter duration (minutes): ");
                int duration = scanner.nextInt();
                scanner.nextLine();

                workout = new CardioWorkout(duration, 0, exerciseName); 
            }

            default -> System.out.println("Invalid workout choice.");
        }

        if (workout != null) {
            user.addWorkout(workout);
            System.out.println("Workout added successfully!");
            saveWorkoutsToFile(user); 
        }
    }

    private String chooseExercise(Scanner scanner, String... exercises) {
        for (int i = 0; i < exercises.length; i++) {
            System.out.println((i + 1) + ". " + exercises[i]);
        }
        System.out.print("Choose an exercise: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return (choice >= 1 && choice <= exercises.length) ? exercises[choice - 1] : "Unknown";
    }

    public void showAndCompleteWorkouts(User user, Scanner scanner) {
        if (user == null) {
            System.out.println("Error: No user is logged in.");
            return;
        }

        System.out.println("\n=== User Workouts ===");
        if (user.getWorkouts().isEmpty()) {
            System.out.println("No workouts available. Please add a workout first.");
            return;
        }

        int index = 1;
        for (WorkoutPlan workout : user.getWorkouts()) {
            String workoutDetails;
            if (workout instanceof StrengthTraining) {
                StrengthTraining strengthWorkout = (StrengthTraining) workout;
                workoutDetails = String.format("%d. %s - %d reps (Completed: %b)", index++,
                        workout.getWorkoutName(), strengthWorkout.getReps(), workout.isCompleted());
            } else {
                workoutDetails = String.format("%d. %s - %d minutes (Completed: %b)", index++,
                        workout.getWorkoutName(), workout.getDuration(), workout.isCompleted());
            }
            System.out.println(workoutDetails);
        }

        System.out.print("\nEnter workout number to mark as completed (0 to exit): ");
        int choice = scanner.nextInt();
        if (choice == 0 || choice > user.getWorkouts().size()) {
            System.out.println("No changes made.");
            return;
        }

        WorkoutPlan selectedWorkout = user.getWorkouts().get(choice - 1);
        if (selectedWorkout.isCompleted()) {
            System.out.println("This workout is already completed.");
        } else {
            selectedWorkout.setCompleted(true);
            double calories = selectedWorkout.calculateCaloriesBurned();
            user.updateCaloriesBurned(calories);
            System.out.printf("Workout marked as completed! You burned %.2f kcal.\n", calories);
            saveWorkoutsToFile(user); 
        }
    }

}
