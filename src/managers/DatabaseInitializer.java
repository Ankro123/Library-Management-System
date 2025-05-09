package src.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.swing.JOptionPane;

import java.sql.SQLException;

public class DatabaseInitializer {
    private static final String url = "jdbc:mysql://localhost:3306";
    private static String user = "";
    private static String password = "";

    public static int initialize() {
        try {         
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS LMS");

            conn = DatabaseManager.GetConnection();
            stmt = conn.createStatement();

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Student (" +
                            "ERP_ID VARCHAR(10) PRIMARY KEY, " +
                            "Name VARCHAR(50), " +
                            "Course VARCHAR(5))");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Book (" +
                            "Book_ID VARCHAR(10) PRIMARY KEY, " +
                            "Title VARCHAR(50), " +
                            "Author VARCHAR(50), " +
                            "Availability ENUM('Yes', 'No') DEFAULT 'Yes')");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Transactions (" +
                            "Transaction_ID INT AUTO_INCREMENT PRIMARY KEY, " +
                            "Student_ERP VARCHAR(10), " +
                            "Book_ID VARCHAR(10), " +
                            "Type ENUM('Borrow', 'Return'), " +
                            "Transaction_Date DATE, " +
                            "Due_Date DATE)");

            stmt.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS Users (" +
                            "Username VARCHAR(50) PRIMARY KEY, " +
                            "Password VARCHAR(50));");

            return 1;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Connecting to SQL, Please Check SQLConfig.txt!", "ERROR", JOptionPane.ERROR_MESSAGE);
            return 0;
            }
    }

    public static void getCreds() throws IOException {
        String creds = Files.readString(Path.of("lib/SQLConfig.txt"));
        String[] arr = creds.split("\n");
        user = arr[0].trim();
        password = arr[1].trim();
    }
}
