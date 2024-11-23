package MENU_DATA_HANDLING;

import java.util.ArrayList;

public class MenuData {
    public static ArrayList<String> chickenAndPlattersItemNames = new ArrayList<>();
    public static ArrayList<Integer> chickenAndPlattersItemPrices = new ArrayList<>();

    public static ArrayList<String> breakfastItemNames = new ArrayList<>();
    public static ArrayList<Integer> breakfastItemPrices = new ArrayList<>();

    public static ArrayList<String> burgerItemNames = new ArrayList<>();
    public static ArrayList<Integer> burgerItemPrices = new ArrayList<>();

    public static ArrayList<String> drinksAndDessertsItemNames = new ArrayList<>();
    public static ArrayList<Integer> drinksAndDessertsItemPrices = new ArrayList<>();

    public static ArrayList<String> coffeeItemNames = new ArrayList<>();
    public static ArrayList<Integer> coffeeItemPrices = new ArrayList<>();

    public static ArrayList<String> friesItemNames = new ArrayList<>();
    public static ArrayList<Integer> friesItemPrices = new ArrayList<>();

    //Initialize menu data
    static {
        // Initialize Chicken and Platters items
        chickenAndPlattersItemNames.add("Regular Chicken Meal");
        chickenAndPlattersItemPrices.add(89);

        chickenAndPlattersItemNames.add("Regular Spicy Chicken Meal");
        chickenAndPlattersItemPrices.add(99);

        chickenAndPlattersItemNames.add("2pc Chicken Meal");
        chickenAndPlattersItemPrices.add(159);

        chickenAndPlattersItemNames.add("2pc Spicy Chicken Meal");
        chickenAndPlattersItemPrices.add(179);

        chickenAndPlattersItemNames.add("Chicken Bucket (6pc regular chicken)");
        chickenAndPlattersItemPrices.add(369);

        chickenAndPlattersItemNames.add("Chicken Bucket (8pc regular chicken)");
        chickenAndPlattersItemPrices.add(459);

        chickenAndPlattersItemNames.add("Spicy Chicken Bucket (6pc spicy chicken)");
        chickenAndPlattersItemPrices.add(429);

        chickenAndPlattersItemNames.add("Spicy Chicken Bucket (8pc spicy chicken)");
        chickenAndPlattersItemPrices.add(579);

        // Initialize Breakfast items
        breakfastItemNames.add("Combo Breakfast 1");
        breakfastItemPrices.add(69);

        breakfastItemNames.add("Combo Breakfast 2");
        breakfastItemPrices.add(69);

        breakfastItemNames.add("Combo Breakfast 3");
        breakfastItemPrices.add(69);

        breakfastItemNames.add("Combo Breakfast 4");
        breakfastItemPrices.add(69);

        breakfastItemNames.add("Pancake");
        breakfastItemPrices.add(39);

        breakfastItemNames.add("Regular Peach Mango Pie");
        breakfastItemPrices.add(48);

        breakfastItemNames.add("Large Peach Mango Pie");
        breakfastItemPrices.add(69);

        breakfastItemNames.add("Regular Brewed Coffee");
        breakfastItemPrices.add(49);

        breakfastItemNames.add("Iced Coffee");
        breakfastItemPrices.add(49);

        // Initialize Burger items
        burgerItemNames.add("Regular Burger");
        burgerItemPrices.add(59);

        burgerItemNames.add("Cheeseburger");
        burgerItemPrices.add(79);

        burgerItemNames.add("Bacon Cheeseburger");
        burgerItemPrices.add(99);

        burgerItemNames.add("Jumbo Burger (1 drink)");
        burgerItemPrices.add(139);

        burgerItemNames.add("B1 (1 regular burger, 1 small fries, 1 drink)");
        burgerItemPrices.add(89);

        burgerItemNames.add("B2 (1 cheeseburger, 1 small fries, 1 drink)");
        burgerItemPrices.add(119);

        burgerItemNames.add("B3 (1 bacon cheeseburger, 1 small fries, 1 drink)");
        burgerItemPrices.add(139);

        burgerItemNames.add("B4 (2 regular burgers, 2 small fries, 2 drinks)");
        burgerItemPrices.add(169);

        burgerItemNames.add("B5 (2 jumbo burgers, 2 medium-size fries, 2 drinks)");
        burgerItemPrices.add(399);

        // Initialize Drinks and Desserts items
        drinksAndDessertsItemNames.add("Regular Soft Drink");
        drinksAndDessertsItemPrices.add(40);

        drinksAndDessertsItemNames.add("Large Soft Drink");
        drinksAndDessertsItemPrices.add(60);

        drinksAndDessertsItemNames.add("Iced Tea");
        drinksAndDessertsItemPrices.add(45);

        drinksAndDessertsItemNames.add("Milkshake (Vanilla/Chocolate)");
        drinksAndDessertsItemPrices.add(80);

        drinksAndDessertsItemNames.add("Chocolate Sundae");
        drinksAndDessertsItemPrices.add(40);

        drinksAndDessertsItemNames.add("Vanilla Sundae");
        drinksAndDessertsItemPrices.add(40);

        drinksAndDessertsItemNames.add("Caramel Sundae");
        drinksAndDessertsItemPrices.add(45);

        drinksAndDessertsItemNames.add("Banana Split");
        drinksAndDessertsItemPrices.add(80);

        drinksAndDessertsItemNames.add("Choco Chip Cookie (2 pcs)");
        drinksAndDessertsItemPrices.add(50);

        drinksAndDessertsItemNames.add("Mango Float");
        drinksAndDessertsItemPrices.add(90);

        // Initialize Coffee items
        coffeeItemNames.add("Regular Coffee");
        coffeeItemPrices.add(50);

        coffeeItemNames.add("Large Regular Coffee");
        coffeeItemPrices.add(70);

        coffeeItemNames.add("Espresso");
        coffeeItemPrices.add(55);

        coffeeItemNames.add("Americano");
        coffeeItemPrices.add(60);

        coffeeItemNames.add("Latte");
        coffeeItemPrices.add(80);

        coffeeItemNames.add("Cappuccino");
        coffeeItemPrices.add(80);

        coffeeItemNames.add("Mocha");
        coffeeItemPrices.add(90);

        coffeeItemNames.add("Iced Coffee");
        coffeeItemPrices.add(70);

        coffeeItemNames.add("Cold Brew");
        coffeeItemPrices.add(85);

        // Initialize Fries items
        friesItemNames.add("Small Fries");
        friesItemPrices.add(40);

        friesItemNames.add("Medium Fries");
        friesItemPrices.add(60);

        friesItemNames.add("Large Fries");
        friesItemPrices.add(80);

        friesItemNames.add("Cheese Fries");
        friesItemPrices.add(70);

        friesItemNames.add("Loaded Fries");
        friesItemPrices.add(100);

        friesItemNames.add("Garlic Parmesan Fries");
        friesItemPrices.add(80);

        friesItemNames.add("Spicy Fries");
        friesItemPrices.add(70);

        friesItemNames.add("BBQ Fries");
        friesItemPrices.add(75);

        friesItemNames.add("Truffle Fries");
        friesItemPrices.add(90);

        friesItemNames.add("Sweet Potato Fries");
        friesItemPrices.add(85);
    }
}
