package banking;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class BankingApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainPanel;
    private UserDAO userDAO;
    private String currentUser;
    
    // Dashboard components that need updating
    private JLabel balanceDisplayLabel;
    private JLabel accountNoLabel;
    private JLabel usernameDisplayLabel;
    private JTextArea transactionHistoryArea;
    private ArrayList<String> transactionHistory;

    // Color Palettes - UPDATED LIGHTER PALETTE
    private final Color WELCOME_BG = new Color(240, 248, 255);
    private final Color LOGIN_BG = new Color(245, 242, 255);
    private final Color DASHBOARD_BG = new Color(248, 250, 252); // Much lighter!
    
    private final Color PRIMARY_BLUE = new Color(41, 128, 185);
    private final Color PRIMARY_PURPLE = new Color(155, 89, 182);
    private final Color GREEN_DEPOSIT = new Color(52, 211, 153);
    private final Color ORANGE_WITHDRAW = new Color(251, 146, 60);
    private final Color TEAL_BALANCE = new Color(45, 212, 191);
    private final Color RED_LOGOUT = new Color(239, 68, 68);
    
    private final Color TEXT_DARK = new Color(44, 62, 80);
    private final Color TEXT_LIGHT = new Color(127, 140, 141);
    
    // New lighter card colors
    private final Color CARD_PURPLE = new Color(139, 92, 246);
    private final Color CARD_WHITE = new Color(255, 255, 255);
    private final Color BORDER_LIGHT = new Color(226, 232, 240);

    public BankingApp() {
        userDAO = new UserDAO();
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        transactionHistory = new ArrayList<>();

        mainPanel.add(createWelcomePanel(), "Welcome");
        mainPanel.add(createLoginPanel(), "Login");
        mainPanel.add(createDashboardPanel(), "Dashboard");

        add(mainPanel);
        setTitle("FinTrust Bank - Simple Banking System");
        setSize(1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        cardLayout.show(mainPanel, "Welcome");
    }

    // --- WELCOME PANEL (unchanged) ---
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(139, 92, 246));

        JPanel decorCircle1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        decorCircle1.setBounds(50, 50, 120, 120);
        decorCircle1.setOpaque(false);
        panel.add(decorCircle1);

        JPanel decorCircle2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 30));
                g2.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        decorCircle2.setBounds(1000, 500, 150, 150);
        decorCircle2.setOpaque(false);
        panel.add(decorCircle2);

        JLabel logo = new JLabel("🏦", SwingConstants.CENTER);
        logo.setFont(new Font("Segoe UI Emoji", Font.BOLD, 100));
        logo.setBounds(525, 100, 150, 150);
        panel.add(logo);

        JLabel title = new JLabel("FinTrust Bank", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 56));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 280, 1200, 70);
        panel.add(title);

        JLabel tagline = new JLabel("Your Money, Our Trust", SwingConstants.CENTER);
        tagline.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        tagline.setForeground(new Color(230, 230, 255));
        tagline.setBounds(0, 360, 1200, 35);
        panel.add(tagline);

        JLabel feature1 = new JLabel("✨ Secure  •  Simple  •  Smart", SwingConstants.CENTER);
        feature1.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        feature1.setForeground(new Color(220, 220, 255));
        feature1.setBounds(0, 420, 1200, 30);
        panel.add(feature1);

        JButton startBtn = new JButton("Get Started →") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(new Color(240, 240, 255));
                } else if (getModel().isRollover()) {
                    g2.setColor(new Color(248, 248, 255));
                } else {
                    g2.setColor(Color.WHITE);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        startBtn.setFont(new Font("Segoe UI", Font.BOLD, 20));
        startBtn.setForeground(new Color(139, 92, 246));
        startBtn.setContentAreaFilled(false);
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        startBtn.setBounds(475, 500, 250, 60);
        startBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startBtn.addActionListener(e -> cardLayout.show(mainPanel, "Login"));
        panel.add(startBtn);

        return panel;
    }

    // --- LOGIN PANEL (unchanged) ---
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(LOGIN_BG);

        JLabel title = new JLabel("Welcome Back!", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 36));
        title.setForeground(TEXT_DARK);
        title.setBounds(0, 80, 1200, 50);
        panel.add(title);

        JLabel subtitle = new JLabel("Login to your account", SwingConstants.CENTER);
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitle.setForeground(TEXT_LIGHT);
        subtitle.setBounds(0, 135, 1200, 25);
        panel.add(subtitle);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        userLabel.setForeground(TEXT_DARK);
        userLabel.setBounds(400, 220, 400, 25);
        panel.add(userLabel);

        JTextField userField = createStyledTextField(400, 250, 400, 45);
        panel.add(userField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passLabel.setForeground(TEXT_DARK);
        passLabel.setBounds(400, 320, 400, 25);
        panel.add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        passField.setBounds(400, 350, 400, 45);
        passField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 2, true),
            BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        panel.add(passField);

        JButton loginBtn = createStyledButton("Login", PRIMARY_PURPLE, 400, 450, 190, 50);
        JButton signupBtn = createStyledButton("Signup", new Color(52, 152, 219), 610, 450, 190, 50);

        panel.add(loginBtn);
        panel.add(signupBtn);

        loginBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if(userDAO.login(user, pass)) {
                currentUser = user;
                transactionHistory.clear();
                addTransaction("LOGIN", 0, "Logged into account");
                updateDashboard();
                cardLayout.show(mainPanel, "Dashboard");
            } else {
                showBeautifulDialog("Login Failed", "Invalid username or password!", 
                    JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
            }
        });

        signupBtn.addActionListener(e -> {
            String user = userField.getText();
            String pass = new String(passField.getPassword());
            if(userDAO.signup(user, pass)) {
                String accNo = userDAO.getAccountNo(user);
                showBeautifulDialog("Success", "Signup successful!\nYour Account No: " + accNo,
                    JOptionPane.INFORMATION_MESSAGE, GREEN_DEPOSIT);
            } else {
                showBeautifulDialog("Error", "Signup failed! Username may already exist.",
                    JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
            }
        });

        return panel;
    }

    // --- ENHANCED DASHBOARD PANEL WITH LIGHTER COLORS ---
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(DASHBOARD_BG); // Much lighter background!

        // === LEFT SIDE: Account Info Card ===
        JPanel accountInfoPanel = new JPanel(null);
        accountInfoPanel.setBackground(CARD_PURPLE);
        accountInfoPanel.setBounds(40, 30, 400, 280);
        accountInfoPanel.setBorder(new CompoundBorder(
            new LineBorder(CARD_PURPLE, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel cardTitle = new JLabel("💳 Account Details");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        cardTitle.setForeground(Color.WHITE);
        cardTitle.setBounds(20, 15, 360, 30);
        accountInfoPanel.add(cardTitle);

        usernameDisplayLabel = new JLabel("Customer: " + (currentUser != null ? currentUser : "Guest"));
        usernameDisplayLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        usernameDisplayLabel.setForeground(new Color(255, 255, 255));
        usernameDisplayLabel.setBounds(20, 65, 360, 30);
        accountInfoPanel.add(usernameDisplayLabel);

        accountNoLabel = new JLabel("Account: " + (currentUser != null ? userDAO.getAccountNo(currentUser) : "N/A"));
        accountNoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        accountNoLabel.setForeground(new Color(220, 230, 255));
        accountNoLabel.setBounds(20, 105, 360, 25);
        accountInfoPanel.add(accountNoLabel);

        JLabel balanceLabel = new JLabel("Current Balance");
        balanceLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        balanceLabel.setForeground(new Color(200, 220, 255));
        balanceLabel.setBounds(20, 155, 360, 20);
        accountInfoPanel.add(balanceLabel);

        balanceDisplayLabel = new JLabel("$0.00");
        balanceDisplayLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        balanceDisplayLabel.setForeground(new Color(167, 243, 208));
        balanceDisplayLabel.setBounds(20, 180, 360, 50);
        accountInfoPanel.add(balanceDisplayLabel);

        panel.add(accountInfoPanel);

        // === RIGHT SIDE: Quick Actions ===
        JPanel actionsPanel = new JPanel(null);
        actionsPanel.setBackground(CARD_WHITE);
        actionsPanel.setBounds(460, 30, 700, 280);
        actionsPanel.setBorder(new CompoundBorder(
            new LineBorder(BORDER_LIGHT, 1, true),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel actionsTitle = new JLabel("⚡ Quick Actions");
        actionsTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        actionsTitle.setForeground(TEXT_DARK);
        actionsTitle.setBounds(20, 15, 660, 30);
        actionsPanel.add(actionsTitle);

        JButton depositBtn = createActionButton("💵 Deposit", GREEN_DEPOSIT, 20, 65, 320, 80);
        JButton withdrawBtn = createActionButton("💸 Withdraw", ORANGE_WITHDRAW, 360, 65, 320, 80);
        JButton checkBalanceBtn = createActionButton("💰 Refresh Balance", TEAL_BALANCE, 20, 165, 320, 80);
        JButton logoutBtn = createActionButton("🚪 Logout", RED_LOGOUT, 360, 165, 320, 80);

        actionsPanel.add(depositBtn);
        actionsPanel.add(withdrawBtn);
        actionsPanel.add(checkBalanceBtn);
        actionsPanel.add(logoutBtn);

        panel.add(actionsPanel);

        // === BOTTOM: Transaction History ===
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBackground(CARD_WHITE);
        historyPanel.setBounds(40, 330, 1120, 360);
        historyPanel.setBorder(new CompoundBorder(
            new LineBorder(BORDER_LIGHT, 1, true),
            new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel historyTitle = new JLabel("📜 Recent Transactions");
        historyTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        historyTitle.setForeground(TEXT_DARK);
        historyTitle.setBorder(new EmptyBorder(0, 0, 10, 0));
        historyPanel.add(historyTitle, BorderLayout.NORTH);

        transactionHistoryArea = new JTextArea();
        transactionHistoryArea.setFont(new Font("Consolas", Font.PLAIN, 14));
        transactionHistoryArea.setEditable(false);
        transactionHistoryArea.setBackground(new Color(249, 250, 251));
        transactionHistoryArea.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        JScrollPane scrollPane = new JScrollPane(transactionHistoryArea);
        scrollPane.setBorder(new LineBorder(BORDER_LIGHT, 1));
        historyPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(historyPanel);

        // === BUTTON ACTIONS ===
        depositBtn.addActionListener(e -> handleDeposit());
        withdrawBtn.addActionListener(e -> handleWithdraw());
        checkBalanceBtn.addActionListener(e -> {
            updateDashboard();
            showBeautifulNotification("✓ Balance refreshed!", GREEN_DEPOSIT);
        });
        
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            transactionHistory.clear();
            cardLayout.show(mainPanel, "Login");
        });

        return panel;
    }

    // === DEPOSIT HANDLER WITH BEAUTIFUL DIALOG ===
    private void handleDeposit() {
        JTextField amountField = createBeautifulTextField();
        
        Object[] message = {
            createDialogLabel("💵 Enter Deposit Amount"),
            Box.createVerticalStrut(10),
            createDialogSubLabel("Enter the amount you'd like to deposit"),
            Box.createVerticalStrut(15),
            amountField
        };

        int result = showBeautifulInputDialog(message, "Deposit Money", GREEN_DEPOSIT);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    userDAO.deposit(currentUser, amount);
                    addTransaction("DEPOSIT", amount, "Deposited $" + String.format("%.2f", amount));
                    updateDashboard();
                    showBeautifulNotification("✓ Successfully deposited $" + String.format("%.2f", amount), GREEN_DEPOSIT);
                } else {
                    showBeautifulDialog("Invalid Amount", "Amount must be positive!", 
                        JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
                }
            } catch (Exception ex) {
                showBeautifulDialog("Error", "Please enter a valid amount!", 
                    JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
            }
        }
    }

    // === WITHDRAW HANDLER WITH BEAUTIFUL DIALOG ===
    private void handleWithdraw() {
        JTextField amountField = createBeautifulTextField();
        
        Object[] message = {
            createDialogLabel("💸 Enter Withdrawal Amount"),
            Box.createVerticalStrut(10),
            createDialogSubLabel("Enter the amount you'd like to withdraw"),
            Box.createVerticalStrut(15),
            amountField
        };

        int result = showBeautifulInputDialog(message, "Withdraw Money", ORANGE_WITHDRAW);

        if (result == JOptionPane.OK_OPTION) {
            try {
                double amount = Double.parseDouble(amountField.getText());
                if (amount > 0) {
                    if (userDAO.withdraw(currentUser, amount)) {
                        addTransaction("WITHDRAW", amount, "Withdrew $" + String.format("%.2f", amount));
                        updateDashboard();
                        showBeautifulNotification("✓ Successfully withdrew $" + String.format("%.2f", amount), ORANGE_WITHDRAW);
                    } else {
                        showBeautifulDialog("Transaction Failed", "Insufficient balance!", 
                            JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
                    }
                } else {
                    showBeautifulDialog("Invalid Amount", "Amount must be positive!", 
                        JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
                }
            } catch (Exception ex) {
                showBeautifulDialog("Error", "Please enter a valid amount!", 
                    JOptionPane.ERROR_MESSAGE, RED_LOGOUT);
            }
        }
    }

    // === BEAUTIFUL DIALOG HELPERS ===
    private JLabel createDialogLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setForeground(TEXT_DARK);
        return label;
    }

    private JLabel createDialogSubLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_LIGHT);
        return label;
    }

    private JTextField createBeautifulTextField() {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(203, 213, 225), 2, true),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setBackground(new Color(248, 250, 252));
        return field;
    }

    private int showBeautifulInputDialog(Object[] message, String title, Color accentColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        for (Object component : message) {
            if (component instanceof Component) {
                panel.add((Component) component);
            }
        }

        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", accentColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        
        int result = JOptionPane.showConfirmDialog(this, panel, title, 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        // Reset UIManager
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
        
        return result;
    }

    private void showBeautifulDialog(String title, String message, int messageType, Color accentColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));

        String icon = messageType == JOptionPane.ERROR_MESSAGE ? "❌" : "✓";
        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(iconLabel);
        
        panel.add(Box.createVerticalStrut(15));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_DARK);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);
        
        panel.add(Box.createVerticalStrut(10));
        
        JLabel messageLabel = new JLabel("<html><center>" + message + "</center></html>");
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_LIGHT);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", accentColor);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        
        JOptionPane.showMessageDialog(this, panel, "", JOptionPane.PLAIN_MESSAGE);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
    }

    private void showBeautifulNotification(String message, Color color) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(25, 35, 25, 35));

        JLabel iconLabel = new JLabel("✓", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 56));
        iconLabel.setForeground(color);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(iconLabel);
        
        panel.add(Box.createVerticalStrut(15));
        
        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        messageLabel.setForeground(TEXT_DARK);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(messageLabel);

        UIManager.put("OptionPane.background", Color.WHITE);
        UIManager.put("Panel.background", Color.WHITE);
        UIManager.put("Button.background", color);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        
        JOptionPane.showMessageDialog(this, panel, "Success", JOptionPane.PLAIN_MESSAGE);
        
        UIManager.put("OptionPane.background", null);
        UIManager.put("Panel.background", null);
        UIManager.put("Button.background", null);
    }

    // === UPDATE DASHBOARD ===
    private void updateDashboard() {
        if (currentUser != null) {
            double balance = userDAO.getBalance(currentUser);
            balanceDisplayLabel.setText("$" + String.format("%.2f", balance));
            accountNoLabel.setText("Account: " + userDAO.getAccountNo(currentUser));
            usernameDisplayLabel.setText("Customer: " + currentUser);
            
            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-20s %-15s %-15s %s\n", "TIMESTAMP", "TYPE", "AMOUNT", "DESCRIPTION"));
            sb.append("=".repeat(100) + "\n");
            for (int i = transactionHistory.size() - 1; i >= 0; i--) {
                sb.append(transactionHistory.get(i)).append("\n");
            }
            transactionHistoryArea.setText(sb.toString());
        }
    }

    // === ADD TRANSACTION TO HISTORY ===
    private void addTransaction(String type, double amount, String description) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String timestamp = sdf.format(new Date());
        String amountStr = amount > 0 ? String.format("$%.2f", amount) : "-";
        String record = String.format("%-20s %-15s %-15s %s", timestamp, type, amountStr, description);
        transactionHistory.add(record);
    }

    // === HELPER: Create Action Button ===
    private JButton createActionButton(String text, Color baseColor, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new CompoundBorder(
            new LineBorder(baseColor, 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        
        return button;
    }

    // === HELPER: Create Styled Button ===
    private JButton createStyledButton(String text, Color baseColor, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBounds(x, y, width, height);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new CompoundBorder(
            new LineBorder(baseColor, 2, true),
            new EmptyBorder(10, 20, 10, 20)
        ));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(baseColor.brighter());
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(baseColor);
            }
        });
        
        return button;
    }

    // === HELPER: Create Styled TextField ===
    private JTextField createStyledTextField(int x, int y, int width, int height) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBounds(x, y, width, height);
        field.setBorder(new CompoundBorder(
            new LineBorder(new Color(189, 195, 199), 2, true),
            new EmptyBorder(5, 15, 5, 15)
        ));
        return field;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankingApp().setVisible(true));
    }
}
