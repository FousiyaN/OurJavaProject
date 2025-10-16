package banking;

import javax.swing.*;
import java.awt.*;

public class BankingApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserDAO userDAO;
    private String currentUser;

    public BankingApp() {
        userDAO = new UserDAO();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        JPanel welcomePanel = createWelcomePanel();
        JPanel loginPanel = createLoginPanel();
        JPanel dashboardPanel = createDashboardPanel();

        mainPanel.add(welcomePanel, "Welcome");
        mainPanel.add(loginPanel, "Login");
        mainPanel.add(dashboardPanel, "Dashboard");

        add(mainPanel);
        setTitle("FinTrust Bank - Simple Banking System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout.show(mainPanel, "Welcome");
    }

    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 240, 255));

        JLabel title = new JLabel("FinTrust Bank", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setForeground(new Color(0, 51, 153));

        JLabel tagline = new JLabel("Your Money, Our Trust", SwingConstants.CENTER);
        tagline.setFont(new Font("Arial", Font.ITALIC, 16));
        tagline.setForeground(Color.DARK_GRAY);

        JButton startBtn = new JButton("Get Started");
        startBtn.setFont(new Font("Arial", Font.BOLD, 16));
        startBtn.setBackground(new Color(0, 120, 215));
        startBtn.setForeground(Color.WHITE);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1));
        centerPanel.setBackground(new Color(230, 240, 255));
        centerPanel.add(title);
        centerPanel.add(tagline);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(startBtn, BorderLayout.SOUTH);

        startBtn.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        return panel;
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField();
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");
        JButton signupBtn = new JButton("Signup");

        panel.add(userLabel); panel.add(userField);
        panel.add(passLabel); panel.add(passField);
        panel.add(loginBtn); panel.add(signupBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if(userDAO.login(user, pass)) {
                currentUser = user;
                JOptionPane.showMessageDialog(this, "Welcome " + user + "!");
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid login!");
            }
        });

        signupBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            System.out.println("Trying signup for user: " + user);
            
            if(userDAO.signup(user, pass)) {
                JOptionPane.showMessageDialog(this, "Signup successful! Please login.");
            } else {
                JOptionPane.showMessageDialog(this, "Signup failed! Username may exist.");
            }
        });

        return panel;
    }

    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton checkBalanceBtn = new JButton("Check Balance");
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        panel.add(checkBalanceBtn);
        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(logoutBtn);

        checkBalanceBtn.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Balance: $" + userDAO.getBalance(currentUser))
        );

        depositBtn.addActionListener(e -> {
            String amt = JOptionPane.showInputDialog("Enter amount to deposit:");
            try {
                double amount = Double.parseDouble(amt);
                userDAO.deposit(currentUser, amount);
                JOptionPane.showMessageDialog(this, "Deposited $" + amount);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });

        withdrawBtn.addActionListener(e -> {
            String amt = JOptionPane.showInputDialog("Enter amount to withdraw:");
            try {
                double amount = Double.parseDouble(amt);
                if(userDAO.withdraw(currentUser, amount)) {
                    JOptionPane.showMessageDialog(this, "Withdrew $" + amount);
                } else {
                    JOptionPane.showMessageDialog(this, "Insufficient balance!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount!");
            }
        });

        logoutBtn.addActionListener(e -> {
            currentUser = null;
            cardLayout.show(mainPanel, "Login");
        });

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingApp().setVisible(true));
    }
}


// end of main session 

