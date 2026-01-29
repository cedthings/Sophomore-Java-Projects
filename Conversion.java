import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

// --- UI COMPONENTS ---
class ConvModeBtn extends JButton {
    private boolean isExpanded = false;

    public ConvModeBtn() {
        super("Convert");
        setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
        setForeground(Color.WHITE); setCursor(new Cursor(Cursor.HAND_CURSOR));

        addActionListener(e -> {
            isExpanded = !isExpanded;
            setSize(getWidth(), isExpanded ? 78 : 41);
            if (getParent() != null) { getParent().revalidate(); getParent().repaint(); }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isExpanded && e.getY() > 40) {
                    Window win = SwingUtilities.getWindowAncestor(ConvModeBtn.this);
                    if (win != null) win.dispose();
                    // Assuming Calculator class exists in your project
                    SwingUtilities.invokeLater(() -> { try { new Calculator(); } catch (Exception ex) {} });
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(52, 52, 52));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 36, 36);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.PLAIN, 16));
        g2.drawString("Convert " + (isExpanded ? "" : "            >"), 20, 26);
        if (isExpanded) {
            g2.setColor(new Color(94, 94, 94)); g2.fillRect(21, 39, 110, 1);
            g2.setColor(Color.WHITE); g2.drawString("Basic", 21, 65);
        }
        g2.dispose();
    }
}

class ConvCircleBtn extends JButton {
    public ConvCircleBtn(String label, Color bg, Color fg) {
        super(label); setBackground(bg); setForeground(fg);
        setContentAreaFilled(false); setFocusPainted(false); setBorderPainted(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int diameter = Math.min(getWidth(), getHeight()) - 4; 
        int x = (getWidth() - diameter) / 2;
        int y = (getHeight() - diameter) / 2;
        g2.setColor(getBackground());
        g2.fillOval(x, y, diameter, diameter);
        g2.setColor(isEnabled() ? getForeground() : Color.DARK_GRAY);
        g2.setFont(new Font("Arial", Font.PLAIN, 24));
        FontMetrics fm = g2.getFontMetrics();
        int textX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textY = (getHeight() + fm.getAscent()) / 2 - 4;
        g2.drawString(getText(), textX, textY);
        g2.dispose();
    }
}

public class Conversion {
    private final JFrame frame = new JFrame("Conversion");
    private final JLabel binVal = new JLabel("0"), octVal = new JLabel("0"), decVal = new JLabel("0"), hexVal = new JLabel("0");
    private String currentActiveBase = "DEC"; 
    private final JPanel buttonsPanel = new JPanel(new GridLayout(5, 3, 15, 15));
    private boolean isNumericMode = true;

    private final List<String> numKeys = Arrays.asList("Del", "AC", "(A)", "7", "8", "9", "4", "5", "6", "1", "2", "3", ".", "0", ".");
    private final List<String> letKeys = Arrays.asList("Del", "AC", "(1)", "A", "B", "C", "D", "E", "F", "BIN", "OCT", "DEC", ".", "HEX", ".");

    public Conversion() {
        frame.setSize(380, 800); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(Color.BLACK); 
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(null);
        topPanel.setBackground(Color.BLACK);
        topPanel.setPreferredSize(new Dimension(380, 320));

        ConvModeBtn modeBtn = new ConvModeBtn(); 
        modeBtn.setBounds(20, 40, 154, 41);
        topPanel.add(modeBtn);

        JPanel gridPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        gridPanel.setBackground(Color.BLACK);
        gridPanel.setBounds(20, 100, 340, 180);

        addBaseToGrid(gridPanel, binVal, "Bin");
        addBaseToGrid(gridPanel, octVal, "Oct");
        addBaseToGrid(gridPanel, decVal, "Dec");
        addBaseToGrid(gridPanel, hexVal, "Hex");
        
        topPanel.add(gridPanel);
        renderButtons();
        updateLabelColors();

        frame.add(topPanel, BorderLayout.NORTH); 
        frame.add(buttonsPanel, BorderLayout.CENTER);
        buttonsPanel.setBackground(Color.BLACK); 
        buttonsPanel.setBorder(new EmptyBorder(0, 20, 30, 20));
        
        frame.setLocationRelativeTo(null); 
        frame.setVisible(true);
    }

