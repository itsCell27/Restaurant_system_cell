package ORDER_SYSTEM;

import MENU_DATA_HANDLING.MenuData;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class HandleMyOrder {
    private Scanner scanner;
    private Order[] orders;
    private OrderCount orderCount;
    private int dineInOrTakeOut;
    private static int orderNumber = 1;  // Starting order number

    public HandleMyOrder(Scanner scanner, Order[] orders, OrderCount orderCount, int dineInOrTakeOut) {
        this.scanner = scanner;
        this.orders = orders;
        this.orderCount = orderCount;
        this.dineInOrTakeOut = dineInOrTakeOut;
    }

    public void handleMyOrder() {
        if (orderCount.count == 0) {
            System.out.println("\nNo orders to display.");
            waitForEnter();
            return;
        }
        System.out.println("\nYour Order Summary:");
        int total = 0;
        for (int i = 0; i < orderCount.count; i++) {
            if (orders[i] == null) continue;

            String itemName = getItemName(orders[i]);
            System.out.printf("Item: %s\n", itemName);
            System.out.printf("Quantity: %d\n", orders[i].getQuantity());
            System.out.printf("Price: %d PHP\n", orders[i].getPrice());
            total += orders[i].getPrice();
            System.out.println();
        }
        System.out.printf("Total Price: %d PHP\n", total);
        System.out.println("Dining Option: " + (dineInOrTakeOut == 1 ? "Dine In" : "Take Out"));

        System.out.print("Input 1 to check out, 2 to cancel, or 0 to go back: ");
        int action = scanner.nextInt();

        if (action == 0) return;
        if (action == 1) handleCheckout(total);
        else if (action == 2) {
            System.out.println("Cancelling all orders...");
            resetOrders();
        } else {
            System.out.println("Invalid option selected.");
        }
    }

    private void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }

    private String getItemName(Order order) {
        switch (order.getCategory()) {
            case "Breakfast":
                return MenuData.breakfastItemNames[order.getItemIndex()];
            case "ChickenAndPlatters":
                return MenuData.chickenAndPlattersItemNames[order.getItemIndex()];
            case "Burger":
                return MenuData.burgerItemNames[order.getItemIndex()];
            case "DrinksAndDesserts":
                return MenuData.drinksAndDessertsItemNames[order.getItemIndex()];
            case "Coffee":
                return MenuData.coffeeItemNames[order.getItemIndex()];
            case "Fries":
                return MenuData.friesItemNames[order.getItemIndex()];
            default:
                return "Unknown Item";
        }
    }

    private void handleCheckout(int total) {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. E-money");
        System.out.print("Select payment method: ");
        int paymentMethod = scanner.nextInt();

        if (paymentMethod != 1 && paymentMethod != 2) {
            System.out.println("Invalid payment method selected.");
            return;
        }

        File directory = new File("OrderRecords");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Retrieve the last order number from the CSV file
        int lastOrderNumber = getLastOrderNumber();

        // Increment the order number
        orderNumber = lastOrderNumber + 1;

        try {
            File file = new File(directory, "order_records.csv");
            FileWriter writer = new FileWriter(file, true);

            // If the file is empty, write the header row
            if (file.length() == 0) {
                writer.append("Order Number,Item,Quantity,Total Amount,Payment Method,Dining Option,Status,Date\n");
            }

            String currentDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
            String paymentMethodStr = (paymentMethod == 1) ? "Cash" : "E-money";
            String diningOptionStr = (dineInOrTakeOut == 1) ? "Dine in" : "Take out";
            String status = "pending";
            String orderNumberStr = String.valueOf(orderNumber);

            // Loop through each order and write the details to the CSV file
            for (int i = 0; i < orderCount.count; i++) {
                if (orders[i] == null) continue;

                String itemName = getItemName(orders[i]);
                int itemTotal = orders[i].getPrice() * orders[i].getQuantity();  // Calculate the total price for this item

                // Format and write the order details to the CSV file
                writer.append(String.format("%s,%s,%d,%d PHP,%s,%s,%s,%s\n",
                        orderNumberStr, itemName, orders[i].getQuantity(), itemTotal, paymentMethodStr, diningOptionStr, status, currentDate));
            }

            writer.close();
            System.out.println("Order record saved successfully.");
            generateReceipt(orderNumberStr);
            resetOrders();
        } catch (IOException e) {
            System.out.println("Error saving the order record: " + e.getMessage());
        }
    }

    private int getLastOrderNumber() {
        File file = new File("OrderRecords", "order_records.csv");

        if (!file.exists() || file.length() == 0) {
            return 0; // No orders, so the next order number will be 1
        }

        try (Scanner fileScanner = new Scanner(file)) {
            String lastLine = "";
            while (fileScanner.hasNextLine()) {
                lastLine = fileScanner.nextLine();
            }

            // The first column is the order number, so we split the line and return the last order number
            String[] columns = lastLine.split(",");
            return Integer.parseInt(columns[0]);  // Extract the order number from the last row
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading the last order number: " + e.getMessage());
            return 0; // Return 0 in case of an error, meaning we will start with order number 1
        }
    }


    private void generateReceipt(String orderNumberStr) throws IOException {
        // Create the directory to store receipts if it doesn't exist
        File receiptDirectory = new File("Orders");
        if (!receiptDirectory.exists()) {
            receiptDirectory.mkdirs();
        }

        // Create a new file for each order (order#1.txt, order#2.txt, ...)
        File receiptFile = new File(receiptDirectory, "order#" + orderNumberStr + ".txt");
        FileWriter receiptWriter = new FileWriter(receiptFile, false);  // false to overwrite if file exists

        // Write the order header
        receiptWriter.write("====================================\n");
        receiptWriter.write("              ORDER #" + orderNumberStr + "\n");
        receiptWriter.write("====================================\n");

        // Close the writer
        receiptWriter.close();

        // Inform the user
        System.out.println("Receipt for order #" + orderNumberStr + " saved successfully to " + receiptFile.getPath());
    }

    private void resetOrders() {
        for (int i = 0; i < orders.length; i++) {
            orders[i] = null;
        }
        orderCount.count = 0;
    }
}
