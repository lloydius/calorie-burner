package view;

import model.User;
import controller.UserController;
import controller.WorkoutController;
import controller.UpdateProfile;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserController UserController = new UserController();
    private static final WorkoutController WorkoutController = new WorkoutController();
    private static final UpdateProfile updateProfile = new UpdateProfile(UserController);

    public static void main(String[] args) {
        System.out.println("==========================");
        System.out.println("Welcome to CalorieBurner!");
        System.out.println("==========================");

        UserController.loginOrRegister(scanner);

        while (true) {
            System.out.println("==========================");
            System.out.println("Main Menu");
            System.out.println("==========================");
            System.out.println("1. Display User Info");
            System.out.println("2. Update User Info");
            System.out.println("3. Add Workout Plan");
            System.out.println("4. Show Workouts");
            System.out.println("5. Logout");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    User user = UserController.getLoggedInUser();
                    if (user != null) {
                        System.out.println("=== User Info ===");
                        System.out.println("Name: " + user.getName());
                        System.out.println("Age: " + user.getAge());
                        System.out.println("Weight: " + user.getWeight() + " kg");
                        System.out.println("Height: " + user.getHeight() + " cm");
                        System.out.printf("BMI: %.2f\n", user.calculateBMI());
                        System.out.printf("Total Calories Burned: %.2f kcal\n", user.getTotalCaloriesBurned());
                    } else {
                        System.out.println("Error: No user logged in.");
                    }
                }

                case 2 -> UserController.updateUserProfile(scanner);
                // case 2 -> updateProfile.updateProfile(scanner);
                case 3 -> WorkoutController.addWorkout(UserController.getLoggedInUser(), scanner);
                case 4 -> WorkoutController.showAndCompleteWorkouts(UserController.getLoggedInUser(), scanner);
                case 5 -> {
                    System.out.println("Logging out...");
                    UserController.setLoggedInUser(null);
                    UserController.loginOrRegister(scanner);
                }
                case 6 -> {
                    GroupName.displayGroupName();
                    scanner.close();
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
