import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.io.*;
import java.util.ArrayList;

public class homePage {
    int borderWidth = 1280;
    int borderHeight = 832;
    Color customViolet = new Color(46, 45, 77);
    Color customGhostWhite = new Color(248, 244, 249);
    
    JFrame frame = new JFrame("Home Page");

    public homePage() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customGhostWhite);
        frame.setLayout(null);

        // --- SIDEBAR LABELS (now clickable) ---
        JLabel homeLabel = new JLabel("Home");
        homeLabel.setBounds(60, 147, 35, 15);
        homeLabel.setFont(new Font("Inter", Font.BOLD, 12));
        homeLabel.setForeground(customViolet);
        homeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        homeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        homeLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Home"); }
        });
        frame.add(homeLabel);

        JLabel depositLabel = new JLabel("Deposit");
        depositLabel.setBounds(60, 190, 55, 15);
        depositLabel.setFont(new Font("Inter", Font.BOLD, 12));
        depositLabel.setForeground(customViolet);
        depositLabel.setHorizontalAlignment(SwingConstants.LEFT);
        depositLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        depositLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Deposit"); }
        });
        frame.add(depositLabel);

        JLabel withdrawLabel = new JLabel("Withdraw");
        withdrawLabel.setBounds(60, 233, 65, 15);
        withdrawLabel.setFont(new Font("Inter", Font.BOLD, 12));
        withdrawLabel.setForeground(customViolet);
        withdrawLabel.setHorizontalAlignment(SwingConstants.LEFT);
        withdrawLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        withdrawLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Withdraw"); }
        });
        frame.add(withdrawLabel);

        JLabel savingsLabel = new JLabel("Savings");
        savingsLabel.setBounds(60, 276, 55, 15);
        savingsLabel.setFont(new Font("Inter", Font.BOLD, 12));
        savingsLabel.setForeground(customViolet);
        savingsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        savingsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        savingsLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Savings"); }
        });
        frame.add(savingsLabel);

        JLabel cardLabel = new JLabel("Card");
        cardLabel.setBounds(60, 319, 35, 15);
        cardLabel.setFont(new Font("Inter", Font.BOLD, 12));
        cardLabel.setForeground(customViolet);
        cardLabel.setHorizontalAlignment(SwingConstants.LEFT);
        cardLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cardLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Card"); }
        });
        frame.add(cardLabel);

        JLabel inboxLabel = new JLabel("Inbox");
        inboxLabel.setBounds(60, 362, 35, 15);
        inboxLabel.setFont(new Font("Inter", Font.BOLD, 12));
        inboxLabel.setForeground(customViolet);
        inboxLabel.setHorizontalAlignment(SwingConstants.LEFT);
        inboxLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        inboxLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Inbox"); }
        });
        frame.add(inboxLabel);

        JLabel profileLabel = new JLabel("Profile");
        profileLabel.setBounds(60, 405, 45, 15);
        profileLabel.setFont(new Font("Inter", Font.BOLD, 12));
        profileLabel.setForeground(customViolet);
        profileLabel.setHorizontalAlignment(SwingConstants.LEFT);
        profileLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        profileLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { switchToPage("Profile"); }
        });
        frame.add(profileLabel);

        // --- VERTICAL DIVIDER ---
        JLabel verticalLine = new JLabel();
        verticalLine.setBounds(238, 0, 1, borderHeight);
        verticalLine.setOpaque(true);
        verticalLine.setBackground(Color.LIGHT_GRAY);
        frame.add(verticalLine);

        // --- INITIAL CONTENT ---
        buildHomeContent();   // Loads full home page on startup

        // --- LOGO (always stays) ---
        JLabel fern = new JLabel("FERN");
        fern.setBounds(57, 42, 85, 33);
        fern.setFont(new Font("Inter", Font.BOLD, 32)); 
        fern.setForeground(customViolet);
        fern.setHorizontalAlignment(SwingConstants.CENTER);
        fern.setVerticalAlignment(SwingConstants.TOP);
        frame.add(fern); 

        JLabel bank = new JLabel("Bank");
        bank.setBounds(60, 69, 79, 39);
        bank.setFont(new Font("Inter", Font.BOLD, 32)); 
        bank.setForeground(customGhostWhite);
        bank.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(bank); 

        JPanel logoBG = new JPanel();
        logoBG.setBounds(60, 73, 79, 31);
        logoBG.setBackground(customViolet);
        frame.add(logoBG);

        frame.setVisible(true);
    }    

    // ===================================================================
    // ================== NAVIGATION METHODS (NEW) ======================
    // ===================================================================

    private void clearMainContent() {
        Component[] components = frame.getContentPane().getComponents();
        for (Component c : components) {
            // Keep everything on the left (sidebar + logo + divider)
            if (c.getBounds().x > 238) {
                frame.remove(c);
            }
        }
        frame.revalidate();
        frame.repaint();
    }

    private void buildHomeContent() {
        // --- MAIN CONTENT (Wallet + Balance + Card + Transactions) ---

        JLabel walletLabel = new JLabel("Wallet");
        walletLabel.setBounds(340, 133, 75, 29);
        walletLabel.setFont(new Font("Inter", Font.BOLD, 24));
        walletLabel.setForeground(Color.BLACK);
        walletLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(walletLabel);

        JLabel availableBalanceLabel = new JLabel("Available Balance");
        availableBalanceLabel.setBounds(360, 203, 165, 24);
        availableBalanceLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        availableBalanceLabel.setForeground(customGhostWhite);
        availableBalanceLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(availableBalanceLabel);

        // Balance Amount Label
        JLabel balanceAmountLabel = new JLabel("₱" + String.format("%,.2f", SignInPage.currentUserBalance));
        balanceAmountLabel.setBounds(360, 254, 300, 39);
        balanceAmountLabel.setFont(new Font("Inter", Font.PLAIN, 32));
        balanceAmountLabel.setForeground(customGhostWhite);
        balanceAmountLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(balanceAmountLabel);

        // === FIXED Toggle Button (Show/Hide Balance) - no visual bugs ===
        JButton toggleBalanceBtn = new JButton("Hide Balance");
        toggleBalanceBtn.setBounds(360, 375, 145, 22);
        toggleBalanceBtn.setFont(new Font("Inter", Font.PLAIN, 14));
        toggleBalanceBtn.setForeground(customGhostWhite);
        toggleBalanceBtn.setBackground(new Color(0, 0, 0, 0));
        toggleBalanceBtn.setOpaque(false);
        toggleBalanceBtn.setContentAreaFilled(false);
        toggleBalanceBtn.setBorderPainted(false);
        toggleBalanceBtn.setFocusPainted(false);
        toggleBalanceBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleBalanceBtn.setHorizontalAlignment(SwingConstants.LEFT);

        final double actualBalance = SignInPage.currentUserBalance;
        final boolean[] balanceVisible = {true};

        toggleBalanceBtn.addActionListener(e -> {
            balanceVisible[0] = !balanceVisible[0];

            if (balanceVisible[0]) {
                balanceAmountLabel.setText("₱" + String.format("%,.2f", actualBalance));
                toggleBalanceBtn.setText("Hide Balance");
            } else {
                balanceAmountLabel.setText("₱••••••.••");
                toggleBalanceBtn.setText("Show Balance");
            }

            balanceAmountLabel.repaint();
            toggleBalanceBtn.repaint();
            frame.repaint();
        });
        frame.add(toggleBalanceBtn);

        // Masked Card Number (last 4 digits only)
        String card = SignInPage.currentUserCardNumber;
        String lastFour = (card.length() >= 4) ? card.substring(card.length() - 4) : "0000";
        String maskedCard = "**** **** **** " + lastFour;
        JLabel cardNumberLabel = new JLabel(maskedCard);
        cardNumberLabel.setBounds(895, 363, 147, 19);
        cardNumberLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        cardNumberLabel.setForeground(customGhostWhite);
        cardNumberLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(cardNumberLabel);

        JLabel visaLabel = new JLabel("VISA");
        visaLabel.setBounds(1069, 360, 39, 14);
        visaLabel.setFont(new Font("Inter", Font.ITALIC, 16));
        visaLabel.setForeground(new Color(0x1B4DC3));
        visaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        visaLabel.setVerticalAlignment(SwingConstants.CENTER);
        frame.add(visaLabel);
        
        JPanel visaLogoBG = createRoundedPanel(1066, 356, 45, 25, 10, null, 0);
        visaLogoBG.setBackground(customGhostWhite);
        frame.add(visaLogoBG);

        JLabel TransactionHistoryLabel = new JLabel("Transactions");
        TransactionHistoryLabel.setBounds(337, 446, 128, 24);
        TransactionHistoryLabel.setFont(new Font("Inter", Font.BOLD, 20));
        TransactionHistoryLabel.setForeground(Color.BLACK);
        TransactionHistoryLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(TransactionHistoryLabel);

        JPanel transaction1 = createRoundedPanel(336, 506, 44, 44, 100, null, 0);
        transaction1.setBackground(Color.LIGHT_GRAY);
        frame.add(transaction1);

        JPanel transaction2 = createRoundedPanel(336, 578, 44, 44, 100, null, 0);
        transaction2.setBackground(Color.LIGHT_GRAY);
        frame.add(transaction2);

        JLabel WithdrawExampleLabel = new JLabel("Withdrawal");
        WithdrawExampleLabel.setBounds(396, 508, 217, 19);
        WithdrawExampleLabel.setFont(new Font("Inter", Font.BOLD, 16));
        WithdrawExampleLabel.setForeground(Color.BLACK);
        WithdrawExampleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(WithdrawExampleLabel);

        JLabel withdrawAmountLabel = new JLabel("-₱1,500.00");
        withdrawAmountLabel.setBounds(396, 534, 199, 19);
        withdrawAmountLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        withdrawAmountLabel.setForeground(new Color(0xD9534F));
        frame.add(withdrawAmountLabel);

        JLabel DepositExampleLabel = new JLabel("Deposit");
        DepositExampleLabel.setBounds(396, 580, 217, 19);
        DepositExampleLabel.setFont(new Font("Inter", Font.BOLD, 16));  
        DepositExampleLabel.setForeground(Color.BLACK);
        DepositExampleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(DepositExampleLabel);

        JLabel depositAmountLabel = new JLabel("+₱5,000.00");
        depositAmountLabel.setBounds(396, 606, 199, 19);
        depositAmountLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        depositAmountLabel.setForeground(new Color(0x5CB85C));
        frame.add(depositAmountLabel);

        JPanel balanceBG = createRoundedPanel(336, 176, 804, 234, 30, null, 0);
        balanceBG.setBackground(customViolet);
        frame.add(balanceBG);
    }

    private void switchToPage(String page) {
        if ("Savings".equals(page) || "Card".equals(page) || "Inbox".equals(page)) {
            // Redirect to errorCatcher.java and close homePage
            frame.dispose();
            new errorCatcher();
        } else {
            // Keep sidebar + logo for Home / Deposit / Withdraw / Profile
            clearMainContent();

            if ("Home".equals(page)) {
                buildHomeContent();   // Restore full home UI
            } else if ("Deposit".equals(page)) {
                depositPage();        // Load deposit page UI
            } else if ("Withdraw".equals(page)) { // <-- ADD THIS BLOCK
                withdrawPage();
            }
            
            frame.revalidate();
            frame.repaint();
        }
    }

    private void depositPage() {
        // --- Formatter: Adds commas, hides decimals unless they exist ---
        DecimalFormat df = new DecimalFormat("#,##0.##");

        JLabel depositLabel = new JLabel("Deposit");
        depositLabel.setBounds(340, 133, 92, 30);
        depositLabel.setFont(new Font("Inter", Font.BOLD, 24));
        depositLabel.setForeground(customViolet);
        depositLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(depositLabel);

        JLabel requestDepositLabel = new JLabel("Request Deposit");
        requestDepositLabel.setBounds(360, 198, 165, 24);
        requestDepositLabel.setFont(new Font("Inter", Font.BOLD, 20));
        requestDepositLabel.setForeground(customGhostWhite);
        requestDepositLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(requestDepositLabel);

        JLabel cashSign = new JLabel("₱");
        cashSign.setBounds(386, 298, 47, 77);
        cashSign.setFont(new Font("Inter", Font.PLAIN, 64));
        cashSign.setForeground(Color.BLACK);
        cashSign.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(cashSign);

        // --- Starts with exactly "0" ---
        JTextField amountField = new JTextField("0");
        amountField.setBounds(749, 298, 336, 77);
        amountField.setFont(new Font("Inter", Font.PLAIN, 64));
        amountField.setForeground(Color.BLACK);
        amountField.setHorizontalAlignment(JTextField.RIGHT);
        amountField.setBorder(BorderFactory.createEmptyBorder()); 
        amountField.setOpaque(false); 
        amountField.setCaretColor(customViolet); 
        frame.add(amountField);

        // --- Focus Listener: Removes the "0" when clicked so it doesn't leave a leading zero ---
        amountField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (amountField.getText().equals("0")) {
                    amountField.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (amountField.getText().isEmpty()) {
                    amountField.setText("0");
                }
            }
        });

        JLabel hundred = new JLabel("₱100");
        hundred.setBounds(409, 441, 59, 29);
        hundred.setFont(new Font("Inter", Font.PLAIN, 24));
        hundred.setForeground(customGhostWhite);
        hundred.setHorizontalAlignment(SwingConstants.LEFT);
        hundred.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(hundred);

        JLabel fivehundred = new JLabel("₱500");
        fivehundred.setBounds(609, 441, 59, 29);
        fivehundred.setFont(new Font("Inter", Font.PLAIN, 24));
        fivehundred.setForeground(customGhostWhite);
        fivehundred.setHorizontalAlignment(SwingConstants.LEFT);
        fivehundred.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fivehundred);

        JLabel onethousand = new JLabel("₱1,000");
        onethousand.setBounds(799, 441, 79, 29);
        onethousand.setFont(new Font("Inter", Font.PLAIN, 24));
        onethousand.setForeground(customGhostWhite);
        onethousand.setHorizontalAlignment(SwingConstants.LEFT);
        onethousand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(onethousand);

        JLabel fiveThousand = new JLabel("₱5,000");
        fiveThousand.setBounds(997, 441, 83, 29);
        fiveThousand.setFont(new Font("Inter", Font.PLAIN, 24));
        fiveThousand.setForeground(customGhostWhite);
        fiveThousand.setHorizontalAlignment(SwingConstants.LEFT);
        fiveThousand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fiveThousand);

        JPanel hundredBG = createRoundedPanel(363, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        hundredBG.setBackground(customViolet);
        hundredBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(hundredBG);

        JPanel fivehundredBG = createRoundedPanel(563, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        fivehundredBG.setBackground(customViolet);
        fivehundredBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fivehundredBG);

        JPanel onethousandBG = createRoundedPanel(753, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        onethousandBG.setBackground(customViolet);
        onethousandBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(onethousandBG);

        JPanel fiveThousandBG = createRoundedPanel(953, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        fiveThousandBG.setBackground(customViolet);
        fiveThousandBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fiveThousandBG);

        // =======================================================
        // --- PRESET BUTTON LISTENERS (Uses DecimalFormat) ---
        // =======================================================
        
        MouseAdapter add100 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {} 
                amountField.setText(df.format(current + 100));
            }
        };
        hundred.addMouseListener(add100);
        hundredBG.addMouseListener(add100);

        MouseAdapter add500 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {}
                amountField.setText(df.format(current + 500));
            }
        };
        fivehundred.addMouseListener(add500);
        fivehundredBG.addMouseListener(add500);

        MouseAdapter add1000 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {}
                amountField.setText(df.format(current + 1000));
            }
        };
        onethousand.addMouseListener(add1000);
        onethousandBG.addMouseListener(add1000);

        MouseAdapter add5000 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {}
                amountField.setText(df.format(current + 5000));
            }
        };
        fiveThousand.addMouseListener(add5000);
        fiveThousandBG.addMouseListener(add5000);

        // =======================================================

        JPanel amountPanel = createRoundedPanel(360, 269, 750, 136, 18, null, 0);
        amountPanel.setBackground(customGhostWhite);
        frame.add(amountPanel); 

        JPanel line1 = new JPanel();
        line1.setBounds(360, 240, 750, 1);
        line1.setBackground(new Color(0xBBBBBB));
        frame.add(line1);

        JPanel line2 = new JPanel();
        line2.setBounds(360, 507, 750, 1);
        line2.setBackground(new Color(0xBBBBBB));
        frame.add(line2);

        JLabel depositFund = new JLabel("Deposit Fund");
        depositFund.setBounds(662, 587, 151, 29);
        depositFund.setFont(new Font("Inter", Font.PLAIN, 24));
        depositFund.setForeground(customGhostWhite);
        depositFund.setHorizontalAlignment(SwingConstants.LEFT);
        depositFund.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        frame.add(depositFund);

        JPanel depositPanel = createRoundedPanel(336, 176, 804, 361, 30, null, 0);
        depositPanel.setBackground(customViolet);
        frame.add(depositPanel);

        JPanel deposPanel = createRoundedPanel(638, 566, 200, 70, 18, null, 0);
        deposPanel.setBackground(customViolet);
        deposPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(deposPanel);

        // =======================================================
        // --- SUBMIT DEPOSIT LISTENER ---
        // =======================================================
        MouseAdapter confirmDeposit = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    String text = amountField.getText().replace(",", "");
                    if (text.isEmpty()) text = "0";
                    double depositAmount = Double.parseDouble(text);

                    if (depositAmount > 0) {
                        // 1. Add to user's temporary balance
                        SignInPage.currentUserBalance += depositAmount; 
                        
                        // 2. PERMANENTLY SAVE IT TO THE TEXT FILE
                        updateBalanceInFile(SignInPage.currentUserBalance);
                        
                        // 3. Show success popup
                        JOptionPane.showMessageDialog(frame, "Successfully deposited ₱" + df.format(depositAmount), "Deposit Success", JOptionPane.INFORMATION_MESSAGE);
                        
                        // 4. Reset field and go back to home page
                        amountField.setText("0");
                        switchToPage("Home");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter an amount greater than 0.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        // Attach the listener to both the text and the button background
        depositFund.addMouseListener(confirmDeposit);
        deposPanel.addMouseListener(confirmDeposit);
    }

    private void withdrawPage() {
        DecimalFormat df = new DecimalFormat("#,##0.##");

        JLabel withdrawLabel = new JLabel("Withdraw");
        withdrawLabel.setBounds(340, 133, 120, 30);
        withdrawLabel.setFont(new Font("Inter", Font.BOLD, 24));
        withdrawLabel.setForeground(customViolet);
        withdrawLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(withdrawLabel);

        JLabel requestWithdrawLabel = new JLabel("Request Withdraw");
        requestWithdrawLabel.setBounds(360, 198, 200, 24);
        requestWithdrawLabel.setFont(new Font("Inter", Font.BOLD, 20));
        requestWithdrawLabel.setForeground(customGhostWhite);
        requestWithdrawLabel.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(requestWithdrawLabel);

        JLabel cashSign = new JLabel("₱");
        cashSign.setBounds(386, 298, 47, 77);
        cashSign.setFont(new Font("Inter", Font.PLAIN, 64));
        cashSign.setForeground(Color.BLACK);
        cashSign.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(cashSign);

        JTextField amountField = new JTextField("0");
        amountField.setBounds(749, 298, 336, 77);
        amountField.setFont(new Font("Inter", Font.PLAIN, 64));
        amountField.setForeground(Color.BLACK);
        amountField.setHorizontalAlignment(JTextField.RIGHT);
        amountField.setBorder(BorderFactory.createEmptyBorder()); 
        amountField.setOpaque(false); 
        amountField.setCaretColor(customViolet); 
        frame.add(amountField);

        amountField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (amountField.getText().equals("0")) {
                    amountField.setText("");
                }
            }
            public void focusLost(FocusEvent e) {
                if (amountField.getText().isEmpty()) {
                    amountField.setText("0");
                }
            }
        });

        JLabel hundred = new JLabel("₱100");
        hundred.setBounds(409, 441, 59, 29);
        hundred.setFont(new Font("Inter", Font.PLAIN, 24));
        hundred.setForeground(customGhostWhite);
        hundred.setHorizontalAlignment(SwingConstants.LEFT);
        hundred.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(hundred);

        JLabel fivehundred = new JLabel("₱500");
        fivehundred.setBounds(609, 441, 59, 29);
        fivehundred.setFont(new Font("Inter", Font.PLAIN, 24));
        fivehundred.setForeground(customGhostWhite);
        fivehundred.setHorizontalAlignment(SwingConstants.LEFT);
        fivehundred.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fivehundred);

        JLabel onethousand = new JLabel("₱1,000");
        onethousand.setBounds(799, 441, 79, 29);
        onethousand.setFont(new Font("Inter", Font.PLAIN, 24));
        onethousand.setForeground(customGhostWhite);
        onethousand.setHorizontalAlignment(SwingConstants.LEFT);
        onethousand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(onethousand);

        JLabel fiveThousand = new JLabel("₱5,000");
        fiveThousand.setBounds(997, 441, 83, 29);
        fiveThousand.setFont(new Font("Inter", Font.PLAIN, 24));
        fiveThousand.setForeground(customGhostWhite);
        fiveThousand.setHorizontalAlignment(SwingConstants.LEFT);
        fiveThousand.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fiveThousand);

        JPanel hundredBG = createRoundedPanel(363, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        hundredBG.setBackground(customViolet);
        hundredBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(hundredBG);

        JPanel fivehundredBG = createRoundedPanel(563, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        fivehundredBG.setBackground(customViolet);
        fivehundredBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fivehundredBG);

        JPanel onethousandBG = createRoundedPanel(753, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        onethousandBG.setBackground(customViolet);
        onethousandBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(onethousandBG);

        JPanel fiveThousandBG = createRoundedPanel(953, 434, 150, 44, 12, new Color(0xBBBBBB), 1);
        fiveThousandBG.setBackground(customViolet);
        fiveThousandBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(fiveThousandBG);

        // =======================================================
        // --- PRESET BUTTON LISTENERS ---
        // =======================================================
        MouseAdapter add100 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {} 
                amountField.setText(df.format(current + 100));
            }
        };
        hundred.addMouseListener(add100);
        hundredBG.addMouseListener(add100);

        MouseAdapter add500 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {}
                amountField.setText(df.format(current + 500));
            }
        };
        fivehundred.addMouseListener(add500);
        fivehundredBG.addMouseListener(add500);

        MouseAdapter add1000 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {}
                amountField.setText(df.format(current + 1000));
            }
        };
        onethousand.addMouseListener(add1000);
        onethousandBG.addMouseListener(add1000);

        MouseAdapter add5000 = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                double current = 0;
                try {
                    String text = amountField.getText().replace(",", "");
                    if (!text.isEmpty()) current = Double.parseDouble(text);
                } catch (NumberFormatException ex) {}
                amountField.setText(df.format(current + 5000));
            }
        };
        fiveThousand.addMouseListener(add5000);
        fiveThousandBG.addMouseListener(add5000);

        // =======================================================

        JPanel amountPanel = createRoundedPanel(360, 269, 750, 136, 18, null, 0);
        amountPanel.setBackground(customGhostWhite);
        frame.add(amountPanel); 

        JPanel line1 = new JPanel();
        line1.setBounds(360, 240, 750, 1);
        line1.setBackground(new Color(0xBBBBBB));
        frame.add(line1);

        JPanel line2 = new JPanel();
        line2.setBounds(360, 507, 750, 1);
        line2.setBackground(new Color(0xBBBBBB));
        frame.add(line2);

        JLabel withdrawFundLabel = new JLabel("Withdraw Fund");
        withdrawFundLabel.setBounds(653, 587, 180, 29);
        withdrawFundLabel.setFont(new Font("Inter", Font.PLAIN, 24));
        withdrawFundLabel.setForeground(customGhostWhite);
        withdrawFundLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centered text
        withdrawFundLabel.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        frame.add(withdrawFundLabel);

        JPanel mainWithdrawBG = createRoundedPanel(336, 176, 804, 361, 30, null, 0);
        mainWithdrawBG.setBackground(customViolet);
        frame.add(mainWithdrawBG);

        JPanel confirmWithdrawBtnBG = createRoundedPanel(638, 566, 200, 70, 18, null, 0);
        confirmWithdrawBtnBG.setBackground(customViolet);
        confirmWithdrawBtnBG.setCursor(new Cursor(Cursor.HAND_CURSOR));
        frame.add(confirmWithdrawBtnBG);

        // =======================================================
        // --- SUBMIT WITHDRAW LISTENERS ---
        // =======================================================
        MouseAdapter confirmWithdraw = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                try {
                    // Get text and remove commas for math
                    String text = amountField.getText().replace(",", "");
                    if (text.isEmpty()) text = "0";
                    double withdrawAmount = Double.parseDouble(text);

                    if (withdrawAmount > 0) {
                        // CHECK IF USER HAS ENOUGH MONEY
                        if (withdrawAmount <= SignInPage.currentUserBalance) {
                            
                            // 1. Deduct from user's balance
                            SignInPage.currentUserBalance -= withdrawAmount; 
                            
                            // 2. Save it permanently to the text file
                            updateBalanceInFile(SignInPage.currentUserBalance);
                            
                            // 3. Show success popup
                            JOptionPane.showMessageDialog(frame, "Successfully withdrew ₱" + df.format(withdrawAmount), "Withdraw Success", JOptionPane.INFORMATION_MESSAGE);
                            
                            // 4. Reset field and go back to home page
                            amountField.setText("0");
                            switchToPage("Home");

                        } else {
                            // ERROR: Not enough money! Show custom error screen.
                            frame.dispose(); // Close the home page
                            errorCatcher ec = new errorCatcher();
                            ec.paymentDeclined(); // Trigger your custom UI
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please enter an amount greater than 0.", "Invalid Amount", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        
        // Attach the listener to both the text and the button background
        withdrawFundLabel.addMouseListener(confirmWithdraw);
        confirmWithdrawBtnBG.addMouseListener(confirmWithdraw);
    }

    private JPanel createRoundedPanel(int x, int y, int w, int h, int radius, Color strokeColor, int strokeWidth) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius));
                if (strokeColor != null && strokeWidth > 0) {
                    g2.setStroke(new BasicStroke(strokeWidth));
                    g2.setColor(strokeColor);
                    g2.draw(new RoundRectangle2D.Float(strokeWidth/2f, strokeWidth/2f, getWidth()-strokeWidth-1, getHeight()-strokeWidth-1, radius, radius));
                }
                g2.dispose();
            }
        };
        panel.setBounds(x, y, w, h);
        panel.setOpaque(false);
        return panel;
    }

    private void updateBalanceInFile(double newBalance) {
        File file = new File("credentials.txt");
        ArrayList<String> fileContent = new ArrayList<>();
        
        try {
            // Read all existing lines from the file
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // If this line belongs to the logged-in user, update the balance (parts[2])
                if (parts.length >= 4 && parts[0].equals(SignInPage.currentUser)) {
                    // Using Locale.US ensures it saves with a dot (e.g., 150.00) instead of a comma
                    parts[2] = String.format(java.util.Locale.US, "%.2f", newBalance);
                    line = String.join(",", parts); // Stitch the line back together
                }
                fileContent.add(line);
            }
            reader.close();

            // Overwrite the file with the updated data
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            for (String updatedLine : fileContent) {
                writer.write(updatedLine);
                writer.newLine();
            }
            writer.close();
            
        } catch (IOException ex) {
            System.out.println("Error saving to file: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new homePage();
    }
}