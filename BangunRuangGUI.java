import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


// Kelas abstrak induk
abstract class BangunRuang {
    public abstract double volume();
    public abstract double luasSelimut();
    public abstract double luasPermukaan();
}

// Kelas Balok
class Balok extends BangunRuang {
    private double panjang;
    private double lebar;
    private double tinggi;

    public Balok(double panjang, double lebar, double tinggi) {
        this.panjang = panjang;
        this.lebar = lebar;
        this.tinggi = tinggi;
    }

    @Override
    public double volume() {
        return panjang * lebar * tinggi;
    }

    @Override
    public double luasSelimut() {
        return 2 * (panjang * tinggi + lebar * tinggi);
    }

    @Override
    public double luasPermukaan() {
        return 2 * (panjang * lebar + panjang * tinggi + lebar * tinggi);
    }
}

// Kelas Kubus
class Kubus extends BangunRuang {
    private double sisi;

    public Kubus(double sisi) {
        this.sisi = sisi;
    }

    @Override
    public double volume() {
        return Math.pow(sisi, 3);
    }

    @Override
    public double luasSelimut() {
        return 4 * Math.pow(sisi, 2);
    }

    @Override
    public double luasPermukaan() {
        return 6 * Math.pow(sisi, 2);
    }
}

// Kelas Bola
class Bola extends BangunRuang {
    private double jariJari;

    public Bola(double jariJari) {
        this.jariJari = jariJari;
    }

    @Override
    public double volume() {
        return (4.0/3.0) * Math.PI * Math.pow(jariJari, 3);
    }

    @Override
    public double luasSelimut() {
        return 4 * Math.PI * Math.pow(jariJari, 2);
    }

    @Override
    public double luasPermukaan() {
        return 4 * Math.PI * Math.pow(jariJari, 2);
    }
}

// Kelas Kerucut
class Kerucut extends BangunRuang {
    private double jariJari;
    private double tinggi;

    public Kerucut(double jariJari, double tinggi) {
        this.jariJari = jariJari;
        this.tinggi = tinggi;
    }

    private double hitungSisiMiring() {
        return Math.sqrt(Math.pow(jariJari, 2) + Math.pow(tinggi, 2));
    }

    @Override
    public double volume() {
        return (1.0/3.0) * Math.PI * Math.pow(jariJari, 2) * tinggi;
    }

    @Override
    public double luasSelimut() {
        return Math.PI * jariJari * hitungSisiMiring();
    }

    @Override
    public double luasPermukaan() {
        return Math.PI * jariJari * (jariJari + hitungSisiMiring());
    }
}

// Kelas PrismaSegitiga
class PrismaSegitiga extends BangunRuang {
    private double alas;
    private double tinggiSegitiga;
    private double tinggiPrisma;

    public PrismaSegitiga(double alas, double tinggiSegitiga, double tinggiPrisma) {
        this.alas = alas;
        this.tinggiSegitiga = tinggiSegitiga;
        this.tinggiPrisma = tinggiPrisma;
    }

    private double luasAlas() {
        return 0.5 * alas * tinggiSegitiga;
    }

    private double kelilingAlas() {
        double sisiMiring = Math.sqrt(Math.pow(alas/2, 2) + Math.pow(tinggiSegitiga, 2));
        return alas + (2 * sisiMiring);
    }

    @Override
    public double volume() {
        return luasAlas() * tinggiPrisma;
    }

    @Override
    public double luasSelimut() {
        return kelilingAlas() * tinggiPrisma;
    }

    @Override
    public double luasPermukaan() {
        return (2 * luasAlas()) + luasSelimut();
    }
}
public class BangunRuangGUI extends JFrame {
    private JComboBox<String> shapeSelector;
    private JPanel inputPanel;
    private JPanel resultPanel;
    private JTextField[] inputFields;
    private JLabel[] resultLabels;

