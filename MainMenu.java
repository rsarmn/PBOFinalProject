import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {
    private Color primaryColor = new Color(25, 118, 210); // Material Blue
    private Color hoverColor = new Color(13, 71, 161);    // Darker Blue
    private Color backgroundColor = new Color(232, 245, 255); // Light Blue Background

    public MainMenu() {
        setTitle("Calculator Pro");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set background color for main frame
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout(20, 20));

        // Create header panel with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, primaryColor, w, h, hoverColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(600, 100));

        // Add title to header
        JLabel titleLabel = new JLabel("Calculator Pro");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Create main menu panel
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1, 20, 20));
        menuPanel.setBackground(backgroundColor);
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        // Create custom buttons
        JButton[] buttons = new JButton[3];
        String[] buttonTexts = {"Kalkulator", "Bangun Ruang", "Pembayaran"};
        String[] buttonIcons = {"🧮", "📐", "💳"};

        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = createMenuButton(buttonTexts[i], buttonIcons[i]);
            menuPanel.add(buttons[i]);
        }

        // Add action listeners
        buttons[0].addActionListener(e -> {
            new KalkulatorGUI().setVisible(true);
            dispose();
        });

        buttons[1].addActionListener(e -> {
            new BangunRuangGUI().setVisible(true); // Memanggil konstruktor default BangunRuangGUI
            dispose();
        });

        buttons[2].addActionListener(e -> {
            new MetodePembayaran().setVisible(true);
            dispose();
        });

        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(menuPanel, BorderLayout.CENTER);

        // Add footer
        JLabel footerLabel = new JLabel("© 2024 Calculator Pro");
        footerLabel.setHorizontalAlignment(JLabel.CENTER);
        footerLabel.setForeground(primaryColor);
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(footerLabel, BorderLayout.SOUTH);
    }

    private JButton createMenuButton(String text, String icon) {
        JButton button = new JButton(icon + "  " + text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        button.setForeground(primaryColor);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor, 2),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(primaryColor);
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(primaryColor);
            }
        });

        return button;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new MainMenu().setVisible(true);
        });
    }
}
