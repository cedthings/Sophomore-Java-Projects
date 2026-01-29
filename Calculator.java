import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

class ModeButton extends JButton {
    private final int radius = 18;
    private final Color bgColor = new Color(52, 52, 52); 
    private final Color lineOuterColor = new Color(94, 94, 94); 
    private boolean isExpanded = false;

    public ModeButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setForeground(Color.LIGHT_GRAY);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        this.addActionListener(e -> {
            isExpanded = !isExpanded;
            if (isExpanded) {
                this.setBounds(this.getX(), this.getY(), this.getWidth(), 78);
            } else {
                this.setBounds(this.getX(), this.getY(), this.getWidth(), 41);
            }
            this.revalidate();
            this.repaint();
        });

        // --- NEW CLICK DETECTION FOR "CONVERT" ---
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // If expanded and clicking the lower area where "Convert" is drawn
                if (isExpanded && e.getY() > 40) {
                    openConversion();
                }
            }
        });
    }

    private void openConversion() {
        // Find the current frame and close it
        Window win = SwingUtilities.getWindowAncestor(this);
        if (win != null) win.dispose();
        
        // Open the Conversion frame
        SwingUtilities.invokeLater(() -> {
            try {
                new Conversion(); 
            } catch (Exception ex) {
                System.out.println("Conversion class not found!");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius * 2, radius * 2);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Basic", 20, 26);
        
        g2.setFont(new Font("Arial", Font.PLAIN, 18)); 
        if (!isExpanded) {
            g2.drawString(">", 130, 26);
        }

        if (isExpanded) {
            g2.setColor(lineOuterColor);
            g2.fillRect(21, 39, 110, 1);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Inter", Font.PLAIN, 16));
            g2.drawString("Convert", 21, 65); 
        }
        g2.dispose();
    }
}

class CircleButton extends JButton {
    public CircleButton(String label) {
        super(label);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) g2.setColor(getBackground().darker());
        else g2.setColor(getBackground());
        
        int size = Math.min(getWidth(), getHeight());
        int xOffset = (getWidth() - size) / 2;
        int yOffset = (getHeight() - size) / 2;
        g2.fillOval(xOffset, yOffset, size - 1, size - 1);

        g2.setColor(getForeground());
        g2.setFont(getFont());
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() + fm.getAscent()) / 2 - 5;

        g2.drawString(getText(), textX, textY);
        g2.dispose();
    }
}

public class Calculator {
    int borderWidth = 380;
    int borderHeight = 800;

    Color customLightGray = new Color(94, 94, 94);
    Color customDarkGray = new Color(52, 52, 52);
    Color customWhite = new Color(245, 245, 245);
    Color customOrange = new Color(255, 146, 0);
    Color customBlack = new Color(0, 0, 0);

    // "×" hold down the Alt key and type 0215 on the numeric keypad 
    // "÷" hold down the Alt key and type 0247 on the numeric keypad
    String[] buttonValues = {
        "Del", "AC", "%", "÷", 
        "7", "8", "9", "×",
        "4", "5", "6", "-", 
        "1", "2", "3", "+",
        "+/-", "0", ".", "="
    };
    
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"Del", "AC", "%"};

    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel("0");
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;
    boolean isOperatorClicked = false;  

    Calculator() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customBlack);
        frame.setLayout(new BorderLayout());

        // --- TOP SECTION (Absolute Positioning) ---
        displayPanel.setLayout(null); // Use null layout for precise coordinates
        displayPanel.setBackground(customBlack);
        displayPanel.setPreferredSize(new Dimension(borderWidth, 320));

        // modeBasic button: W=154, H=41, Y=60, X=20 (to match left padding)
        ModeButton modeBasic = new ModeButton("Basic >");
        modeBasic.setBounds(20, 60, 154, 41);
        
        // Display Label positioned below the mode button but near the grid
        displayLabel.setForeground(customWhite);
        displayLabel.setFont(new Font("Inter", Font.BOLD, 50));
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setBounds(20, 200, 340, 100); 

        displayPanel.add(modeBasic);
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        // --- GRID SECTION ---
        buttonsPanel.setLayout(new GridLayout(5, 4, 15, 15));
        buttonsPanel.setBackground(customBlack);
        buttonsPanel.setBorder(new EmptyBorder(0, 20, 30, 20));
        
        for (String val : buttonValues) {
            CircleButton button = new CircleButton(val);
            button.setFont(new Font("Arial", Font.PLAIN, 28));
            button.setFocusable(false);
            
            if (Arrays.asList(topSymbols).contains(val)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(val)) {
                button.setBackground(customOrange);
                button.setForeground(customWhite);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(customWhite);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                   JButton button   = (JButton) e.getSource();
                   String buttonValue = button.getText();
                   if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                       if (buttonValue.equals("=")) {
                            if (operator != null){
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);
                                
                                if (operator.equals("+")) displayLabel.setText(removeZeroDecimal(numA + numB));
                                else if (operator.equals("-")) displayLabel.setText(removeZeroDecimal(numA - numB));
                                else if (operator.equals("×")) displayLabel.setText(removeZeroDecimal(numA * numB));
                                else if (operator.equals("÷")) {
                                    if (numB == 0) displayLabel.setText("Error");
                                    else displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                                // Reset state after calculation
                                A = displayLabel.getText(); 
                                operator = null;
                            }
                        }
                            else { 
                            // This handles +, -, ×, ÷
                            A = displayLabel.getText();
                            operator = buttonValue;
                            isOperatorClicked = true; // Signal that we are ready for the second number
                            }
                    } 
                   else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue.equals("AC")) {
                            clearAll();
                          }
                        else if (buttonValue == "%") {
                            try {
                                double currentValue = Double.parseDouble(displayLabel.getText());
                                currentValue /= 100;
                                displayLabel.setText(removeZeroDecimal(currentValue));

                            } catch (NumberFormatException ex) {
                                displayLabel.setText("Error");
                            }
                        }
                        else if (buttonValue == "Del") {
                            String currentText = displayLabel.getText();

                            if (currentText.equals("Error")) {
                                displayLabel.setText("0");
                            }
                            else if (currentText.length() > 1) {
                                displayLabel.setText(currentText.substring(0, currentText.length() - 1));
                            } 
                            else {
                                displayLabel.setText("0");
                            }
                        }
                   }
                   else {
                       if (buttonValue.equals(".")) {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                       }
                       else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0" || isOperatorClicked) {
                                displayLabel.setText(buttonValue);
                                isOperatorClicked = false;
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue); 
                            }    
                       }
                       else if (buttonValue == "+/-") {

                            try {
                                double currentValue = Double.parseDouble(displayLabel.getText());
                                currentValue *= -1;
                                displayLabel.setText(removeZeroDecimal(currentValue));
                            } catch (NumberFormatException ex) {
                                displayLabel.setText("Error");
                            }
                        }
                    }
                }
            });
        }
        
        frame.add(buttonsPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
        displayLabel.setText("0");
    }   

    String removeZeroDecimal(double currentValue) {
        if (currentValue % 1 == 0) {
            return Integer.toString((int) currentValue);
        } else {
            return String.valueOf(currentValue);
        }
    }
}