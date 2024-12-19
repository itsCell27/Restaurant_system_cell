package ADMIN;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class EmployeeManager {
    private EmployeeVar[] employees;
    private int count;
    private static final String FILE_PATH = "EmployeeList/employees.csv";  // Relative path to the CSV file
    RegisterEmployeeMenu rgs = new RegisterEmployeeMenu();

    public EmployeeManager(int size) {
        employees = new EmployeeVar[size];
        count = 0;
        loadEmployeesFromFile();  // Load employees from the file when the object is created
    }

    // Load employees from the CSV file
    private void loadEmployeesFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("\t\t\tNo employee data file found, starting fresh.");
            return;  // Skip loading if the file does not exist
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {  // Adjust to include ID and role
                    String employeeId = data[0];
                    String name = data[1];
                    String password = data[2];
                    String position = data[3];
                    employees[count++] = new EmployeeVar(employeeId, name, password, position);
                }
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError while loading employee data: " + e.getMessage());
        }
    }

    // Register a new employee
    public boolean register(String employeeId, String name, String password, String confirmPassword, String position) {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            System.out.println("\t\t\tPasswords do not match.");
            return false;
        }

        if (count < employees.length) {
            // Create and register the employee with the ID, Name, password, and role
            employees[count++] = new EmployeeVar(employeeId, name, password, position);
            saveEmployeesToFile();  // Save employees to file after adding a new one
            return true;
        }

        System.out.println("\t\t\tEmployee limit reached.");
        return false;
    }

    // Get the current count of employees
    public int getEmployeeCount() {
        return count;  // Return the current employee count
    }

    // Save the employees to the CSV file
    public boolean saveEmployeesToFile() {
        File folder = new File("EmployeeList");
        if (!folder.exists()) {
            folder.mkdirs();  // Create the folder if it doesn't exist
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write the employee data
            for (int i = 0; i < count; i++) {
                writer.write(employees[i].getEmployeeId() + ","
                        + employees[i].getName() + ","
                        + employees[i].getPassword() + ","
                        + employees[i].getPosition());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("\t\t\tError while saving employee data: " + e.getMessage());
            return false;  // Return false if there was an error saving
        }

        return true;  // Return true if save was successful
    }

    // Edit an existing employee's credentials
    public boolean editEmployee(String employeeId, String newName, String newPassword, String confirmPassword, String newPosition) {
        // Find employee by ID
        EmployeeVar employee = findEmployeeById(employeeId);
        
        if (employee == null) {
            System.out.println("\t\t\tEmployee not found.");
            return false;
        }

        // Validate password match
        if (!newPassword.equals(confirmPassword)) {
            System.out.println("\t\t\tPasswords do not match.");
            return false;
        }

        // Update employee details
        employee.setName(newName);
        employee.setPassword(newPassword);
        employee.setPosition(newPosition);

        // Save updated employee data to file
        saveEmployeesToFile();
        System.out.println("\t\t\tEmployee details updated successfully!");
        return true;
    }

    // Find employee by their ID
    public EmployeeVar findEmployeeById(String employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                return employees[i];
            }
        }
        return null;  // Employee not found
    }

    // Delete an employee by ID
    public void deleteEmployee(String employeeId) {
        for (int i = 0; i < count; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                // Shift elements to the left to remove the employee
                for (int j = i; j < count - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--count] = null;  // Nullify the last element
                saveEmployeesToFile();  // Save the updated employee list
                System.out.println("\t\t\tEmployee deleted successfully.");
                return;
            }
        }
        System.out.println("\t\t\tEmployee not found.");
    }
}
