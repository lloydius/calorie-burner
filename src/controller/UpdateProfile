package controller;

import model.User;
import view.UpdateProfileView;
import java.util.Scanner;

public class UpdateProfile {

    private final UserService userService;

    public UpdateProfile(UserService userService) {
        this.userService = userService; 
    }

    public void updateProfile(Scanner scanner) {
        User loggedInUser = userService.getLoggedInUser(); 

        if (loggedInUser == null) {
            System.out.println("You must be logged in to update your profile.");
            return;
        }

        int choice = UpdateProfileView.displayUpdateMenu(scanner);
        handleUpdateChoice(choice, scanner, loggedInUser);
    }

    private void handleUpdateChoice(int choice, Scanner scanner, User loggedInUser) {
        switch (choice) {
            case 1 -> updateAge(scanner, loggedInUser);
            case 2 -> updateWeight(scanner, loggedInUser);
            case 3 -> updateHeight(scanner, loggedInUser);
            case 4 -> updatePassword(scanner, loggedInUser);
            default -> System.out.println("Invalid choice.");
        }
    }

    private void updateAge(Scanner scanner, User loggedInUser) {
        System.out.print("Enter New Age: ");
        loggedInUser.setAge(scanner.nextInt());
        System.out.println("Age updated successfully!");
    }

    private void updateWeight(Scanner scanner, User loggedInUser) {
        System.out.print("Enter New Weight (kg): ");
        loggedInUser.setWeight(scanner.nextDouble());
        System.out.println("Weight updated successfully!");
    }

    private void updateHeight(Scanner scanner, User loggedInUser) {
        System.out.print("Enter New Height (cm): ");
        loggedInUser.setHeight(scanner.nextDouble());
        System.out.println("Height updated successfully!");
    }

    private void updatePassword(Scanner scanner, User loggedInUser) {
        System.out.print("Enter New Password: ");
        loggedInUser.setPassword(scanner.next());
        System.out.println("Password updated successfully!");
    }
}
