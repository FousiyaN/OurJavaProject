package banking;

import java.sql.*;
import java.util.Random;

public class UserDAO {

    // === SIGNUP with Account Number ===
    public boolean signup(String username, String password) {
        // Generate a random unique account number
        String accountNo = generateAccountNo();

        String sql = "INSERT INTO users (username, password, balance, account_no) VALUES (?, ?, 0, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, accountNo);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) { // MySQL duplicate key
                System.out.println("Signup failed: Username already exists.");
            } else {
                System.out.println("Signup failed: " + e.getMessage());
            }
            return false;
        }
    }

    // === LOGIN ===
    public boolean login(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? AND password=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            System.out.println("Login failed: " + e.getMessage());
            return false;
        }
    }

    // === GET ACCOUNT NUMBER ===
    public String getAccountNo(String username) {
        String sql = "SELECT account_no FROM users WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getString("account_no");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // === GET BALANCE ===
    public double getBalance(String username) {
        String sql = "SELECT balance FROM users WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) return rs.getDouble("balance");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // === DEPOSIT ===
    public void deposit(String username, double amount) {
        String sql = "UPDATE users SET balance = balance + ? WHERE username=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, amount);
            stmt.setString(2, username);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // === WITHDRAW (transaction safe) ===
    public boolean withdraw(String username, double amount) {
        String checkSql = "SELECT balance FROM users WHERE username=? FOR UPDATE";
        String updateSql = "UPDATE users SET balance = balance - ? WHERE username=?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    double balance = rs.getDouble("balance");
                    if (balance >= amount) {
                        try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                            updateStmt.setDouble(1, amount);
                            updateStmt.setString(2, username);
                            updateStmt.executeUpdate();
                        }
                        conn.commit();
                        return true;
                    }
                }
                conn.rollback(); // Not enough balance
            } catch (SQLException e) {
                conn.rollback(); // Rollback on error
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // === HELPER: Generate Random Account Number ===
    private String generateAccountNo() {
        Random rand = new Random();
        return "AC" + (100000 + rand.nextInt(900000)); // 6-digit account number
    }
}
