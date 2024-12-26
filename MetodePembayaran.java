import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;

public class MetodePembayaran extends JFrame {
    private JPanel paymentPanel;
    private JComboBox<String> paymentMethodSelector;
    private JTextField amountField;
    private JLabel resultLabel;
    
    public MetodePembayaran() {
        setTitle("Metode Pembayaran");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // Payment method selector
        String[] methods = {"Transfer Bank", "E-Wallet", "Kartu Kredit", "Cash"};
        paymentMethodSelector = new JComboBox<>(methods);
        paymentMethodSelector.addActionListener(e -> updatePaymentFields());

        // Main panel
        paymentPanel = new JPanel(new GridBagLayout());
        setupPaymentPanel();

        // Result panel
        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        resultLabel = new JLabel("Total Pembayaran: Rp. 0");
        resultPanel.add(resultLabel);

        // Back button
        JButton backButton = new JButton("Kembali ke Menu");
        backButton.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton processButton = new JButton("Proses Pembayaran");
        processButton.addActionListener(e -> processPayment());
        buttonPanel.add(processButton);
        buttonPanel.add(backButton);

        // Add components to frame
        add(paymentMethodSelector, BorderLayout.NORTH);
        add(paymentPanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setSize(500, 400);
    }

    private void setupPaymentPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Amount field
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentPanel.add(new JLabel("Jumlah Pembayaran:"), gbc);

        gbc.gridx = 1;
        amountField = new JTextField(15);
        paymentPanel.add(amountField, gbc);

        // Additional fields will be added by updatePaymentFields()
    }

    private void updatePaymentFields() {
        paymentPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Re-add amount field
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentPanel.add(new JLabel("Jumlah Pembayaran:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(amountField, gbc);

        String selectedMethod = (String) paymentMethodSelector.getSelectedItem();
        switch (selectedMethod) {
            case "Transfer Bank":
                addBankTransferFields(gbc);
                break;
            case "E-Wallet":
                addEWalletFields(gbc);
                break;
            case "Kartu Kredit":
                addCreditCardFields(gbc);
                break;
            case "Cash":
                addCashFields(gbc);
                break;
        }

        paymentPanel.revalidate();
        paymentPanel.repaint();
    }

    private void addBankTransferFields(GridBagConstraints gbc) {
        gbc.gridy = 1;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Nomor Rekening:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(new JTextField(15), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Nama Bank:"), gbc);
        gbc.gridx = 1;
        String[] banks = {"BCA", "Mandiri", "BNI", "BRI"};
        paymentPanel.add(new JComboBox<>(banks), gbc);
    }

    private void addEWalletFields(GridBagConstraints gbc) {
        gbc.gridy = 1;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Jenis E-Wallet:"), gbc);
        gbc.gridx = 1;
        String[] wallets = {"OVO", "GoPay", "DANA", "LinkAja"};
        paymentPanel.add(new JComboBox<>(wallets), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Nomor Telepon:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(new JTextField(15), gbc);
    }

    private void addCreditCardFields(GridBagConstraints gbc) {
        gbc.gridy = 1;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Nomor Kartu:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(new JTextField(15), gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Expired Date:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(new JTextField(5), gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("CVV:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(new JTextField(3), gbc);
    }

    private void addCashFields(GridBagConstraints gbc) {
        gbc.gridy = 1;
        gbc.gridx = 0;
        paymentPanel.add(new JLabel("Nama Kasir:"), gbc);
        gbc.gridx = 1;
        paymentPanel.add(new JTextField(15), gbc);
    }

    private void processPayment() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            String method = (String) paymentMethodSelector.getSelectedItem();
            resultLabel.setText(String.format("Total Pembayaran: Rp. %.2f (%s)", amount, method));
            
            JOptionPane.showMessageDialog(this,
                String.format("Pembayaran Rp. %.2f berhasil diproses via %s", amount, method),
                "Sukses",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Mohon masukkan jumlah pembayaran yang valid!",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MetodePembayaran().setVisible(true);
        });
    }
}