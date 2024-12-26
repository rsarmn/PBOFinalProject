import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KalkulatorGUI extends JFrame {
    private JTextField display;
    private String currentExpression = "";
    private boolean isTrigMode = false; // Melacak apakah fungsi trigonometri telah dipilih
    private String trigFunction = ""; // Melacak fungsi trigonometri yang dipilih

    public KalkulatorGUI() {
        setTitle("Kalkulator");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout utama
        setLayout(new BorderLayout());

        // Display
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 24));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 5, 5));

        // Tombol angka dan operasi
        String[] buttons = {
            "7", "8", "9", "/", 
            "4", "5", "6", "*", 
            "1", "2", "3", "-", 
            "0", ".", "=", "+",
            "sin", "cos", "tan", "C"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.addActionListener(new ButtonClickListener());
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        // Back to Main Menu button
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            new MainMenu().setVisible(true);
            dispose();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            try {
                switch (command) {
                    case "C": // Clear
                        currentExpression = "";
                        display.setText("");
                        isTrigMode = false;
                        trigFunction = "";
                        break;

                    case "=": // Evaluasi ekspresi
                        if (isTrigMode) {
                            double value = Double.parseDouble(currentExpression);
                            double radians = Math.toRadians(value);
                            double result = switch (trigFunction) {
                                case "sin" -> Math.sin(radians);
                                case "cos" -> Math.cos(radians);
                                case "tan" -> Math.tan(radians);
                                default -> throw new IllegalStateException("Fungsi trigonometri tidak valid");
                            };
                            display.setText(String.valueOf(result));
                        } else {
                            double result = evaluateExpression(currentExpression);
                            display.setText(String.valueOf(result));
                        }
                        currentExpression = "";
                        isTrigMode = false;
                        trigFunction = "";
                        break;

                    case "sin":
                    case "cos":
                    case "tan":
                        isTrigMode = true;
                        trigFunction = command;
                        if (!currentExpression.isEmpty()) {
                            double value = Double.parseDouble(currentExpression);
                            double radians = Math.toRadians(value);
                            double result = switch (command) {
                                case "sin" -> Math.sin(radians);
                                case "cos" -> Math.cos(radians);
                                case "tan" -> Math.tan(radians);
                                default -> 0;
                            };
                            display.setText(String.valueOf(result));
                            currentExpression = "";
                            isTrigMode = false;
                        } else {
                            display.setText(command + "()");
                        }
                        break;

                    default: // Angka dan operator
                        if (isTrigMode && trigFunction.isEmpty()) {
                            trigFunction = command;
                        }
                        currentExpression += command;
                        display.setText(currentExpression);
                        break;
                }
            } catch (Exception ex) {
                display.setText("Error");
                currentExpression = "";
                isTrigMode = false;
                trigFunction = "";
            }
        }

        private double evaluateExpression(String expression) throws Exception {
            String[] tokens = expression.split("(?<=[-+*/])|(?=[-+*/])");
            double result = Double.parseDouble(tokens[0]);

            for (int i = 1; i < tokens.length; i++) {
                String operator = tokens[i];
                double operand = Double.parseDouble(tokens[++i]);

                switch (operator) {
                    case "+" -> result += operand;
                    case "-" -> result -= operand;
                    case "*" -> result *= operand;
                    case "/" -> result /= operand;
                    default -> throw new IllegalArgumentException("Operator tidak valid");
                }
            }
            return result;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            KalkulatorGUI kalkulator = new KalkulatorGUI();
            kalkulator.setVisible(true);
        });
    }
}
