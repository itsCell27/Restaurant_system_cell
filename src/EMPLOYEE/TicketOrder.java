package EMPLOYEE;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class TicketOrder {

    // WatchService to monitor changes in the CSV file
    private static WatchService watchService;

    // Method to initialize the WatchService for monitoring the CSV file
    private static void startFileWatcher(String csvFilePath) throws IOException {
        Path path = Paths.get(csvFilePath).getParent(); // Get the directory of the CSV file
        watchService = FileSystems.getDefault().newWatchService();
        path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

        // Start a separate thread to monitor the file
        new Thread(() -> {
            while (true) {
                try {
                    WatchKey key = watchService.take(); // Wait for a change
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            Path modifiedFilePath = (Path) event.context();
                            if (modifiedFilePath.toString().equals(new File(csvFilePath).getName())) {
                                // CSV file was modified, reload the orders
                                System.out.println("CSV file changed. Reloading orders...");
                                fetchOrdersFromCSV(csvFilePath); // Reload orders data
                            }
                        }
                    }
                    key.reset();
                } catch (InterruptedException e) {
                    System.out.println("File watcher interrupted: " + e.getMessage());
                    break;
                }
            }
        }).start();
    }

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

                // Only consider served orders now
                if ("served".equalsIgnoreCase(status)) {
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

        String csvFile = "OrderRecords/order_summary.csv"; // Adjust this path if necessary
        try {
            startFileWatcher(csvFile); // Start the file watcher when handling orders
        } catch (IOException e) {
            System.out.println("Error starting file watcher: " + e.getMessage());
            return;
        }

        // Fetch orders from the CSV file
        Map<String, List<Order>> ordersMap = fetchOrdersFromCSV(csvFile);

        if (ordersMap.isEmpty()) {
            System.out.println("No served orders found.");
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
        int selectedOrderIndex = -1;

        while (true) {
            System.out.print("Select Order number (0 to go back): ");
            if (scanner.hasNextInt()) {
                selectedOrderIndex = scanner.nextInt();
                if (selectedOrderIndex == 0) {
                    System.out.println("Going back...");
                    return;
                }
                if (selectedOrderIndex > 0 && selectedOrderIndex <= ordersMap.size()) {
                    break; // Valid input
                }
            }
            System.out.println("Invalid choice. Please select a valid order number.");
            scanner.nextLine(); // Clear invalid input
        }

        // Find the selected order number based on index
        String selectedOrderNumber = new ArrayList<>(ordersMap.keySet()).get(selectedOrderIndex - 1);
        List<Order> selectedOrderItems = ordersMap.get(selectedOrderNumber);

        // Display the selected order details in tabular format
        System.out.println("==================================================================================");
        System.out.printf("|| %-13s || %-10s || %-10s || %-12s || %-15s ||%n",
                "Order No.", "Quantity", "Items", "Price", "Total Price");
        System.out.println("==================================================================================");

        boolean isFirstRow = true;
        int totalPrice = 0;
        for (Order order : selectedOrderItems) {
            int totalItemPrice = order.getTotalPrice();
            totalPrice += totalItemPrice;

            System.out.printf("|| %-13s || %-10d || %-10s || %-12s || %-15s ||%n",
                    isFirstRow ? selectedOrderNumber : "",
                    order.getQuantity(), order.getItemName(),
                    order.getUnitPrice() + " PHP", totalItemPrice + " PHP");
            isFirstRow = false;
        }

        System.out.println("==================================================================================");
        System.out.printf("|| %-57s || %-15s ||%n", "Total", totalPrice + " PHP");
        System.out.println("==================================================================================");

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


    // Receipt space calculator
    public static String padToWidth(String label, String value, int totalWidth) {
        int spacesNeeded = totalWidth - (label.length() + value.length());
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(" ");
        }
        return label + padding + value; // Combine label, spaces, and value
    }

    // for individual price of item
    public static String periodValue(String value, int totalWidth) {
        int spacesNeeded = totalWidth - value.length();
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(".");
        }
        return padding + value; // Combine spaces and value
    }

    // for total price of item
    public static String reverseValue(String value, int totalWidth) {
        int spacesNeeded = totalWidth - value.length();
        spacesNeeded = Math.max(0, spacesNeeded); // Ensure non-negative space count
        StringBuilder padding = new StringBuilder();
        for (int i = 0; i < spacesNeeded; i++) {
            padding.append(" ");
        }
        return value + padding; // Combine spaces and value
    }
    // Receipt space calculator ending


  
    private static void generateReceiptFile(String orderNumber, List<Order> orderItems, int totalPrice) {
        String folderPath = "Receipts";

        // Create the folder if it doesn't exist
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdir();
        }

        String fileName = folderPath + File.separator + "receipt_order_#" + orderNumber + ".txt";

        Scanner scanner = new Scanner(System.in);
        int amountPaid = 0;

        // Prompt user for a valid amount
        while (true) {
            System.out.print("Enter the Amount Paid by the customer: ");
            if (scanner.hasNextInt()) {
                amountPaid = scanner.nextInt();
                if (amountPaid >= totalPrice) {
                    break; // Valid input, exit loop
                }
            } else {
                scanner.nextLine(); // Clear invalid input
            }
            System.out.println("Invalid amount. The amount paid must be greater than or equal to " + totalPrice + " PHP.");
        }

        int amountDue = amountPaid - totalPrice;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("\t==================================================\n");
            writer.write("\t                   ORDER #" + orderNumber + "\n");
            writer.write("\t==================================================\n");

            // Write order details
            Order firstOrder = orderItems.get(0);
            writer.write("\t" + padToWidth("DATE:", firstOrder.getDate(), 50) + "\n");
            writer.write("\t" + padToWidth("DINING OPTION:", firstOrder.getDiningOption(), 50) + "\n");
            writer.write("\t" + padToWidth("PAYMENT METHOD:", firstOrder.getPaymentMethod(), 50) + "\n\n");
            writer.write("\t==================================================\n");

            // Write table headers
            writer.write(String.format("\t%-5s %-16s %-15s %-5s\n", "Qty", "Item", "Unit Price", "Total Price"));
            writer.write("\t==================================================\n");

            // Write individual item details
            for (Order order : orderItems) {
                writer.write(String.format(
                    "\t%-5d %-18s %-17s %-5s\n",
                    order.getQuantity(),
                    order.getItemName(),
                    order.getUnitPrice() + " PHP",
                    order.getTotalPrice() + " PHP"
                ));
            }

            // Write total price
            writer.write("\n\t" + padToWidth("TOTAL:", totalPrice + " PHP", 50) + "\n");

            // Write amount paid and amount due
            writer.write("\n\t" + padToWidth("AMOUNT PAID:", amountPaid + " PHP", 50) + "\n");
            writer.write("\t" + padToWidth("AMOUNT DUE:", amountDue + " PHP", 50) + "\n");

            writer.write("\n\t==================================================\n");
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
        
        public double getUnitPrice() {
            // Assuming totalAmount is in format "2225 PHP", so remove the " PHP" part and divide by quantity
            return Double.parseDouble(totalAmount.replace(" PHP", "").trim()) / quantity;
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
}