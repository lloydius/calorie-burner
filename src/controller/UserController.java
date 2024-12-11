package controller;

import model.User;
import model.RegularUser;
import model.PremiumUser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserController {
    private final List<User> users = new ArrayList<>();
    private final WorkoutController workoutController = new WorkoutController();
    private User loggedInUser = null;

    public void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/data/users.txt"))) {
            String line;
            boolean isFileEmpty = true;

            while ((line = reader.readLine()) != null) {
                isFileEmpty = false;
                String[] userDetails = line.split(",");
                String name = userDetails[0];
                String password = userDetails[1];
                int age = Integer.parseInt(userDetails[2]);
                double weight = Double.parseDouble(userDetails[3]);
                double height = Double.parseDouble(userDetails[4]);
                String userType = userDetails[5];

                User user;
                if (userType.equals("Premium")) {
                    String membershipId = userDetails[6];
                    user = new PremiumUser(name, password, age, weight, height, membershipId);
                } else {
                    user = new RegularUser(name, password, age, weight, height);
                }

                users.add(user);
            }

            if (isFileEmpty) {
                System.out.println("The file is empty. No users to load.");
            }
        } catch (IOException e) {
            System.out.println("Error reading users from file: " + e.getMessage());
        }
    }

    public void saveUserToFile(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/data/users.txt", true))) {
            String userData = user.getName() + ","
                    + user.getPassword() + ","
                    + user.getAge() + ","
                    + user.getWeight() + ","
                    + user.getHeight() + ","
                    + (user instanceof PremiumUser ? "Premium," + ((PremiumUser) user).getMembershipId() : "Regular");
            writer.write(userData);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving user to file: " + e.getMessage());
        }
    }

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

    private void register(Scanner scanner) {
        System.out.println("=== Register ===");
        System.out.print("Enter Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

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

        System.out.println("Select User Type:");
        System.out.println("1. Regular User");
        System.out.println("2. Premium User");
        System.out.print("Choose an option: ");
        int userType = scanner.nextInt();

        User newUser;
        if (userType == 2) {
            System.out.print("Enter Membership ID: ");
            String membershipId = scanner.next();
            newUser = new PremiumUser(name, password, age, weight, height, membershipId);
        } else {
            newUser = new RegularUser(name, password, age, weight, height);
        }

        users.add(newUser);
        loggedInUser = newUser;
        System.out.println("Account created successfully! You are now logged in as " + name + ".");

        saveUserToFile(newUser);
    }

    private void login(Scanner scanner) {
        System.out.println("=== Login ===");
        System.out.print("Enter Name: ");
        scanner.nextLine();
        String name = scanner.nextLine();

        for (User user : users) {
            if (user.getName().equals(name)) {
                int attempts = 3;
                while (attempts > 0) {
                    System.out.print("Enter Password: ");
                    String password = scanner.next();

                    if (user.validatePassword(password)) {
                        loggedInUser = user;
                        System.out.println("Login successful! Welcome, " + name + "!");
                        workoutController.loadWorkoutsFromFile(loggedInUser);
                        return;
                    } else {
                        attempts--;
                        System.out.println("Incorrect password. Attempts remaining: " + attempts);
                    }
                }
                System.out.println("Access denied. Too many incorrect attempts.");
                return;
            }
        }

        System.out.println("User not found. Please register first.");
    }

    public void logout() {
        if (loggedInUser != null) {
            System.out.println("Logging out " + loggedInUser.getName() + "...");
            loggedInUser = null;
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public void overwriteUserInFile(User updatedUser) {
        List<User> allUsers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                String password = data[1];
                int age = Integer.parseInt(data[2]);
                double weight = Double.parseDouble(data[3]);
                double height = Double.parseDouble(data[4]);
                String userType = data[5];

                User user;
                if (userType.equals("Premium")) {
                    String membershipId = data[6];
                    user = new PremiumUser(name, password, age, weight, height, membershipId);
                } else {
                    user = new RegularUser(name, password, age, weight, height);
                }
                allUsers.add(user);
            }
        } catch (IOException e) {
            System.out.println("Error reading users from file: " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("users.txt"))) {
            for (User user : allUsers) {
                if (user.getName().equals(updatedUser.getName())) {
                    String userData = updatedUser.getName() + ","
                            + updatedUser.getPassword() + ","
                            + updatedUser.getAge() + ","
                            + updatedUser.getWeight() + ","
                            + updatedUser.getHeight() + ","
                            + (updatedUser instanceof PremiumUser
                                    ? "Premium," + ((PremiumUser) updatedUser).getMembershipId()
                                    : "Regular");
                    writer.write(userData);
                    writer.newLine();
                } else {
                    String userData = user.getName() + ","
                            + user.getPassword() + ","
                            + user.getAge() + ","
                            + user.getWeight() + ","
                            + user.getHeight() + ","
                            + (user instanceof PremiumUser ? "Premium," + ((PremiumUser) user).getMembershipId()
                                    : "Regular");
                    writer.write(userData);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public void upgradeUserToPremium(String membershipId) {
        if (loggedInUser instanceof RegularUser) {
            RegularUser regularUser = (RegularUser) loggedInUser;

            PremiumUser premiumUser = regularUser.upgradeToPremium(membershipId);
            loggedInUser = premiumUser;
            System.out.println("User has been upgraded to Premium!");

            overwriteUserInFile(premiumUser);
        } else {
            System.out.println("Error: User is already a Premium member.");
        }
    }

    /**
     * @deprecated This method is outdated. Use the new `updateProfile` method on
     *             UpdateProfile controller
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
                overwriteUserInFile(loggedInUser);
            }
            case 2 -> {
                System.out.print("Enter New Weight (kg): ");
                loggedInUser.setWeight(scanner.nextDouble());
                System.out.println("Weight updated successfully!");
                overwriteUserInFile(loggedInUser);
            }
            case 3 -> {
                System.out.print("Enter New Height (cm): ");
                loggedInUser.setHeight(scanner.nextDouble());
                System.out.println("Height updated successfully!");
                overwriteUserInFile(loggedInUser);
            }
            case 4 -> {
                System.out.print("Enter New Password: ");
                loggedInUser.setPassword(scanner.next());
                System.out.println("Password updated successfully!");
                overwriteUserInFile(loggedInUser);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
