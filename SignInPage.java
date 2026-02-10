import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class SignInPage {
    // Dimensions and Colors
    int borderWidth = 1280;
    int borderHeight = 832;
    Color customViolet = new Color(46, 45, 77);
    Color customGhostWhite = new Color(248, 244, 249);
    Color customBlue = new Color(27, 77, 195);
    Color customRed = new Color(236, 103, 103);
    Color transparentViolet = new Color(46, 45, 77, 120);

    // Account Data Persistence
    String registeredUser = "admin";
    String registeredPass = "admin";
    
    // Temp storage for Sign-up flow
    String tempName, tempEmail, tempPass;

    JFrame frame = new JFrame("Account Set-up");

    public SignInPage() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customGhostWhite);
        frame.setLayout(null);

        showSignIn(); 
        frame.setVisible(true);
    }

    // --- VIEW: SIGN IN ---
    public void showSignIn() {
        prepareView();
        addLeftPanel("Machine Problem 3", "<html>You can sign in to access with your<br>existing account.</html>");

        JLabel header = createHeader("Sign in", 843, 211);
        
        JTextField userField = createPlaceholderField("Username or email", 843, 295, 349, 63, 64, false);
        JPasswordField passField = (JPasswordField) createPlaceholderField("Password", 843, 370, 349, 63, 64, true);

        // Error Label (Initially Hidden)
        JLabel errorLabel = new JLabel("Wrong username or password!");
        errorLabel.setBounds(843, 439, 250, 15);
        errorLabel.setForeground(customRed);
        errorLabel.setFont(new Font("Inter", Font.PLAIN, 12));
        errorLabel.setVisible(false);
        frame.add(errorLabel);

        JLabel forgotPW = createLinkLabel("Forgot Password?", 1056, 448, 136, customBlue);
        forgotPW.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showForgotPW(); }
        });

        JButton signInBtn = createStyledButton("Sign in", 504);
        setupValidation(new JTextField[]{userField, passField}, signInBtn);

        // Logic for Login
        signInBtn.addActionListener(e -> {
            String inputUser = userField.getText();
            String inputPass = new String(passField.getPassword());

            if (inputUser.equals(registeredUser) && inputPass.equals(registeredPass)) {
                new loadingScreen();
                //JOptionPane.showMessageDialog(frame, "Login Successful! Welcome " + inputUser);
            } else {
                errorLabel.setVisible(true);
                userField.putClientProperty("error", true);
                passField.putClientProperty("error", true);
                frame.repaint();
            }
        });

        addBottomLinks("   New Here?", "Create an Account", new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showSignUp(); }
        });

        frame.add(header); frame.add(userField); frame.add(passField); 
        frame.add(forgotPW); frame.add(signInBtn);
        finishView();
    }

    // --- VIEW: SIGN UP ---
    public void showSignUp() {
        prepareView();
        addLeftPanel("Welcome to Fern", "a desktop banking app. Somehow.");

        JLabel header = createHeader("Sign up", 843, 211);

        JTextField nameField = createPlaceholderField("Name", 843, 289, 349, 63, 64, false);
        JTextField emailField = createPlaceholderField("Email address", 843, 364, 349, 63, 64, false);
        JPasswordField passField = (JPasswordField) createPlaceholderField("Password", 843, 439, 349, 63, 64, true);

        JButton signUpBtn = createStyledButton("Proceed", 519);
        setupValidation(new JTextField[]{nameField, emailField, passField}, signUpBtn);

        signUpBtn.addActionListener(e -> {
            tempName = nameField.getText();
            tempEmail = emailField.getText();
            tempPass = new String(passField.getPassword());
            bankDetails();
        });

        addBottomLinks("Already have an account?", "Log in", new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showSignIn(); }
        });

        frame.add(header); frame.add(nameField); frame.add(emailField); 
        frame.add(passField); frame.add(signUpBtn);
        finishView();
    }
    
    // --- VIEW: BANK DETAILS ---
    public void bankDetails() {
        prepareView();
        addLeftPanel("Let’s see", "<html>How many millions you have in your<br>bank account.</html>");

        JLabel header = createHeader("Bank Details", 843, 211);

        JTextField cardNum = createPlaceholderField("Card Number", 843, 289, 349, 63, 64, false);
        setNumericLimit(cardNum, 16);

        JTextField cardName = createPlaceholderField("Card Holder Name", 843, 364, 349, 63, 64, false);

        JTextField CVV = createPlaceholderField("CVV", 843, 439, 131, 63, 64, false);
        setNumericLimit(CVV, 3);

        JTextField expDate = createPlaceholderField("MM/YY", 986, 439, 206, 63, 64, false);
        setNumericLimit(expDate, 4);

        JButton proceedBtn = createStyledButton("Proceed", 530);
        setupValidation(new JTextField[]{cardNum, cardName, CVV, expDate}, proceedBtn);

        proceedBtn.addActionListener(e -> {
            // Commit registration
            registeredUser = tempEmail;
            registeredPass = tempPass;
            JOptionPane.showMessageDialog(frame, "Account created successfully for " + tempName);
            showSignIn();
        });

        addBottomLinks("Already have an account?", "Log in", new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showSignIn(); }
        });

        frame.add(header); frame.add(cardNum); frame.add(cardName); 
        frame.add(CVV); frame.add(expDate); frame.add(proceedBtn);

        finishView();
    }

    // --- VIEW: FORGOT PASSWORD ---
    public void showForgotPW() {
        prepareView();
        addLeftPanel("How could you?", "forgot that quickly..");

        JLabel header = createHeader("Forgot Password", 843, 211);
        JTextField userField = createPlaceholderField("Username", 843, 295, 349, 63, 64, false);
        JTextField emailField = createPlaceholderField("Email address", 843, 370, 349, 63, 64, false);

        JButton resetBtn = createStyledButton("Reset Password", 450);
        setupValidation(new JTextField[]{userField, emailField}, resetBtn);

        resetBtn.addActionListener(e -> changePW());

        addBottomLinks("Already have an account?", "Log in", new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showSignIn(); }
        });

        frame.add(header); frame.add(userField); frame.add(emailField); frame.add(resetBtn);
        finishView();
    }

    public void changePW() {
        prepareView();
        addLeftPanel("I'm begging you.", "to write it down. Somewhere.");

        JLabel header = createHeader("Change Password", 843, 211);
        JPasswordField newPass = (JPasswordField) createPlaceholderField("New password", 843, 295, 349, 63, 64, true);
        JPasswordField reType = (JPasswordField) createPlaceholderField("Re-type your password", 843, 370, 349, 63, 64, true);

        JButton changeBTN = createStyledButton("Change Password", 450);
        setupMatchValidation(newPass, reType, changeBTN);
        
        changeBTN.addActionListener(e -> {
            registeredPass = new String(newPass.getPassword());
            JOptionPane.showMessageDialog(frame, "Password Changed Successfully!");
            showSignIn();
        });

        addBottomLinks("Already have an account?", "Log in", new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { showSignIn(); }
        });

        frame.add(header); frame.add(newPass); frame.add(reType); frame.add(changeBTN);
        finishView();
    }

    // --- LOGIC: VALIDATION & INPUT ---
    private void setupValidation(JTextField[] fields, JButton btn) {
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void changedUpdate(DocumentEvent e) { check(); }
            private void check() {
                boolean allFilled = true;
                for (JTextField f : fields) {
                    String txt = f.getText().trim();
                    if (txt.isEmpty() || f.getForeground().equals(transparentViolet)) {
                        allFilled = false;
                        break;
                    }
                }
                btn.setEnabled(allFilled);
                btn.setBackground(allFilled ? customViolet : Color.LIGHT_GRAY);
            }
        };
        for (JTextField f : fields) f.getDocument().addDocumentListener(dl);
    }

    private void setupMatchValidation(JPasswordField p1, JPasswordField p2, JButton btn) {
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { check(); }
            public void removeUpdate(DocumentEvent e) { check(); }
            public void changedUpdate(DocumentEvent e) { check(); }
            private void check() {
                String pass1 = new String(p1.getPassword());
                String pass2 = new String(p2.getPassword());
                boolean isNotEmpty = !pass1.isEmpty() && !p1.getForeground().equals(transparentViolet);
                boolean matches = pass1.equals(pass2);
                btn.setEnabled(isNotEmpty && matches);
                btn.setBackground((isNotEmpty && matches) ? customViolet : Color.LIGHT_GRAY);
            }
        };
        p1.getDocument().addDocumentListener(dl);
        p2.getDocument().addDocumentListener(dl);
    }

    private void setNumericLimit(JTextField field, int limit) {
        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) || field.getText().length() >= limit) e.consume();
            }
        });
    }

    // --- COMPONENT FACTORY ---
    private JTextField createPlaceholderField(String hint, int x, int y, int w, int h, int radius, boolean isPassword) {
        JTextField field = isPassword ? new JPasswordField(hint) : new JTextField(hint);
        if (isPassword) ((JPasswordField)field).setEchoChar((char)0); 

        field.setBounds(x, y, w, h);
        field.setBackground(new Color(0,0,0,0));
        field.setForeground(transparentViolet);
        field.setFont(new Font("Inter", Font.ITALIC, 20));
        field.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));
        field.setOpaque(false);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getForeground().equals(transparentViolet)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                    field.setFont(new Font("Inter", Font.PLAIN, 20));
                    if (isPassword) ((JPasswordField)field).setEchoChar('\u2022');
                    field.putClientProperty("error", false); // Reset error state on type
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(hint);
                    field.setForeground(transparentViolet);
                    field.setFont(new Font("Inter", Font.ITALIC, 20));
                    if (isPassword) ((JPasswordField)field).setEchoChar((char)0);
                }
            }
        });

        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                Boolean hasError = (Boolean) field.getClientProperty("error");
                g2.setColor(hasError != null && hasError ? customRed : transparentViolet);
                
                g2.draw(new RoundRectangle2D.Float(0, 0, getWidth()-1, getHeight()-1, radius, radius));
                g2.dispose();
            }
        };
        background.setBounds(x, y, w, h);
        background.setOpaque(false);
        frame.add(background);

        return field;
    }

    private JButton createStyledButton(String text, int y) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 64, 64));
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int tx = (getWidth() - fm.stringWidth(getText())) / 2;
                int ty = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), tx, ty);
                g2.dispose();
            }
        };
        btn.setBounds(843, y, 349, 63);
        btn.setFont(new Font("Inter", Font.BOLD, 24));
        btn.setBackground(Color.LIGHT_GRAY);
        btn.setForeground(Color.WHITE);
        btn.setEnabled(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        return btn;
    }

    private void prepareView() {
        frame.getContentPane().removeAll();
    }

    private void finishView() {
        addDesignElements();
        JPanel leftPanel = new JPanel();
        leftPanel.setBounds(0, 0, 759, 832);
        leftPanel.setBackground(customViolet);
        leftPanel.setLayout(null);
        frame.add(leftPanel);
        frame.revalidate(); frame.repaint();
        leftPanel.requestFocusInWindow();
    }

    private void addLeftPanel(String title, String subtitle) {
        JLabel t = new JLabel(title);
        t.setBounds(125, 329, 600, 58);
        t.setFont(new Font("Inter", Font.BOLD, 48));
        t.setForeground(customGhostWhite);
        frame.add(t);

        JLabel s = new JLabel(subtitle);
        s.setBounds(125, 400, 600, 64);
        s.setFont(new Font("Inter", Font.PLAIN, 24));
        s.setForeground(customGhostWhite);
        frame.add(s);
    }

    private JLabel createHeader(String text, int x, int y) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, 600, 58);
        l.setFont(new Font("Inter", Font.BOLD, 40));
        l.setForeground(Color.BLACK);
        return l;
    }

    private JLabel createLinkLabel(String text, int x, int y, int w, Color color) {
        JLabel l = new JLabel(text);
        l.setBounds(x, y, w, 24);
        l.setFont(new Font("Inter", Font.PLAIN, 16));
        l.setForeground(color);
        l.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return l;
    }

    private void addBottomLinks(String text, String linkText, MouseAdapter adapter) {
        JLabel l1 = new JLabel(text);
        l1.setBounds(906, 598, 229, 24);
        l1.setFont(new Font("Inter", Font.PLAIN, 16));
        frame.add(l1);

        JLabel l2 = createLinkLabel(linkText, 900, 598, 229, customBlue);
        l2.setHorizontalAlignment(SwingConstants.RIGHT);
        l2.addMouseListener(adapter);
        frame.add(l2);
    }

    private void addDesignElements() {
        frame.add(createRoundedCircle(485, 160, 27));
        frame.add(createRoundedCircle(82, 644, 27));
        addCross(220, 131);
        addCross(349, 591);
    }

    private void addCross(int x, int y) {
        JPanel h = new JPanel(); h.setBackground(customGhostWhite); h.setBounds(x, y, 30, 3);
        JPanel v = new JPanel(); v.setBackground(customGhostWhite); v.setBounds(x + 14, y - 13, 3, 30);
        frame.add(h); frame.add(v);
    }

    private JPanel createRoundedCircle(int x, int y, int size) {
        JPanel p = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(customGhostWhite);
                g2.setStroke(new BasicStroke(3));
                g2.drawOval(1, 1, getWidth()-3, getHeight()-3);
                g2.dispose();
            }
        };
        p.setBounds(x, y, size, size);
        p.setOpaque(false);
        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(SignInPage::new);
    }
}