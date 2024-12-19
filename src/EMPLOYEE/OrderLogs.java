package EMPLOYEE;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class OrderLogs {

    private static Scanner scanner = new Scanner(System.in);

    // Method to display order logs from the order_summary.csv file with precise tabular formatting
    public static void displayOrderLogs() {
        String filePath = "OrderRecords/order_summary.csv";  // Path to your CSV file
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line;

            // Skip the header line
            reader.readLine();

            // Print the table header with improved spacing
            System.out.println("\n\t\t\t==========================================================================================================================================");
            System.out.printf("\t\t\t| %-14s %-15s %-9s %-13s %-17s %-18s %-10s %-12s %-8s %-9s |\n", 
                              "Order Number", "Item", "Quantity", "Total Price", "Payment Method", "Dining Option", "Status", "Date", "Time", "Discount");
            System.out.println("\t\t\t==========================================================================================================================================");

            // Read each order line from the CSV file and display the data
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                
                // Extract the required fields from CSV
                String orderNumber = fields[0];
                String item = fields[1];
                String quantity = fields[2];
                String totalPrice = fields[3];
                String paymentMethod = fields[4];
                String diningOption = fields[5];
                String status = fields[6];
                String date = fields[7];
                String time = fields[8];
                String discount = fields[9];

                // Print each order line in the specified format
                System.out.printf("\t\t\t| %-14s %-15s %-9s %-13s %-17s %-18s %-10s %-12s %-8s %-9s |\n", 
                                  orderNumber, item, quantity, totalPrice, paymentMethod, diningOption, status, date, time, discount);
            }

            // End the table with a border
            System.out.println("\t\t\t==========================================================================================================================================");
            
            // Prompt user to press ENTER to continue
            System.out.print("\n\t\t\tPress ENTER to continue...");
            scanner.nextLine(); // Wait for user input

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the order log file. Please ensure the file exists.");
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.out.println("\t\t\tError closing the file.");
            }
        }
    }
}
