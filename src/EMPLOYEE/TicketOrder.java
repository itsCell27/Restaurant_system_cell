package EMPLOYEE;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TicketOrder {

    // Method to fetch the order data from the CSV file
    private static Map<String, List<Order>> fetchOrdersFromCSV(String csvFile) {
        Map<String, List<Order>> ordersMap = new HashMap<>();
        String line;
        String cvsSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip the header line
            br.readLine();

            // Read through the CSV file and populate the orders map
            while ((line = br.readLine()) != null) {
                String[] orderDetails = line.split(cvsSplitBy);
                if (orderDetails.length < 8) continue; // Skip incomplete rows

                String orderNumber = orderDetails[0].trim();
                String itemName = orderDetails[1].trim();
                int quantity = Integer.parseInt(orderDetails[2].trim());
                String totalAmount = orderDetails[3].trim();  // Remove 'PHP' later
                String paymentMethod = orderDetails[4].trim();
                String diningOption = orderDetails[5].trim();
                String status = orderDetails[6].trim();
                String date = orderDetails[7].trim();

                // Only consider pending orders
                if ("pending".equalsIgnoreCase(status)) {
                    Order order = new Order(orderNumber, itemName, quantity, totalAmount, paymentMethod, diningOption, date);
                    ordersMap.putIfAbsent(orderNumber, new ArrayList<>());
                    ordersMap.get(orderNumber).add(order);
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading the CSV file: " + e.getMessage());
        }

        return ordersMap;
    }

    // Method to display the orders and generate the receipt
    public static void handleOrder() {
        System.out.println("Handling ticket order...");
        
        String csvFile = "OrderRecords/order_records.csv";  // Adjust this path if necessary
        Map<String, List<Order>> ordersMap = fetchOrdersFromCSV(csvFile);

        if (ordersMap.isEmpty()) {
            System.out.println("No pending orders found.");
            return;
        }

        // Display the available order numbers
        System.out.println("Order numbers:");
        int index = 1;
        for (String orderNumber : ordersMap.keySet()) {
            System.out.println(index++ + ". Order#" + orderNumber);
        }

        // Get user input to select an order
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select Order number (0 to go back): ");
        int selectedOrderIndex = scanner.nextInt();
        
        if (selectedOrderIndex == 0) {
            System.out.println("Going back...");
            return;
        }

        // Find the selected order number based on index
        String selectedOrderNumber = new ArrayList<>(ordersMap.keySet()).get(selectedOrderIndex - 1);
        List<Order> selectedOrderItems = ordersMap.get(selectedOrderNumber);

        // Display the selected order details
        System.out.println("Order #" + selectedOrderNumber);
        int totalPrice = 0;
        for (Order order : selectedOrderItems) {
            totalPrice += order.getTotalPrice();
            System.out.printf("%d %-15s ..................................%d PHP\n", order.getQuantity(), order.getItemName(), order.getTotalPrice());
        }
        System.out.printf("Total               ..................................%d PHP\n", totalPrice);

        // Ask if the user wants to generate the receipt
        System.out.print("Do you want to generate receipt (1 yes / 0 no)? ");
        int generateReceipt = scanner.nextInt();

        if (generateReceipt == 1) {
            generateReceiptFile(selectedOrderNumber, selectedOrderItems, totalPrice);
            System.out.println("Receipt has been generated and saved to the 'Receipts' folder.");
        } else {
            System.out.println("Receipt generation cancelled.");
        }
    }

    // Method to generate the receipt and save it to a folder
    private static void generateReceiptFile(String orderNumber, List<Order> orderItems, int totalPrice) {
        // Folder path to store receipts
        String folderPath = "Receipts";
        
        // Create the folder if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir(); // Creates the directory
        }

        // Define the receipt file path
        String fileName = folderPath + File.separator + "receipt_" + orderNumber + ".txt";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("====================================\n");
            writer.write("              ORDER #" + orderNumber + "\n");
            writer.write("====================================\n");

            // Write order details
            Order firstOrder = orderItems.get(0);
            writer.write(firstOrder.getDate() + "                              Dining Option: " + firstOrder.getDiningOption() + "\n");
            writer.write("                                                 Payment Method: " + firstOrder.getPaymentMethod() + "\n");

            for (Order order : orderItems) {
                writer.write(String.format("%d %-15s ..................................%d PHP\n", order.getQuantity(), order.getItemName(), order.getTotalPrice()));
            }

            writer.write("\nTotal               .................................."+ totalPrice + " PHP\n");
            writer.write("\n====================================\n");

        } catch (IOException e) {
            System.out.println("Error writing the receipt: " + e.getMessage());
        }
    }

    // Order class to hold order details
    private static class Order {
        private String orderNumber;
        private String itemName;
        private int quantity;
        private String totalAmount;
        private String paymentMethod;
        private String diningOption;
        private String date;

        public Order(String orderNumber, String itemName, int quantity, String totalAmount, String paymentMethod, String diningOption, String date) {
            this.orderNumber = orderNumber;
            this.itemName = itemName;
            this.quantity = quantity;
            this.totalAmount = totalAmount;
            this.paymentMethod = paymentMethod;
            this.diningOption = diningOption;
            this.date = date;
        }

        public int getTotalPrice() {
            // Assuming totalAmount is in format "2225 PHP", so remove the " PHP" part and convert it to an integer
            return Integer.parseInt(totalAmount.replace(" PHP", "").trim());
        }

        public String getItemName() {
            return itemName;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public String getDiningOption() {
            return diningOption;
        }

        public String getDate() {
            return date;
        }
    }

    // Main method to run the system
    public static void main(String[] args) {
        handleOrder();
    }
}
