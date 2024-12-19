package ADMIN;

public class EmployeeVar {
    private String employeeId;  // Employee ID (7 characters long)
    private String name;
    private String password;
    private String position;  // Employee role (e.g., Manager, Counter, Kitchen, etc.)

    // Constructor to initialize all fields
    public EmployeeVar(String employeeId, String name, String password, String position) {
        this.employeeId = employeeId;
        this.name = name;
        this.password = password;
        this.position = position;
    }

    // Getters and Setters for all fields
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
