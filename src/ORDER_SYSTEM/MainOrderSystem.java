package ORDER_SYSTEM;

import ADMIN.AdminSystem;
import ADMIN.AdminAuthForLogout;
import MENU_DATA_HANDLING.MenuData;
import EMPLOYEE.EmployeeMenu;
import java.util.Scanner;

public class MainOrderSystem {
    static Scanner scanner = new Scanner(System.in);
    static Order[] orders = new Order[100];
    static OrderCount orderCount = new OrderCount(0);
    static int dineInOrTakeOut;
    static boolean isUserLoggedIn = false;  // Track user login status

    
    public static void main(String[] args) {
        int mainChoice;
        BreakfastMenu breakfastMenu = new BreakfastMenu(orders, orderCount);
        ChickenAndPlattersMenu chickenAndPlattersMenu = new ChickenAndPlattersMenu(orders, orderCount);
        Burger burger = new Burger(orders, orderCount);
        DrinksAndDessert drinksAndDessert = new DrinksAndDessert(orders, orderCount);
        Coffee coffee = new Coffee(orders, orderCount);
        Fries fries = new Fries(orders, orderCount);
        HandleMyOrder handleOrder = new HandleMyOrder(scanner, orders, orderCount, dineInOrTakeOut);

        displayUserRoleMenu();  // Display the user role selection menu

        if (isUserLoggedIn) {  // Only proceed if the user selects "Customer"
              // Display dine in or take out options

            do {
                System.out.println("\nWelcome to the Restaurant!");
                System.out.println("1. Breakfast Menu");
                System.out.println("2. Chicken And Platters");
                System.out.println("3. Burger Menu");
                System.out.println("4. Drinks & Desserts Menu");
                System.out.println("5. Coffee Menu");
                System.out.println("6. Fries Menu");
                System.out.println("7. My Order");
                System.out.println("0. Go Back");
                System.out.print("Please select an option: ");
                mainChoice = scanner.nextInt();

                switch (mainChoice) {
                    case 1:
                        breakfastMenu.displayBreakfastMenu();
                        break;
                    case 2:
                        chickenAndPlattersMenu.displayChickenAndPlatters();
                        break;
                    case 3:
                        burger.displayBurgerMenu();
                        break;
                    case 4:
                        drinksAndDessert.displayDrinksAndDessertsMenu();
                        break;
                    case 5:
                        coffee.displayCoffeeMenu();
                        break;
                    case 6:
                        fries.displayFriesMenu();
                        break;
                    case 7:
                        handleOrder.handleMyOrder();
                        break;
                    case 0:
                        displayDineInOrTakeOut();
                        break;
                    default:
                        System.out.println("Invalid choice. Please select again.");
                }
            } while (true);
        } else {
            System.out.println("Exiting system.");
        }
    }

    // Display Login and Register menu before Dine In/Take Out
    public static void displayUserRoleMenu() {
        int choice;
        while (true) {
            System.out.println("\nSelect your role:");
            System.out.println("1. Admin");
            System.out.println("2. Employee");
            System.out.println("3. Customer");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                	AdminSystem adminSystem = new AdminSystem(); // Instantiate the AdminSystem class
                    adminSystem.start();
                    break;
                case 2:
                	EmployeeMenu employeeMenu = new EmployeeMenu(); // Instantiate the EmployeeMenu class
                    employeeMenu.start();
                    break;
                case 3:
                    isUserLoggedIn = true;  // Only if Customer is selected, allow access to the system
                    displayDineInOrTakeOut();  // Call this method after login
                    return;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }


    // Display Dine In or Take Out menu
    public static void displayDineInOrTakeOut() {
        int choice;
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Dine In");
            System.out.println("2. Take Out");
            System.out.println("3. Logout");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    dineInOrTakeOut = 1;
                    return;
                case 2:
                    dineInOrTakeOut = 2;
                    return;
                case 3:
                    // Attempt to log out by authenticating
                    AdminAuthForLogout authForLogout = new AdminAuthForLogout();
                    if (authForLogout.authenticateForLogout()) {
                        isUserLoggedIn = false;  // Log the user out if authentication is successful
                        displayUserRoleMenu();
                        return;
                    } else {
                        System.out.println("Logout failed. Returning to menu.");
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }

    public static void displayOrderSummary() {
        System.out.println("\nOrder Summary:");
        int total = 0;
        for (int i = 0; i < orderCount.count; i++) {
            String itemName = "";
            switch (orders[i].getCategory()) {
                case "Breakfast":
                    itemName = MenuData.breakfastItemNames[orders[i].getItemIndex()];
                    break;
                case "ChickenAndPlatters":
                    itemName = MenuData.chickenAndPlattersItemNames[orders[i].getItemIndex()];
                    break;
                case "Burger":
                    itemName = MenuData.burgerItemNames[orders[i].getItemIndex()];
                    break;
                case "DrinksAndDesserts":
                    itemName = MenuData.drinksAndDessertsItemNames[orders[i].getItemIndex()];
                    break;
                case "Coffee":
                    itemName = MenuData.coffeeItemNames[orders[i].getItemIndex()];
                    break;
                case "Fries":
                    itemName = MenuData.friesItemNames[orders[i].getItemIndex()];
                    break;
            }

            System.out.printf("Item: %s\n", itemName);
            System.out.printf("Quantity: %d\n", orders[i].getQuantity());
            System.out.printf("Price: %d PHP\n", orders[i].getPrice());
            total += orders[i].getPrice();
            System.out.println();
        }
        System.out.printf("Total Price: %d PHP\n", total);
    }

    public static void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}

class Order {
    private String category;
    private int itemIndex;
    private int quantity;
    private int price;

    public Order(String category, int itemIndex, int quantity, int price) {
        this.category = category;
        this.itemIndex = itemIndex;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
