package ADMIN;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewEmployees {

    // Constructor to read and display the CSV file content
    public void ViewEmployees() {
        String filePath = "Employeelist/employees.csv"; // Path to the CSV file

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            // Skip the header line (optional)
            br.readLine();

            // Read each line and display its content
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(","); // Split by comma

                // Display the username and password
                System.out.println("Username: " + columns[0] + ", Password: " + columns[1]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
