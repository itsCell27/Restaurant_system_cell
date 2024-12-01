package ORDER_SYSTEM;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class HandleMyOrder {

    private static int orderCount = getLastOrderNumber() + 1;  // Start from the next number after the last order number
    private List<Order> orders = new ArrayList<>();  // Store orders in a list

    // Static variable to store the dining option
    private static String diningOption;

    // Method to show the order summary
    public void showOrderSummary() {
        if (orders.isEmpty()) {
            System.out.println("No orders to display.");
            return;
        }

        double totalAmount = 0;  // Variable to store the total amount of all orders

        // Display the summary for each order and calculate the total amount
        for (Order order : orders) {
            order.displaySummary();  // Display the order summary using the displaySummary method of the Order class
            System.out.println("Dining Option: " + diningOption);  // Display the dining option
            totalAmount += order.getTotalAmount();  // Add the total price of the current order to totalAmount
            System.out.println("--------------------------------");
        }

        // Display the total amount of the orders
        System.out.println("Total Amount: " + String.format("%.2f", totalAmount));

        // Ask user if they want to checkout
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to checkout the order? (1 for Yes / 0 for No): ");
        int userInput = scanner.nextInt();

        if (userInput == 1) {
            // Ask for the payment method
            System.out.print("Choose a payment method (1 for Cash / 2 for E-money / 0 to Go Back): ");
            int paymentMethodInput = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            String paymentMethod = null;

            switch (paymentMethodInput) {
                case 1:
                    paymentMethod = "Cash";
                    break;
                case 2:
                    paymentMethod = "E-money";
                    break;
                case 0:
                    System.out.println("Returning to the previous menu...");
                    return; // Exit the method without proceeding
                default:
                    System.out.println("Invalid choice. Please select 1 for Cash, 2 for E-money, or 0 to go back.");
                    return; // Exit the method for invalid input
            }

            // Save the order to CSV and clear the orders
            saveOrderToCSV(paymentMethod);
            clearOrders();

            // Ask if the user wants to continue or exit
            System.out.print("Do you want to add more orders or exit? (1 for Add more / 0 for Exit): ");
            int continueInput = scanner.nextInt();
            if (continueInput == 1) {
                // Proceed to add more orders
                System.out.println("You can now add more orders.");
            } else if (continueInput == 0) {
                // Exit the system or stop the process
                System.out.println("Exiting the system. Thank you!");
                return;  // Exit the method and the program
            } else {
                System.out.println("Invalid input. Exiting...");
                return;
            }
        } else if (userInput == 0) {
            System.out.println("Order not checked out.");
        } else {
            System.out.println("Invalid input. Please enter 1 for Yes or 0 for No.");
        }
    }

    // Method to add an order to the list
    public void addOrder(Order order) {
        orders.add(order);
    }

    // Method to set the dining option globally
    public static void setDiningOption(String option) {
        if ("Dine In".equalsIgnoreCase(option) || "Take Out".equalsIgnoreCase(option)) {
            diningOption = option;
        } else {
            throw new IllegalArgumentException("Invalid dining option. Choose 'Dine In' or 'Take Out'.");
        }
    }
    
 // Method to generate and save a ticket to a text file
 // Method to generate and save a ticket to a text file with a dynamic name
    public void generateTicketFile(int orderNumber) {
        // Define the directory path
        String directoryPath = "Tickets";
        File directory = new File(directoryPath);

        // Create the "Tickets" directory if it does not exist
        if (!directory.exists()) {
            directory.mkdir();  // Create directory if not exists
        }

        // Define the file name dynamically as ticket_order_#<order_number>.txt
        String fileName = directoryPath + "/ticket_order_#" + orderNumber + ".txt";  // Dynamic filename

        // Try-with-resources to ensure the FileWriter is closed after use
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            // Write the ticket in the desired format
            fileWriter.append("====================================\n");
            fileWriter.append("              ORDER #" + orderNumber + "\n");
            fileWriter.append("====================================\n\n");

            System.out.println("Ticket for ORDER #" + orderNumber + " has been saved to " + fileName);

        } catch (IOException e) {
            System.out.println("An error occurred while saving the ticket: " + e.getMessage());
        }
    }



    // Method to save orders to CSV file
    private void saveOrderToCSV(String paymentMethod) {
        // Define the new file path in the "OrderRecords" folder
        String directoryPath = "OrderRecords";
        File directory = new File(directoryPath);

        // Create the "OrderRecords" directory if it does not exist
        if (!directory.exists()) {
            directory.mkdir();  // Create directory
        }

        String fileName = directoryPath + "/order_summary.csv";  // File path inside OrderRecords folder

        // Try-with-resources to ensure the FileWriter is closed after use
        try (FileWriter fileWriter = new FileWriter(fileName, true)) {
            // Write header if the file is empty
            if (new File(fileName).length() == 0) {
                fileWriter.append("Order Number,Item,Quantity,Total Price,Payment Method,Dining Option,Status,Date,Time\n");
            }

            // Get the current date and time
            String[] currentDateTime = getCurrentDateTime();

            // Write each order to the CSV file
            for (Order order : orders) {
                fileWriter.append(String.valueOf(orderCount)) // Order Number
                          .append(",")
                          .append(order.getItem().getName())  // Item Name
                          .append(",")
                          .append(String.valueOf(order.getQuantity())) // Quantity
                          .append(",")
                          .append(String.valueOf(order.getTotalAmount())) // Total Price
                          .append(",")
                          .append(paymentMethod) // Payment Method
                          .append(",")
                          .append(diningOption)  // Dining Option (Dine In / Take Out)
                          .append(",")
                          .append("pending") // Status (could be "pending" initially)
                          .append(",")
                          .append(currentDateTime[0]) // Date
                          .append(",")
                          .append(currentDateTime[1]) // Time
                          .append("\n");
            }

            System.out.println("Order has been checked out and saved to " + fileName);

            // Increment orderCount after saving the order
            orderCount++;
            generateTicketFile(orderCount - 1);

        } catch (IOException e) {
            System.out.println("An error occurred while saving the order to CSV: " + e.getMessage());
        }
    }

    // Method to clear orders
    private void clearOrders() {
        orders.clear();
        System.out.println("All orders have been cleared.");
    }

    // Method to get the last order number from the CSV file
    private static int getLastOrderNumber() {
        int lastOrderNumber = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("OrderRecords/order_summary.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length > 0) {
                    try {
                        lastOrderNumber = Integer.parseInt(data[0]);
                    } catch (NumberFormatException e) {
                        // Ignore invalid number formats
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }
        return lastOrderNumber;
    }

    // Helper method to get the current date and time
    private String[] getCurrentDateTime() {
        String[] dateTime = new String[2];

        // Get the current date and time in the Asia/Manila timezone
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Manila"));
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");  // 12-hour format with AM/PM
        
        dateTime[0] = now.format(dateFormatter);  // Get the current date
        dateTime[1] = now.format(timeFormatter);  // Get the current time in 12-hour format with AM/PM

        return dateTime;  // Return both as an array
    }
}
