package view;

import model.User;
import controller.*;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserController userController = new UserController();
    private static final WorkoutController workoutController = new WorkoutController();

    public static void main(String[] args) {
        System.out.println("==========================");
        System.out.println("Welcome to CalorieBurner!");
        System.out.println("==========================");

        userController.loadUsersFromFile();

        while (true) {
            loginOrRegisterFlow();
        }
    }

    private static void loginOrRegisterFlow() {
        userController.loginOrRegister(scanner);

        User loggedInUser = userController.getLoggedInUser();

        if (loggedInUser != null) {

            String membershipInfo = loggedInUser.getMembershipInfo();
            if (membershipInfo.contains("Premium")) {
                PremiumMenu premiumMenu = new PremiumMenu(userController, workoutController, scanner);
                premiumMenu.showMenu();
            } else {
                RegularMenu regularMenu = new RegularMenu(userController, scanner);
                regularMenu.showMenu();
            }
        } else {
            System.out.println("Error: No user logged in.");
        }
    }
}
