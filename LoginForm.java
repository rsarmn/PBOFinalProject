import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private final Color primaryColor = new Color(25, 118, 210);
    private final Color backgroundColor = new Color(232, 245, 255);
    
    // Simpan username dan password (Dalam praktik nyata, gunakan database)
    private static final HashMap<String, String> userCredentials = new HashMap<>();
    static {
        userCredentials.put("admin", "admin123");
        userCredentials.put("user", "user123");
    }

    public LoginForm() {
        setTitle("Login - Calculator Pro");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set background color
        getContentPane().setBackground(backgroundColor);
        setLayout(new BorderLayout());

        // Header Panel dengan gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                GradientPaint gp = new GradientPaint(0, 0, primaryColor, w, h, 
                    new Color(13, 71, 161));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };
        headerPanel.setPreferredSize(new Dimension(400, 100));
        
        // Logo dan judul
        JLabel titleLabel = new JLabel("👤 Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBackground(backgroundColor);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField = new JTextField(20);
        styleTextField(usernameField);

        // Password field
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField = new JPasswordField(20);
        styleTextField(passwordField);

        // Login button
        JButton loginButton = new JButton("Login");
        styleButton(loginButton);
        loginButton.addActionListener(e -> verifyLogin());

        // Register button
        JButton registerButton = new JButton("Register");
        styleButton(registerButton);
        registerButton.addActionListener(e -> showRegisterDialog());

        // Add components to form panel
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(userLabel, gbc);
        gbc.gridy = 1;
        formPanel.add(usernameField, gbc);
        gbc.gridy = 2;
        formPanel.add(passLabel, gbc);
        gbc.gridy = 3;
        formPanel.add(passwordField, gbc);
        gbc.gridy = 4;
        gbc.insets = new Insets(20, 5, 5, 5);
        formPanel.add(loginButton, gbc);
        gbc.gridy = 5;
        gbc.insets = new Insets(5, 5, 5, 5);
        formPanel.add(registerButton, gbc);

        // Add panels to frame
        add(headerPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);

        // Add key listener for Enter key
        KeyAdapter enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    verifyLogin();
                }
            }
        };
        usernameField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(primaryColor),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(primaryColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(13, 71, 161));
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(primaryColor);
            }
        });
    }

    private void verifyLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (userCredentials.containsKey(username) && 
            userCredentials.get(username).equals(password)) {
            JOptionPane.showMessageDialog(this, 
                "Login berhasil! Selamat datang, " + username,
                "Login Sukses", 
                JOptionPane.INFORMATION_MESSAGE);
            new MainMenu().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Username atau Password salah!",
                "Login Gagal",
                JOptionPane.ERROR_MESSAGE);
            passwordField.setText("");
        }
    }

    private void showRegisterDialog() {
        JDialog registerDialog = new JDialog(this, "Register New User", true);
        registerDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField newUsername = new JTextField(20);
        JPasswordField newPassword = new JPasswordField(20);
        JPasswordField confirmPassword = new JPasswordField(20);
        styleTextField(newUsername);
        styleTextField(newPassword);
        styleTextField(confirmPassword);

        JButton registerBtn = new JButton("Register");
        styleButton(registerBtn);

        registerBtn.addActionListener(e -> {
            String username = newUsername.getText();
            String password = new String(newPassword.getPassword());
            String confirm = new String(confirmPassword.getPassword());

            if (username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                JOptionPane.showMessageDialog(registerDialog,
                    "Semua field harus diisi!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!password.equals(confirm)) {
                JOptionPane.showMessageDialog(registerDialog,
                    "Password tidak cocok!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userCredentials.containsKey(username)) {
                JOptionPane.showMessageDialog(registerDialog,
                    "Username sudah digunakan!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            userCredentials.put(username, password);
            JOptionPane.showMessageDialog(registerDialog,
                "Registrasi berhasil! Silakan login.",
                "Success",
                JOptionPane.INFORMATION_MESSAGE);
            registerDialog.dispose();
        });

        gbc.gridx = 0; gbc.gridy = 0;
        registerDialog.add(new JLabel("Username:"), gbc);
        gbc.gridy = 1;
        registerDialog.add(newUsername, gbc);
        gbc.gridy = 2;
        registerDialog.add(new JLabel("Password:"), gbc);
        gbc.gridy = 3;
        registerDialog.add(newPassword, gbc);
        gbc.gridy = 4;
        registerDialog.add(new JLabel("Confirm Password:"), gbc);
        gbc.gridy = 5;
        registerDialog.add(confirmPassword, gbc);
        gbc.gridy = 6;
        gbc.insets = new Insets(20, 5, 5, 5);
        registerDialog.add(registerBtn, gbc);

        registerDialog.pack();
        registerDialog.setLocationRelativeTo(this);
        registerDialog.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}