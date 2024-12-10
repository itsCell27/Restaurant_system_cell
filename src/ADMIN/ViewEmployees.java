package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import ORDER_SYSTEM.MainOrderSystem;

public class ViewEmployees {

    // Method to read and display the CSV file content
    public void viewEmployees() {
    	Scanner scanner = new Scanner(System.in);
        String filePath = "Employeelist/employees.csv"; // Path to the CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Display table header
            MainOrderSystem.clearScreen();
            System.out.println("\t\t\t\t\t\t\tEMPLOYEES");
            System.out.println("\t\t\t===========================================================================");
            System.out.printf("\t\t\t| %-40s %-30s |\n", "Username", "Password");
            System.out.println("\t\t\t===========================================================================");

            // Read each line and display its content
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // Split by comma

                // Display username and password in the table format
                if (columns.length >= 2) {
                    System.out.printf("\t\t\t| %-40s %-30s |\n", columns[0], columns[1]);
                } else {
                    System.out.printf("\t\t\t| %-40s %-30s |\n", "Invalid Data", "Invalid Data");
                }
            }

            // Display table footer
            System.out.println("\t\t\t===========================================================================\n\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
        MainOrderSystem.clearScreenBottom();
        
        System.out.print("\t\t\tPress ENTER to continue...");
        scanner.nextLine();
    }
}
