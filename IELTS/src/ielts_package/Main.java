package ielts_package;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        // Ask the user to enter their ID
        System.out.print("Enter ID: ");
        String id = input.nextLine();

        // Ask the user to enter their password
        System.out.print("Enter Password: ");
        String password = input.nextLine();

        // Try to log in using the entered ID and password
        User user = User.login(id, password);

        // Check if login failed (user not found or incorrect credentials)
        if (user == null) {
            System.out.println("Login failed.");
            System.exit(0); // End the program
        }

        // If login succeeds, you can continue your program here...
        
        
        switch (user.getRole()) {

        case "student":
           
            break;

        case "institute":   // staff
           
            break;

        case "admission":   // admin
          
            break;

        case "dean":        // dean 
          
            break;

        default:
            System.out.println("Unknown role.");
    }
    }
}
