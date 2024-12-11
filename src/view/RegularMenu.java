package view;

import model.PremiumUser;
import model.User;
import controller.*;

import java.util.Scanner;

public class RegularMenu {
    private final UserController userController;
    private final Scanner scanner;

    public RegularMenu(UserController userController, Scanner scanner) {
        this.userController = userController;
        this.scanner = scanner;
    }

    public void showMenu() {
        while (true) {
            System.out.println("==========================");
            System.out.println("Main Menu (Regular User)");
            System.out.println("==========================");
            System.out.println("1. Display User Info");
            System.out.println("2. Update User Info");
            System.out.println("3. Become Premium");
            System.out.println("4. Logout");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    User user = userController.getLoggedInUser();
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
                case 2 -> {
                    UpdateProfile updateProfile = new UpdateProfile(userController);
                    updateProfile.updateProfile(scanner);
                }

                case 3 -> {
                    System.out.println("Enter Membership ID to become Premium: ");
                    String membershipId = scanner.next();
                    userController.upgradeUserToPremium(membershipId);

                    if (userController.getLoggedInUser() instanceof PremiumUser) {
                        PremiumMenu premiumMenu = new PremiumMenu(userController, new WorkoutController(), scanner);
                        premiumMenu.showMenu();
                        return;
                    }
                }

                case 4 -> {
                    System.out.println("Logging out...");
                    userController.logout();
                    System.out.println("You have been logged out.");
                    userController.loginOrRegister(scanner);
                    return;
                }
                case 5 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    GroupName.displayGroupName(); 
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
