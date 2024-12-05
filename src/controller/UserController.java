package controller;

import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final List<User> users = new ArrayList<>();
    private User loggedInUser = null;

    public void loginOrRegister(Scanner scanner) {
        while (loggedInUser == null) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1 -> login(scanner);
                case 2 -> register(scanner);
                case 3 -> {
                    System.out.println("Exiting the application...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void login(Scanner scanner) {
        System.out.println("=== Login ===");
        System.out.print("Enter Name: ");
        String name = scanner.next();

        for (User user : users) {
            if (user.getName().equals(name)) {
                int attempts = 5;
                while (attempts > 0) {
                    System.out.print("Enter Password: ");
                    String password = scanner.next();

                    if (user.validatePassword(password)) {
                        loggedInUser = user;
                        System.out.println("Login successful! Welcome, " + name + "!");
                        return;
                    } else {
                        attempts--;
                        if (attempts > 0) {
                            System.out.println("Incorrect password. Try again (" + attempts + " attempts left).");
                        } else {
                            System.out.println("Incorrect password. Access denied.");
                        }
                    }
                }
                return;
            }
        }

        System.out.println("User not found. Please register first.");
    }

    private void register(Scanner scanner) {
        System.out.println("=== Register ===");
        System.out.print("Enter Name: ");
        String name = scanner.next();

        for (User user : users) {
            if (user.getName().equals(name)) {
                System.out.println("User already exists. Please log in instead.");
                return;
            }
        }

        System.out.print("Enter Password: ");
        String password = scanner.next();
        System.out.print("Enter Age: ");
        int age = scanner.nextInt();
        System.out.print("Enter Weight (kg): ");
        double weight = scanner.nextDouble();
        System.out.print("Enter Height (cm): ");
        double height = scanner.nextDouble();

        User newUser = new User(name, password, age, weight, height);
        users.add(newUser);
        loggedInUser = newUser;
        System.out.println("Account created and logged in successfully!");
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    /**
     * @deprecated This method is outdated. Use the new `updateProfile` method on UpdateProfile controller
     *             instead.
     */
    @Deprecated
    public void updateUserProfile(Scanner scanner) {
        if (loggedInUser == null) {
            System.out.println("You must be logged in to update your profile.");
            return;
        }

        System.out.println("=== Update User Profile ===");
        System.out.println("1. Update Age");
        System.out.println("2. Update Weight");
        System.out.println("3. Update Height");
        System.out.println("4. Change Password");
        System.out.print("Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1 -> {
                System.out.print("Enter New Age: ");
                loggedInUser.setAge(scanner.nextInt());
                System.out.println("Age updated successfully!");
            }
            case 2 -> {
                System.out.print("Enter New Weight (kg): ");
                loggedInUser.setWeight(scanner.nextDouble());
                System.out.println("Weight updated successfully!");
            }
            case 3 -> {
                System.out.print("Enter New Height (cm): ");
                loggedInUser.setHeight(scanner.nextDouble());
                System.out.println("Height updated successfully!");
            }
            case 4 -> {
                System.out.print("Enter New Password: ");
                loggedInUser.setPassword(scanner.next());
                System.out.println("Password updated successfully!");
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
