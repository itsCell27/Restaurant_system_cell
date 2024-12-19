package EMPLOYEE;

import ORDER_SYSTEM.MainOrderSystem;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class TicketOrder {

    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {  // Print 50 newlines
            System.out.println();
        }   
    }

    public static void clearScreenBottom() {
        for (int i = 0; i < 30; i++) {  // Print 30 newlines
            System.out.println();
        }   
    }

    public static void clearScreenSmall() {
        for (int i = 0; i < 20; i++) {  // Print 20 newlines
            System.out.println();
        }   
    }

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
                double totalAmount = Double.parseDouble(orderDetails[3].trim().replace(" PHP", "").trim()); // Now parsing as double
                String paymentMethod = orderDetails[4].trim();
                String diningOption = orderDetails[5].trim();
                String status = orderDetails[6].trim();
                String date = orderDetails[7].trim();

                // Removed the status check, so we include all orders regardless of status
                Order order = new Order(orderNumber, itemName, quantity, totalAmount, paymentMethod, diningOption, date);
                ordersMap.putIfAbsent(orderNumber, new ArrayList<>());
                ordersMap.get(orderNumber).add(order);
            }

        } catch (IOException e) {
            System.out.println("\t\t\tError reading the CSV file: " + e.getMessage());
        }

        return ordersMap;
    }

    // Method to display the orders and generate the receipt
    public static void handleOrder() {

        String csvFile = "OrderRecords/order_summary.csv"; // Adjust this path if necessary
        try {
            startFileWatcher(csvFile); // Start the file watcher when handling orders
        } catch (IOException e) {
            System.out.println("\t\t\tError starting file watcher: " + e.getMessage());
            return;
        }

        // Fetch orders from the CSV file
        Map<String, List<Order>> ordersMap = fetchOrdersFromCSV(csvFile);

        if (ordersMap.isEmpty()) {
            System.out.println("\t\t\tNo served orders found.");
            return;
        }

        MainOrderSystem.clearScreen();
        System.out.println("\t\t\tOrder numbers:\n\n");
        int index = 1;
        for (String orderNumber : ordersMap.keySet()) {
            System.out.println("\t\t\t[" + index++ + "] Order#" + orderNumber);
        }

        // Get user input to select an order
        Scanner scanner = new Scanner(System.in);
        int selectedOrderIndex = -1;
        while (true) {
            try {
                System.out.print("\t\t\tSelect Order number (0 to go back): ");
                if (scanner.hasNextInt()) {
                    selectedOrderIndex = scanner.nextInt();
                    if (selectedOrderIndex == 0) {
                        System.out.println("\t\t\tGoing back...");
                        return;
                    }
                    if (selectedOrderIndex > 0 && selectedOrderIndex <= ordersMap.size()) {
                        break; // Valid input
                    }
                } else {
                    System.out.println("\n\n\n\t\t\tInvalid choice. Please enter a number.");
                    scanner.next(); // Clear invalid input
                }
            } catch (InputMismatchException e) {
                System.out.println("\n\n\n\t\t\tInvalid input. Please enter a valid integer.\n\n\n");
                scanner.next(); // Clear invalid input
            }
        }

        // Find the selected order number based on index
        String selectedOrderNumber = new ArrayList<>(ordersMap.keySet()).get(selectedOrderIndex - 1);
        List<Order> selectedOrderItems = ordersMap.get(selectedOrderNumber);

        // Display the selected order details in tabular format
        System.out.println("\t\t\t========================================================================");
        System.out.printf("\t\t\t| %-13s  %-10s  %-10s  %-12s  %-15s |%n", "Order No.", "Quantity", "Items", "Price", "Total Price");
        System.out.println("\t\t\t========================================================================");

        boolean isFirstRow = true;
        double totalPrice = 0;
        for (Order order : selectedOrderItems) {
            double totalItemPrice = order.getTotalPrice();
            totalPrice += totalItemPrice;

            System.out.printf("\t\t\t| %-13s  %-10d  %-10s  %-12s  %-15s |%n",
                    isFirstRow ? selectedOrderNumber : "",
                    order.getQuantity(), order.getItemName(),
                    String.format("%.2f PHP", order.getUnitPrice()), String.format("%.2f PHP", totalItemPrice));
            isFirstRow = false;
        }

        System.out.println("\t\t\t========================================================================");
        System.out.printf("\t\t\t| %-51s  %-15s |%n", "Total", String.format("%.2f PHP", totalPrice));
        System.out.println("\t\t\t========================================================================");
        clearScreenSmall();

        // Ask if the user wants to generate the receipt
        int actionChoice = -1;
        while (actionChoice != 0) {
            System.out.print("\n\t\t\tDo you want to update or cancel the order? (1 - Update / 2 - Cancel / 0 - Go back): ");
            if (scanner.hasNextInt()) {
                actionChoice = scanner.nextInt();

                switch (actionChoice) {
                    case 1:
                        // Use the new OrderUpdateManager class for updating
                        OrderUpdate.updateOrder(selectedOrderNumber, selectedOrderItems);
                        break;
                    case 2:
                        // Cancel functionality (yet to be implemented)
                        System.out.println("\t\t\tYou chose to cancel the order. (Functionality not implemented yet)");
                        break;
                    case 0:
                        System.out.println("\t\t\tGoing back to the order menu.");
                        break;
                    default:
                        System.out.println("\t\t\tInvalid option. Please select 1, 2, or 0.");
                }
            } else {
                System.out.println("\t\t\tInvalid input. Please enter a number (1, 2, or 0).");
                scanner.next(); // Clear invalid input
            }
        }
    }
}
