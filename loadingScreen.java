import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class loadingScreen {
    int borderWidth = 1280;
    int borderHeight = 832;
    
    Color customViolet = new Color(46, 45, 77);
    Color customGhostWhite = new Color(248, 244, 249);

    JFrame frame = new JFrame("loading..");
    JPanel filledLoadingBar; // Moved to class level so startLoading can see it
    int maxWidth = 632;      // The full width of the empty bar

    public loadingScreen(){
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customGhostWhite);
        frame.setLayout(null);

        // Fern Bank Logo
        JLabel fern = new JLabel("FERN");
        fern.setBounds(429, 242, 422, 124);
        fern.setFont(new Font("Inter", Font.BOLD, 128)); 
        fern.setForeground(customViolet);
        fern.setHorizontalAlignment(SwingConstants.CENTER);
        fern.setVerticalAlignment(SwingConstants.TOP);
        frame.add(fern); 

        JLabel bank = new JLabel("Bank");
        bank.setBounds(522, 366, 236, 100);
        bank.setFont(new Font("Inter", Font.BOLD, 96)); 
        bank.setForeground(customGhostWhite);
        bank.setHorizontalAlignment(SwingConstants.CENTER);
        frame.add(bank); 

        JPanel logoBG = new JPanel();
        logoBG.setBounds(476, 366, 328, 100);
        logoBG.setBackground(customViolet);
        frame.add(logoBG);

        // PROGRESS BAR LOGIC
        filledLoadingBar = createRoundedPanel(324, 656, 0, 10, 18, null, 0);
        filledLoadingBar.setBackground(customViolet);
        frame.add(filledLoadingBar);

        JPanel loadingBarBG = createRoundedPanel(324, 656, 632, 10, 18, Color.GRAY, 1);
        loadingBarBG.setBackground(new Color(230, 230, 230));
        frame.add(loadingBarBG);

        frame.setVisible(true);
        startLoading(); 
    }

    private void startLoading() {
        // Timer runs every 20 milliseconds
        Timer timer = new Timer(20, e -> {
            int currentWidth = filledLoadingBar.getWidth();
            
            if (currentWidth < maxWidth) {
                // Increase width by 16 pixels each tick
                filledLoadingBar.setSize(currentWidth + 16, filledLoadingBar.getHeight());
            } else {
                ((Timer)e.getSource()).stop();
                
                // Transition to next screen
                //JOptionPane.showMessageDialog(frame, "Loading Complete!");
                frame.dispose();
                //new errorCatcher().paymentDeclined(); // Example: Open your other class
                new errorCatcher();
            }
        });
        timer.start();
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

/*     public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new loadingScreen());
    }
        */
}