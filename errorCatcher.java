import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;

public class errorCatcher {
    int borderWidth = 1280;
    int borderHeight = 832;

    Color customViolet = new Color(46, 45, 77);
    Color customGhostWhite = new Color(248, 244, 249);
    //Color customBlue = new Color(27, 77, 195);
    //Color customRed = new Color(236, 103, 103);
    
    JFrame frame = new JFrame("404 - Something went Wrong");

    public errorCatcher() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customViolet);
        frame.setLayout(null);

        String[][] messages = {
            {"Hard at work!", "<html><body style='text-align: center;'>We’re currently polishing this page to make sure<br>everything is perfect. Check back soon!</body></html>"},
            {"Something big is coming.", "<html><body style='text-align: center;'>You caught us mid-build! We’re putting the finishing<br>touches on this section.</body></html>"},
            {"Pardon our dust.", "<html><body style='text-align: center;'>We’re currently polishing this page to make sure<br>everything is perfect. Check back soon!</body></html>"}
        };

        Random rand = new Random();
        int index = rand.nextInt(messages.length);

        JLabel headerLabel = new JLabel(messages[index][0]);
        headerLabel.setBounds(363, 311, 553, 58);
        headerLabel.setFont(new Font("Inter", Font.BOLD, index == 1 ? 40 : 48)); 
        headerLabel.setForeground(customViolet);
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(headerLabel);

        JLabel bodyLabel = new JLabel(messages[index][1]);
        bodyLabel.setBounds(389, 381, 502, 60); 
        bodyLabel.setFont(new Font("Inter", Font.PLAIN, 20));
        bodyLabel.setForeground(customViolet);
        bodyLabel.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(bodyLabel);

        // --- Button and Design ---
        JButton returnButton = createRoundedButton("Back to Homepage", 32);
        returnButton.setBounds(465, 470, 349, 63);
        returnButton.setBackground(customViolet);
        returnButton.setForeground(customGhostWhite);
        returnButton.setFont(new Font("Inter", Font.PLAIN, 24));
        
        returnButton.addActionListener(e -> {
            System.out.println("Returning to home...");
            new homePage();
            frame.dispose();
        });
        frame.add(returnButton);

        // Stars & Background
        addDesignElements();

        JPanel errorPanel = createRoundedPanel(301, 250, 678, 332, 30, null, 0);
        errorPanel.setBackground(customGhostWhite);
        frame.add(errorPanel);

        frame.setVisible(true);
    }

    public void paymentDeclined() {
        // 1. Clear the default 404 text from the constructor
        frame.getContentPane().removeAll();
        
        // 2. Re-add the background design elements
        addDesignElements();

        JLabel paymentDeclined = new JLabel("Payment Declined");
        paymentDeclined.setBounds(363, 311, 553, 58);
        paymentDeclined.setFont(new Font("Inter", Font.BOLD, 48));
        paymentDeclined.setForeground(customViolet);
        paymentDeclined.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(paymentDeclined);

        JLabel paymentDeclinedBody = new JLabel("<html><body style='text-align: center;'>"
                                            + "Your bank was unable to authorize this transaction<br>"
                                            + "due to insufficient funds."
                                            + "</body></html>");
        paymentDeclinedBody.setBounds(395, 381, 489, 48);
        paymentDeclinedBody.setFont(new Font("Inter", Font.PLAIN, 20));
        paymentDeclinedBody.setForeground(customViolet);
        paymentDeclinedBody.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(paymentDeclinedBody); // <-- Added missing frame.add()

         //Button and Background
        JButton retryButton = createRoundedButton("Retry Payment", 32);
        retryButton.setBounds(465, 457, 349, 63);
        retryButton.setBackground(customViolet);
        retryButton.setForeground(customGhostWhite);
        retryButton.setFont(new Font("Inter", Font.PLAIN, 24));
        
        // <-- UPDATED: Make sure Retry goes back to the home page!
        retryButton.addActionListener(e -> {
            frame.dispose(); 
            new homePage(); 
        });
        frame.add(retryButton);

        JPanel errorPanel = createRoundedPanel(301, 250, 678, 332, 30, null, 0);
        errorPanel.setBackground(customGhostWhite);
        frame.add(errorPanel);

        // 3. Refresh the frame to show the clean Payment Declined UI
        frame.revalidate();
        frame.repaint();
    }

    private void addDesignElements() {
        JPanel circle1 = createRoundedPanel(129, 64, 27, 27, 26, customGhostWhite, 3);
        circle1.setBackground(customViolet);
        frame.add(circle1);

        JPanel circle2 = createRoundedPanel(1206, 549, 27, 27, 26, null, 0);
        circle2.setBackground(customGhostWhite);
        frame.add(circle2);
        
        JPanel line1 = new JPanel();
        line1.setBackground(customGhostWhite);
        line1.setBounds(350, 747, 3, 30);
        frame.add(line1);

        JPanel line2 = new JPanel();
        line2.setBackground(customGhostWhite);
        line2.setBounds(336, 760, 30, 3);
        frame.add(line2);
    }

    private JButton createRoundedButton(String text, int radius) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) g2.setColor(getBackground().darker());
                else if (getModel().isRollover()) g2.setColor(getBackground().brighter());
                else g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius));
                g2.dispose();
                super.paintComponent(g);
            }
        };
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new errorCatcher());
    }
}
