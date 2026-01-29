import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

public class loadingScreen {
    
    int borderWidth = 380;
    int borderHeight = 800;

    Color customBlack = new Color(0, 0, 0);
    Color customGray = new Color(200, 200, 200); 
    Color customOrange = new Color(255, 146, 0); 
    Color customWhite = new Color(255, 255, 255);

    JFrame frame = new JFrame("Loading...");
    JProgressBar progressBar = new JProgressBar(0, 100);
    JLabel tipLabel = new JLabel();
    JLabel waterMark = new JLabel();

    loadingScreen() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customBlack);
        
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 40, 10, 40); 

        // 1. Title Label
        JLabel titleLabel = new JLabel("CALCULATOR");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        titleLabel.setForeground(customWhite);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        frame.add(titleLabel, gbc);

        // 2. Styled Oblong Progress Bar
        progressBar.setPreferredSize(new Dimension(300, 20)); // Adjusted height for sleek look
        progressBar.setStringPainted(false);
        progressBar.setOpaque(false); // Important for rounded corners to show background
        progressBar.setBorderPainted(false);
        
        progressBar.setUI(new BasicProgressBarUI() {
            @Override
            protected void paintDeterminate(Graphics g, JComponent c) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = progressBar.getWidth();
                int height = progressBar.getHeight();
                int arc = height; // Full rounding for "oblong" pill shape

                // Paint the track (Background)
                g2d.setColor(customGray);
                g2d.fill(new RoundRectangle2D.Float(0, 0, width, height, arc, arc));

                // Paint the progress (Foreground)
                double percent = progressBar.getPercentComplete();
                if (percent > 0) {
                    g2d.setColor(customOrange);
                    // Standard progress width
                    int progressWidth = (int) (width * percent);
                    g2d.fill(new RoundRectangle2D.Float(0, 0, progressWidth, height, arc, arc));
                }
            }

            @Override
            protected void paintIndeterminate(Graphics g, JComponent c) {
                // Fallback for indeterminate mode if needed
                super.paintIndeterminate(g, c);
            }
        });

        gbc.gridy = 1;
        frame.add(progressBar, gbc);

        // 3. Tip Label
        tipLabel.setText("<html><center>Tips: You can solve basic arithmetic problem in calculator, I bet you didn't know that.</center></html>");
        tipLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tipLabel.setForeground(customWhite);
        gbc.gridy = 2;
        frame.add(tipLabel, gbc);

        // 4. Watermark Label
        waterMark.setText("<html><br><br><br><br><br><br><center>Developed by Cedrick Tijay Crispino - 2026</center></html>");
        waterMark.setFont(new Font("SansSerif", Font.ITALIC, 10));
        waterMark.setForeground(customGray);
        gbc.gridy = 3;
        frame.add(waterMark, gbc);

        frame.setVisible(true);
        startLoading();
    }

    private void startLoading() {
        Timer timer = new Timer(5, e -> {
            int val = progressBar.getValue();
            if (val < 100) {
                progressBar.setValue(val + 1);
            } else {
                ((Timer)e.getSource()).stop();
                frame.dispose(); 
                new Calculator(); 
            }
        });
        timer.start();
    } 
}