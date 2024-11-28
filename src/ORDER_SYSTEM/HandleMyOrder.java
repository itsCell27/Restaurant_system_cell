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
        System.out.print("Do you want to checkout the order? (yes/no): ");
        String userInput = scanner.nextLine();

        if ("yes".equalsIgnoreCase(userInput)) {
            // Ask for the payment method
            System.out.print("Choose a payment method (Cash/E-money): ");
            String paymentMethod = scanner.nextLine();

            if (!paymentMethod.equalsIgnoreCase("cash") && !paymentMethod.equalsIgnoreCase("e-money")) {
                System.out.println("Invalid payment method. Please choose 'Cash' or 'E-money'.");
                return;  // Exit the method if invalid input
            }

            saveOrderToCSV(paymentMethod);
            clearOrders();  // Clear orders after successful checkout
        } else {
            System.out.println("Order not checked out.");
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

    // Method to save orders to CSV file
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
