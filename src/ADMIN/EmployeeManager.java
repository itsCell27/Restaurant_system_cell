<<<<<<< HEAD
package ADMIN;

public class EmployeeManager {
    private EmployeeVar[] employees;
    private int count;

    public EmployeeManager(int size) {
        employees = new EmployeeVar[size];
        count = 0;
    }

    public boolean register(String username, String password, String confirmPassword) {
        // Check if the passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return false; 
        }

        if (count < employees.length) {
            employees[count++] = new EmployeeVar(username, password);
            System.out.println("Employee registered successfully!");
            return true;
        }

        System.out.println("Employee limit is reached.");
        return false;
    }
}
=======
package ADMIN;

public class EmployeeManager {
    private EmployeeVar[] employees;
    private int count;

    public EmployeeManager(int size) {
        employees = new EmployeeVar[size];
        count = 0;
    }

    public boolean register(String username, String password, String confirmPassword) {
        // Check if the passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return false; 
        }

        if (count < employees.length) {
            employees[count++] = new EmployeeVar(username, password);
            System.out.println("Employee registered successfully!");
            return true;
        }

        System.out.println("Employee limit is reached.");
        return false;
    }
}
>>>>>>> branch 'Changes' of https://github.com/Andrewlars/Restaurant_system.git
