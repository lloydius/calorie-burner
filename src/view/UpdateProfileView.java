package view;

import java.util.Scanner;

public class UpdateProfileView {

    public static int displayUpdateMenu(Scanner scanner) {
        System.out.println("=== Update User Profile ===");
        System.out.println("1. Update Age");
        System.out.println("2. Update Weight");
        System.out.println("3. Update Height");
        System.out.println("4. Change Password");
        System.out.print("Choose an option: ");
        return scanner.nextInt();
    }
}