    public BangunRuangGUI() {
        setTitle("Kalkulator Bangun Ruang");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        // Shape selector
        String[] shapes = {"Balok", "Kubus", "Bola", "Kerucut", "Prisma Segitiga"};
        shapeSelector = new JComboBox<>(shapes);
        shapeSelector.addActionListener(e -> updateInputFields());

        // Main panels
        inputPanel = new JPanel();
        resultPanel = new JPanel();

        // Back button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });

        // Create a panel for the back button
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(resultPanel, BorderLayout.CENTER);
        bottomPanel.add(backButton, BorderLayout.SOUTH);

        // Initialize components
        setupPanels();

        // Add components to frame
        add(shapeSelector, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Set initial state
        updateInputFields();

        // Set size and center the window
        setSize(400, 500);
        setLocationRelativeTo(null);
    }

    BangunRuangGUI(Object object) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void setupPanels() {
        inputPanel.setLayout(new GridBagLayout());
        resultPanel.setLayout(new GridLayout(3, 2, 5, 5));

        // Initialize result labels
        resultLabels = new JLabel[3];
        for (int i = 0; i < 3; i++) {
            resultLabels[i] = new JLabel("0.0");
            JLabel label = new JLabel();
            switch (i) {
                case 0:
                    label.setText("Volume:");
                    break;
                case 1:
                    label.setText("Luas Selimut:");
                    break;
                case 2:
                    label.setText("Luas Permukaan:");
                    break;
            }
            resultPanel.add(label);
            resultPanel.add(resultLabels[i]);
        }
    }

    // Added the missing addInputField method
    private void addInputField(GridBagConstraints gbc, String labelText, int index) {
        gbc.gridx = 0;
        gbc.gridy = index;
        gbc.gridwidth = 1;
        inputPanel.add(new JLabel(labelText), gbc);

        gbc.gridx = 1;
        inputFields[index] = new JTextField(10);
        inputPanel.add(inputFields[index], gbc);
    }

    private void updateInputFields() {
        inputPanel.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        String selectedShape = (String) shapeSelector.getSelectedItem();
        JButton calculateButton = new JButton("Hitung");

        switch (selectedShape) {
            case "Balok":
                inputFields = new JTextField[3];
                addInputField(gbc, "Panjang:", 0);
                addInputField(gbc, "Lebar:", 1);
                addInputField(gbc, "Tinggi:", 2);
                break;

            case "Kubus":
                inputFields = new JTextField[1];
                addInputField(gbc, "Sisi:", 0);
                break;

            case "Bola":
                inputFields = new JTextField[1];
                addInputField(gbc, "Jari-jari:", 0);
                break;

            case "Kerucut":
                inputFields = new JTextField[2];
                addInputField(gbc, "Jari-jari:", 0);
                addInputField(gbc, "Tinggi:", 1);
                break;

            case "Prisma Segitiga":
                inputFields = new JTextField[3];
                addInputField(gbc, "Alas Segitiga:", 0);
                addInputField(gbc, "Tinggi Segitiga:", 1);
                addInputField(gbc, "Tinggi Prisma:", 2);
                break;
        }

        calculateButton.addActionListener(e -> calculate());

        gbc.gridx = 0;
        gbc.gridy = inputFields.length;
        gbc.gridwidth = 2;
        inputPanel.add(calculateButton, gbc);

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    // Added the missing calculate method
    private void calculate() {
        try {
            String selectedShape = (String) shapeSelector.getSelectedItem();
            BangunRuang shape = null;

            switch (selectedShape) {
                case "Balok":
                    double panjang = Double.parseDouble(inputFields[0].getText());
                    double lebar = Double.parseDouble(inputFields[1].getText());
                    double tinggi = Double.parseDouble(inputFields[2].getText());
                    shape = new Balok(panjang, lebar, tinggi);
                    break;

                case "Kubus":
                    double sisi = Double.parseDouble(inputFields[0].getText());
                    shape = new Kubus(sisi);
                    break;

                case "Bola":
                    double jariJari = Double.parseDouble(inputFields[0].getText());
                    shape = new Bola(jariJari);
                    break;

                case "Kerucut":
                    double r = Double.parseDouble(inputFields[0].getText());
                    double h = Double.parseDouble(inputFields[1].getText());
                    shape = new Kerucut(r, h);
                    break;

                case "Prisma Segitiga":
                    double alas = Double.parseDouble(inputFields[0].getText());
                    double tinggiSegitiga = Double.parseDouble(inputFields[1].getText());
                    double tinggiPrisma = Double.parseDouble(inputFields[2].getText());
                    shape = new PrismaSegitiga(alas, tinggiSegitiga, tinggiPrisma);
                    break;
            }

            if (shape != null) {
                resultLabels[0].setText(String.format("%.2f", shape.volume()));
                resultLabels[1].setText(String.format("%.2f", shape.luasSelimut()));
                resultLabels[2].setText(String.format("%.2f", shape.luasPermukaan()));
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Mohon masukkan angka yang valid!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BangunRuangGUI().setVisible(true);
        });
    }
}