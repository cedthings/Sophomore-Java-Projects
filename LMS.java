import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

public class LMS {

    int borderWidth = 1440;
    int borderHeight = 1024;

    Color customGreen = new Color(9, 70, 75);
    Color customDarkGreen = new Color(48, 51, 34);
    Color customGray = new Color (217, 217, 217);

    JFrame frame = new JFrame("LMS");
    JLabel worldName = new JLabel("Republic of the World Teyvat");
    JLabel sat = new JLabel("SUMERU AKADEMIYA AND TECHNOLOGY");
    JLabel sea = new JLabel("SCIENCE EDUCATION AND ACADEMY");
    JPanel logoLine = new JPanel();
    
    JPanel selectionLine = new JPanel();

    public LMS() {
        frame.setSize(borderWidth, borderHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(customGreen);
        frame.setLayout(null);

        // LOGOS
        worldName.setBounds(176, 74, 300, 20);
        worldName.setFont(new Font("Inter", Font.PLAIN, 15));
        worldName.setForeground(Color.WHITE);
        worldName.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(worldName);

        logoLine.setBounds(176, 98, 384, 1);
        logoLine.setBackground(Color.WHITE);
        frame.add(logoLine);

        sat.setBounds(176, 105, 300, 18);
        sat.setFont(new Font("Inter", Font.PLAIN, 15));
        sat.setForeground(Color.WHITE);
        sat.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(sat);

        sea.setBounds(176, 128, 450, 29);
        sea.setFont(new Font("Inter", Font.BOLD, 24));
        sea.setForeground(Color.WHITE);
        sea.setHorizontalAlignment(SwingConstants.LEFT);
        frame.add(sea);

        // BUTTONS
        JButton homeButton = createNavButton("Home", 758, 207, 124);
        JButton aboutButton = createNavButton("About SAT-SEA", 885, 207, 231);
        JButton programsButton = createNavButton("Programs", 1120, 207, 143);
        JButton contactButton = createNavButton("About", 1269, 207, 152);

        selectionLine.setBackground(Color.WHITE);
        selectionLine.setBounds(758, 257, 124, 2);

        homeButton.addActionListener(e -> moveSelection(homeButton));
        aboutButton.addActionListener(e -> moveSelection(aboutButton));
        programsButton.addActionListener(e -> moveSelection(programsButton));
        contactButton.addActionListener(e -> moveSelection(contactButton));

        //WELCOME SECTION
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBounds(0, 290, 1440, 444);
        welcomePanel.setBackground(customDarkGreen);

        JLabel quotes = new JLabel("<html>''Book learning alone is not enough to cultivate <br> intelligence. All those scholars in the Akademiya <br> are prime examples''</html>");
        quotes.setBounds(35, 326, 575, 87);
        quotes.setFont(new Font("Inter", Font.PLAIN, 24));
        quotes.setForeground(Color.WHITE);
        quotes.setHorizontalAlignment(SwingConstants.LEFT); 
        welcomePanel.add(quotes);

        JLabel alhaitham = new JLabel("- Alhaitham");
        alhaitham.setBounds(67, 431, 511, 29);
        alhaitham.setFont(new Font("Inter", Font.PLAIN, 24));
        alhaitham.setForeground(Color.WHITE);
        alhaitham.setHorizontalAlignment(SwingConstants.RIGHT); 
        welcomePanel.add(alhaitham);

        //Main Headers
        JLabel welcome = new JLabel("WELCOME TO THE");
        welcome.setBounds(25, 603, 600, 39);
        welcome.setFont(new Font("Inter", Font.PLAIN, 24));
        welcome.setForeground(Color.WHITE);
        welcome.setHorizontalAlignment(SwingConstants.LEFT); 

        JLabel seaBold = new JLabel("SCIENCE EDUCATION ACADEMY");
        seaBold.setBounds(25, 638, 638, 39);
        seaBold.setFont(new Font("Inter", Font.BOLD, 40));
        seaBold.setForeground(Color.WHITE);
        seaBold.setHorizontalAlignment(SwingConstants.LEFT);

        JLabel subTitle = new JLabel("TECHNOLOGY-INNOVATION • PEOPLE-CENTERED • DATA-DRIVEN");
        subTitle.setBounds(25, 668, 600, 39);
        subTitle.setFont(new Font("Inter", Font.PLAIN, 16));
        subTitle.setForeground(Color.WHITE);
        subTitle.setHorizontalAlignment(SwingConstants.LEFT);

        // ENROLL BUTTON (Radius: 18)
        JButton enrollButton = new JButton("ENROLL NOW") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int thickness = 1; // Thickness of the white border
                g2.setColor(getBackground());
                g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 18, 18));
                g2.setStroke(new BasicStroke(thickness));
                g2.setColor(Color.WHITE);
                g2.draw(new RoundRectangle2D.Float(thickness/2f, thickness/2f, getWidth() - thickness, getHeight() - thickness, 18, 18));
                g2.setColor(getForeground());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 4;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };
        enrollButton.setBounds(550, 791, 340, 80);
        enrollButton.setFont(new Font("Inter", Font.BOLD, 24));
        enrollButton.setForeground(Color.WHITE);
        enrollButton.setBackground(customGreen);
        enrollButton.setBorderPainted(false);
        enrollButton.setContentAreaFilled(false);
        enrollButton.setFocusPainted(false);

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new registePage(); 
            }
        });

        JPanel image1 = createRoundedPanel(30, 938, 420, 305, 26);
        JPanel image2 = createRoundedPanel(510, 938, 420, 305, 26);
        JPanel image3 = createRoundedPanel(990, 938, 420, 305, 26);

        frame.add(homeButton);
        frame.add(aboutButton);
        frame.add(programsButton);
        frame.add(contactButton);
        frame.add(selectionLine);
        frame.add(quotes);
        frame.add(alhaitham);
        frame.add(welcome);
        frame.add(seaBold);
        frame.add(subTitle);
        frame.add(enrollButton);
        frame.add(image1);
        frame.add(image2);
        frame.add(image3);


        frame.add(welcomePanel);
        

        frame.setVisible(true);
    }

    private JButton createNavButton(String text, int x, int y, int w) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, w, 50);
        btn.setBackground(customGreen);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Inter", Font.PLAIN, 24));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(true);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        return btn;
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
        panel.setBackground(customGray);
        panel.setOpaque(false);
        return panel;
    }

    private void moveSelection(JButton target) {
        selectionLine.setBounds(target.getX(), target.getY() + target.getHeight(), target.getWidth(), 2);
        frame.repaint();
    }
    public static void main(String[] args) throws Exception {
        new LMS();
    }
}