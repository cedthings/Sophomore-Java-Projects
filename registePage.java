import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class registePage {
    int borderWidth = 1440;
    int borderHeight = 1024;

    Color customGreen = new Color(9, 70, 75);
    Color customDarkGreen = new Color(48, 51, 34);
    Color customGray = new Color(217, 217, 217);
    Color customWhite = new Color(254, 254, 254);

    JFrame frame = new JFrame("LMS - Registration");
    JLabel worldName = new JLabel("Republic of the World Teyvat");
    JLabel sat = new JLabel("SUMERU AKADEMIYA AND TECHNOLOGY");
    JLabel sea = new JLabel("SCIENCE EDUCATION AND ACADEMY");
    JPanel logoLine = new JPanel();

    public registePage() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customGreen);
        frame.setLayout(null);

        showRegistrationForm();

        frame.setVisible(true);
    }

    private void showRegistrationForm() {
        frame.getContentPane().removeAll();
        addLogosToFrame();

        // --- UI PANELS ---
        JPanel registerPanel = createRoundedPanel(341, 271, 759, 550, 24);
        registerPanel.setBackground(customWhite);

        JPanel registerPhoto = createRoundedPanel(368, 308, 342, 485, 18);
        registerPhoto.setBackground(customGray);

        // --- LABELS ---
        JLabel nameLbl = createLabel("FULL NAME", 768, 316);
        JLabel uniLbl = createLabel("UNIVERSITY", 768, 396);
        JLabel progLbl = createLabel("PROGRAM", 768, 476);
        JLabel yearLbl = createLabel("SCHOOL YEAR-SEMESTER", 768, 556);
        JLabel gwaLbl = createLabel("GWA", 768, 629);

        // --- INPUT FIELDS ---
        JTextField nameInput = createPlaceholderField("Surname, First Name, MI.", 768, 340, 287, 40, 8);
        JTextField uniInput = createPlaceholderField("Name of University", 768, 420, 287, 40, 8);
        JTextField programInput = createPlaceholderField("Bachelor of", 768, 500, 287, 40, 8);
        JTextField schoolYearInput = createPlaceholderField("202x-202x", 768, 580, 287, 40, 8);
        JTextField gwaInput = createPlaceholderField("1.xx", 768, 653, 287, 40, 8);

        // --- REGISTER BUTTON ---
        JButton registerButton = new JButton("Register") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        registerButton.setBounds(768, 709, 287, 50);
        registerButton.setFont(new Font("Inter", Font.BOLD, 16));
        registerButton.setBackground(Color.LIGHT_GRAY);
        registerButton.setForeground(Color.WHITE);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);
        registerButton.setFocusPainted(false);
        registerButton.setEnabled(false);

        // --- VALIDATION LOGIC ---
        JTextField[] fields = {nameInput, uniInput, programInput, schoolYearInput, gwaInput};
        for (JTextField f : fields) {
            f.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) { check(); }
                public void removeUpdate(DocumentEvent e) { check(); }
                public void changedUpdate(DocumentEvent e) { check(); }
                private void check() {
                    boolean allFilled = true;
                    for (JTextField field : fields) {
                        if (field.getText().trim().isEmpty() || field.getForeground().equals(Color.GRAY)) {
                            allFilled = false;
                            break;
                        }
                    }
                    registerButton.setEnabled(allFilled);
                    registerButton.setBackground(allFilled ? customGreen : Color.LIGHT_GRAY);
                }
            });
        }

        // --- BUTTON ACTION: OPEN RECEIPT ---
        registerButton.addActionListener(e -> {
            try {
                double gwa = Double.parseDouble(gwaInput.getText());
                receiptPage(gwa);
            } catch (NumberFormatException ex) {
                receiptPage(5.0);
            }
        });

        frame.add(nameInput); frame.add(uniInput); frame.add(programInput);
        frame.add(schoolYearInput); frame.add(gwaInput); frame.add(registerButton);
        frame.add(nameLbl); frame.add(uniLbl); frame.add(progLbl);
        frame.add(yearLbl); frame.add(gwaLbl);
        frame.add(registerPhoto); frame.add(registerPanel);

        frame.revalidate();
        frame.repaint();
    }

    void receiptPage(double gwa) {
        frame.getContentPane().removeAll();
        addLogosToFrame();

        // --- MATH CALCULATIONS ---
        double tuitionFee = 24600.00;
        String[] fees = {"150.00", "500.00", "160.00", "300.00", "100.00", "100.00", "100.00", "90.00", "100.00", "300.00", "60.00", "60.00", "160.00"};
        double miscSum = 0;
        for (String fee : fees) miscSum += Double.parseDouble(fee);
        double assesSum = tuitionFee + miscSum;

        // --- GWA DISCOUNT LOGIC ---
        double discPercent;
        String grantName;
        if (gwa <= 1.00) { discPercent = 1.00; grantName = "SAT-SEA (FULL)"; }
        else if (gwa <= 1.25) { discPercent = 0.75; grantName = "SAT-SEA (HIGH)"; }
        else if (gwa <= 1.50) { discPercent = 0.50; grantName = "SAT-SEA (MID)"; }
        else if (gwa <= 1.75) { discPercent = 0.25; grantName = "SAT-SEA (PARTIAL)"; }
        else { discPercent = 0.00; grantName = "NONE"; }

        double discountAmtValue = assesSum * discPercent;
        double finalTotalDue = assesSum - discountAmtValue;

        // --- UI PANELS ---
        JPanel registerPanel = createRoundedPanel(341, 271, 759, 550, 24);
        registerPanel.setBackground(customWhite); 

        JPanel miscFeeBox = new JPanel();
        miscFeeBox.setBounds(368, 305, 343, 490);
        miscFeeBox.setBackground(customWhite);
        miscFeeBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel assesmentBox = new JPanel();
        assesmentBox.setBounds(731, 305, 342, 219);
        assesmentBox.setBackground(customWhite);
        assesmentBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel discountBox = new JPanel();
        discountBox.setBounds(731, 550, 342, 133);
        discountBox.setBackground(customWhite);
        discountBox.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); 

        // --- LINES ---
        JPanel miscLine = new JPanel();
        miscLine.setBounds(368, 340, 342, 1);
        miscLine.setBackground(Color.BLACK);
        frame.add(miscLine);

        JPanel assesLine = new JPanel();
        assesLine.setBounds(731, 340, 342, 1);
        assesLine.setBackground(Color.BLACK);
        frame.add(assesLine);

        JPanel discLine = new JPanel();
        discLine.setBounds(731, 582, 342, 1);
        discLine.setBackground(Color.BLACK);
        frame.add(discLine);

        JPanel innerDiscLine = new JPanel();
        innerDiscLine.setBounds(744, 631, 316, 1);
        innerDiscLine.setBackground(Color.BLACK);
        frame.add(innerDiscLine);

        JPanel dateLine = new JPanel();
        dateLine.setBounds(731, 714, 342, 1);
        dateLine.setBackground(Color.BLACK);
        frame.add(dateLine);

        // --- LABELS ---
        JLabel miscFee = new JLabel("Miscellaneous Fee");
        miscFee.setBounds(467, 314, 145, 20);
        miscFee.setFont(new Font("Inter", Font.BOLD, 16));
        miscFee.setForeground(Color.BLACK);
        frame.add(miscFee);

        JLabel assesDetails = new JLabel("Assessment Detail");
        assesDetails.setBounds(829, 314, 145, 20);
        assesDetails.setFont(new Font("Inter", Font.BOLD, 16));
        assesDetails.setForeground(Color.BLACK);
        frame.add(assesDetails);

        JLabel discDetails = new JLabel("Discount Details");
        discDetails.setBounds(838, 557, 145, 20);
        discDetails.setFont(new Font("Inter", Font.BOLD, 16));
        discDetails.setForeground(Color.BLACK);
        frame.add(discDetails);

        // --- FEES LIST ---
        String[] feesList = {"Registration Fee", "Athletic Fee", "Medical/Dental Fee", "Library", "Publication", "Journal", "Cultural", "Student Council Fund", "Science Lab Fee", "Computer Fee", "PE FEE", "CDTF", "IT Fee"};
        JLabel miscList = new JLabel("<html>" + String.join("<br>", feesList) + "</html>");
        miscList.setBounds(381, 353, 316, 260);
        miscList.setFont(new Font("Inter", Font.PLAIN, 14));
        miscList.setForeground(Color.BLACK);
        frame.add(miscList);

        JLabel miscFees = new JLabel("<html><div align='right'>" + String.join("<br>", fees) + "</div></html>");
        miscFees.setBounds(381, 353, 316, 260); 
        miscFees.setFont(new Font("Inter", Font.PLAIN, 14));
        miscFees.setHorizontalAlignment(SwingConstants.RIGHT);
        miscFees.setVerticalAlignment(SwingConstants.TOP);
        frame.add(miscFees);

        JLabel miscTotal = new JLabel("TOTAL");
        miscTotal.setBounds(381, 761, 46, 17); 
        miscTotal.setFont(new Font("Inter", Font.BOLD, 14));
        frame.add(miscTotal);

        JLabel miscTotalFee = new JLabel(String.format("%.2f", miscSum));
        miscTotalFee.setBounds(600, 761, 97, 17);
        miscTotalFee.setFont(new Font("Inter", Font.PLAIN, 14));
        miscTotalFee.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(miscTotalFee);

        // --- ASSESSMENT ---
        JLabel assesList = new JLabel("<html>Tuition Fee <br>Miscellaneous Fee");
        assesList.setBounds(745, 353, 316, 36); 
        assesList.setFont(new Font("Inter", Font.PLAIN, 14));
        frame.add(assesList);

        JLabel assesFee = new JLabel("<html><div align='right'>" + String.format("%.2f", tuitionFee) + "<br>" + String.format("%.2f", miscSum) + "</div></html>");
        assesFee.setBounds(745, 353, 316, 50); 
        assesFee.setFont(new Font("Inter", Font.PLAIN, 14));
        assesFee.setHorizontalAlignment(SwingConstants.RIGHT);
        assesFee.setVerticalAlignment(SwingConstants.TOP);
        frame.add(assesFee);

        JLabel assTotal = new JLabel("TOTAL");
        assTotal.setBounds(745, 489, 60, 17); 
        assTotal.setFont(new Font("Inter", Font.BOLD, 14));
        frame.add(assTotal);

        JLabel assesTotalFee = new JLabel(String.format("%.2f", assesSum));
        assesTotalFee.setBounds(960, 489, 100, 17); 
        assesTotalFee.setFont(new Font("Inter", Font.PLAIN, 14));
        assesTotalFee.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(assesTotalFee);

        // --- DISCOUNTS ---
        JLabel scholarshipGrant = new JLabel("Scholarship Grant");
        scholarshipGrant.setBounds(744, 592, 150, 20); 
        scholarshipGrant.setFont(new Font("Inter", Font.PLAIN, 14));
        frame.add(scholarshipGrant);

        JLabel scholarName = new JLabel(grantName);
        scholarName.setBounds(894, 592, 166, 20); 
        scholarName.setFont(new Font("Inter", Font.BOLD, 14));
        scholarName.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(scholarName);

        JLabel discountPercentage = new JLabel("Discount Percentage");
        discountPercentage.setBounds(744, 638, 150, 20);
        discountPercentage.setFont(new Font("Inter", Font.PLAIN, 14));
        frame.add(discountPercentage);

        JLabel discPercentLabel = new JLabel((int)(discPercent * 100) + "%");
        discPercentLabel.setBounds(894, 638, 166, 20);
        discPercentLabel.setFont(new Font("Inter", Font.BOLD, 14));
        discPercentLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(discPercentLabel);

        JLabel discountAmount = new JLabel("Discount Amount");
        discountAmount.setBounds(744, 655, 150, 20);
        discountAmount.setFont(new Font("Inter", Font.PLAIN, 14));
        frame.add(discountAmount);

        JLabel discountAmountFee = new JLabel(String.format("- %.2f", discountAmtValue));
        discountAmountFee.setBounds(894, 655, 166, 20);
        discountAmountFee.setFont(new Font("Inter", Font.BOLD, 14));
        discountAmountFee.setForeground(new Color(150, 0, 0)); 
        discountAmountFee.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(discountAmountFee);

        // --- DATE ---
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String formattedDate = today.format(formatter);

        JLabel actualDate = new JLabel(formattedDate);
        actualDate.setBounds(864, 695, 200, 20);
        actualDate.setFont(new Font("Inter", Font.BOLD, 14));
        actualDate.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(actualDate);

        JLabel dateToday = new JLabel("Date Today");
        dateToday.setBounds(870, 718, 194, 15);
        dateToday.setFont(new Font("Inter", Font.PLAIN, 12));
        dateToday.setHorizontalAlignment(SwingConstants.RIGHT);
        frame.add(dateToday);

        // --- PAYMENT BUTTON ---
        JButton paymentButton = new JButton("Pay PHP " + String.format("%.2f", finalTotalDue)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 12, 12));
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        paymentButton.setBounds(731, 745, 342, 50);
        paymentButton.setFont(new Font("Inter", Font.BOLD, 16));
        paymentButton.setBackground(customGreen);
        paymentButton.setForeground(Color.WHITE);
        paymentButton.setContentAreaFilled(false);
        paymentButton.setBorderPainted(false);
        paymentButton.setFocusPainted(false);

        // ADDED ACTION LISTENER HERE
        paymentButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(frame, "Payment Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            showRegistrationForm(); // Redirect back to registration form after payment
        });
        
        frame.add(paymentButton);

        // --- BACKGROUND PANELS ---
        frame.add(miscFeeBox);
        frame.add(assesmentBox);
        frame.add(discountBox);
        frame.add(registerPanel);

        frame.revalidate();
        frame.repaint();
    }

    private void addLogosToFrame() {
        worldName.setBounds(176, 74, 300, 20);
        worldName.setFont(new Font("Inter", Font.PLAIN, 15));
        worldName.setForeground(Color.WHITE);
        frame.add(worldName);
        logoLine.setBounds(176, 98, 384, 1);
        logoLine.setBackground(Color.WHITE);
        frame.add(logoLine);
        sat.setBounds(176, 105, 400, 18);
        sat.setFont(new Font("Inter", Font.PLAIN, 15));
        sat.setForeground(Color.WHITE);
        frame.add(sat);
        sea.setBounds(176, 128, 450, 29);
        sea.setFont(new Font("Inter", Font.BOLD, 24));
        sea.setForeground(Color.WHITE);
        frame.add(sea);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 15);
        label.setFont(new Font("Inter", Font.PLAIN, 12));
        label.setForeground(Color.BLACK);
        return label;
    }

    private JPanel createRoundedPanel(int x, int y, int w, int h, int radius) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
                g2.dispose();
            }
        };
        panel.setBounds(x, y, w, h);
        panel.setOpaque(false);
        return panel;
    }

    private JTextField createPlaceholderField(String hint, int x, int y, int w, int h, int radius) {
        JTextField field = new JTextField(hint) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
                super.paintComponent(g);
                g2.dispose();
            }
        };
        field.setBounds(x, y, w, h);
        field.setBackground(customGray);
        field.setForeground(Color.GRAY);
        field.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        field.setOpaque(false);
        field.setFont(new Font("Inter", Font.ITALIC, 12));

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(hint)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setFont(new Font("Inter", Font.PLAIN, 14));
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setFont(new Font("Inter", Font.ITALIC, 12));
                    field.setText(hint);
                }
            }
        });
        return field;
    }
}