package ORDER_SYSTEM;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

//Enhanced BreakfastMenu class with error handling
public class BreakfastMenu {

 private static final String FILE_PATH = "MenuItems/menu.csv"; // Hardcoded file path

 public List<MenuItem> readMenuItems() {
     List<MenuItem> menuItems = new ArrayList<>();
     File file = new File(FILE_PATH);

     if (!file.exists()) {
         System.out.println("                                                                                         Error: File not found at " + FILE_PATH);
         return menuItems;
     }

     try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
         String line;
         br.readLine(); // Skip header

         while ((line = br.readLine()) != null) {
             String[] data = line.split(",");
             if (data.length == 3) {
                 try {
                     String name = data[0].trim();
                     double price = Double.parseDouble(data[1].trim());
                     String category = data[2].trim();
                     menuItems.add(new MenuItem(name, price, category));
                 } catch (NumberFormatException e) {
                     System.out.println("                                                                                         Warning: Invalid price format in line: " + line);
                 }
             } else {
                 System.out.println("                                                                                         Warning: Invalid data format in line: " + line);
             }
         }
     } catch (IOException e) {
         System.out.println("                                                                                         Error reading file: " + e.getMessage());
     }

     return menuItems;
 }

 public void displayMenu(Scanner scanner, HandleMyOrder handleOrder) {
     List<MenuItem> menuItems = readMenuItems();
     List<MenuItem> breakfastMenu = menuItems.stream()
             .filter(item -> item.getCategory().equalsIgnoreCase("Breakfast"))
             .collect(Collectors.toList());

     if (breakfastMenu.isEmpty()) {
         System.out.println("                                                                                         No items found in the Breakfast menu.");
         return;
     }

     System.out.println("                                                                                                                   BREAKFAST MENU\n");
     System.out.println("                                                                                         ==================================================================");
     System.out.printf("                                                                                         | %-5s | %-30s | %-10s |\n", "No.", "Item", "Price");
     System.out.println("                                                                                         ==================================================================");
     for (int i = 0; i < breakfastMenu.size(); i++) {
         MenuItem item = breakfastMenu.get(i);
         System.out.printf("                                                                                         | %-5d | %-30s | %-10.2f |\n",
                 i + 1, item.getName(), item.getPrice());
     }
     System.out.println("                                                                                         ==================================================================");

     int itemChoice = -1;
     while (itemChoice < 0 || itemChoice > breakfastMenu.size()) {
         System.out.print("                                                                                         Please select an option (1-" + breakfastMenu.size() + "), input 0 to go back: ");
         try {
             itemChoice = scanner.nextInt();
             if (itemChoice == 0) {
                 return; // Go back to the main menu
             } else if (itemChoice < 1 || itemChoice > breakfastMenu.size()) {
                 System.out.println("                                                                                         Invalid choice. Please try again.");
             }
         } catch (InputMismatchException e) {
             System.out.println("                                                                                         Invalid input. Please enter a number.");
             scanner.next(); // Clear invalid input
         }
     }

     processOrder(breakfastMenu.get(itemChoice - 1), scanner, handleOrder);
 }

 private void processOrder(MenuItem item, Scanner scanner, HandleMyOrder handleOrder) {
     int quantity = -1;
     while (quantity < 1) {
         System.out.print("                                                                                         Enter the quantity for this item: ");
         try {
             quantity = scanner.nextInt();
             if (quantity < 1) {
                 System.out.println("                                                                                         Quantity must be at least 1. Please try again.");
             }
         } catch (InputMismatchException e) {
             System.out.println("                                                                                         Invalid input. Please enter a valid number.");
             scanner.next(); // Clear invalid input
         }
     }

     double totalPrice = item.getPrice() * quantity;

     int saveOrder = -1;
     while (saveOrder != 0 && saveOrder != 1) {
         System.out.print("                                                                                         Do you want to save this order? (1 for Yes, 0 for No): ");
         try {
             saveOrder = scanner.nextInt();
             if (saveOrder != 0 && saveOrder != 1) {
                 System.out.println("                                                                                         Invalid choice. Please enter 1 or 0.");
             }
         } catch (InputMismatchException e) {
             System.out.println("                                                                                         Invalid input. Please enter 1 or 0.");
             scanner.next(); // Clear invalid input
         }
     }

     if (saveOrder == 1) {
         Order order = new Order(item, quantity);
         handleOrder.addOrder(order);

         System.out.println("                                                                                         \nOrder Summary:");
         System.out.println("                                                                                         Item: " + item.getName());
         System.out.println("                                                                                         Quantity: " + quantity);
         System.out.println("                                                                                         Price: " + String.format("%.2f", totalPrice) + " PHP");
         System.out.println("                                                                                         \nTotal Price: " + String.format("%.2f", totalPrice) + " PHP");
         System.out.println("                                                                                         Press Enter to continue...");
         scanner.nextLine(); // Consume newline
         scanner.nextLine(); // Wait for Enter
     } else {
         System.out.println("                                                                                         Order not saved. Returning to menu.");
     }
 }
}