    private void addBaseToGrid(JPanel parent, JLabel valLabel, String tag) {
        JPanel container = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 10));
        container.setBackground(Color.BLACK);
        valLabel.setFont(new Font("Inter", Font.BOLD, 30));
        JLabel tagLabel = new JLabel(tag);
        tagLabel.setForeground(Color.DARK_GRAY);
        tagLabel.setFont(new Font("Inter", Font.PLAIN, 14));
        container.add(valLabel);
        container.add(tagLabel);
        parent.add(container);
    }

    private void updateLabelColors() {
        binVal.setForeground(currentActiveBase.equals("BIN") ? Color.WHITE : Color.GRAY);
        octVal.setForeground(currentActiveBase.equals("OCT") ? Color.WHITE : Color.GRAY);
        decVal.setForeground(currentActiveBase.equals("DEC") ? Color.WHITE : Color.GRAY);
        hexVal.setForeground(currentActiveBase.equals("HEX") ? Color.WHITE : Color.GRAY);
    }

    // --- REAL-TIME CONVERSION LOGIC ---
    private void performConversion(String input) {
        if (input.equals("0") || input.isEmpty()) {
            binVal.setText("0"); octVal.setText("0"); decVal.setText("0"); hexVal.setText("0");
            return;
        }
        try {
            int radix = getRadix(currentActiveBase);
            long decimalValue = Long.parseLong(input, radix);

            if (!currentActiveBase.equals("BIN")) binVal.setText(Long.toBinaryString(decimalValue));
            if (!currentActiveBase.equals("OCT")) octVal.setText(Long.toOctalString(decimalValue));
            if (!currentActiveBase.equals("DEC")) decVal.setText(String.valueOf(decimalValue));
            if (!currentActiveBase.equals("HEX")) hexVal.setText(Long.toHexString(decimalValue).toUpperCase());
        } catch (NumberFormatException e) {
            // Handle overflow or invalid input
        }
    }

    private int getRadix(String base) {
        switch (base) {
            case "BIN": return 2;
            case "OCT": return 8;
            case "HEX": return 16;
            default: return 10;
        }
    }

    private void renderButtons() {
        buttonsPanel.removeAll();
        List<String> keys = isNumericMode ? numKeys : letKeys;
        for (String val : keys) {
            Color bg = new Color(52, 52, 52), fg = Color.WHITE;
            if (val.equals("Del") || val.equals("AC")) { bg = new Color(94, 94, 94); fg = Color.BLACK; }
            else if (val.startsWith("(")) bg = new Color(255, 146, 0);

            ConvCircleBtn btn = new ConvCircleBtn(val, bg, fg);
            boolean isBaseKey = Arrays.asList("BIN","OCT","DEC","HEX").contains(val);
            if (!isKeyValidForBase(val) && !val.equals("Del") && !val.equals("AC") && !val.startsWith("(") && !isBaseKey) {
                btn.setEnabled(false);
            }

            btn.addActionListener(e -> handleInput(val));
            buttonsPanel.add(btn);
        }
        buttonsPanel.revalidate(); buttonsPanel.repaint();
    }

    private boolean isKeyValidForBase(String key) {
        if (key.equals(".") || key.trim().isEmpty()) return false;
        switch (currentActiveBase) {
            case "BIN": return "01".contains(key);
            case "OCT": return "01234567".contains(key);
            case "DEC": return "0123456789".contains(key);
            case "HEX": return "0123456789ABCDEF".contains(key);
            default: return true;
        }
    }

    private void handleInput(String val) {
        List<String> bases = Arrays.asList("BIN", "OCT", "DEC", "HEX");
        if (val.equals("(A)") || val.equals("(1)")) {
            isNumericMode = !isNumericMode; renderButtons();
        } else if (val.equals("AC")) {
            performConversion("0");
        } else if (val.equals("Del")) {
            JLabel active = getActiveLabel();
            String txt = active.getText();
            String newTxt = (txt.length() > 1) ? txt.substring(0, txt.length() - 1) : "0";
            active.setText(newTxt);
            performConversion(newTxt);
        } else if (bases.contains(val)) {
            currentActiveBase = val;
            updateLabelColors();
            performConversion("0"); // Auto-AC
            renderButtons();
        } else {
            JLabel active = getActiveLabel();
            String current = active.getText();
            String newTxt = current.equals("0") ? val : current + val;
            active.setText(newTxt);
            performConversion(newTxt);
        }
    }

    private JLabel getActiveLabel() {
        switch (currentActiveBase) {
            case "BIN": return binVal;
            case "OCT": return octVal;
            case "HEX": return hexVal;
            default: return decVal;
        }
    }
}