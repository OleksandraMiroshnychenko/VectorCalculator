import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class VectorCalculator extends JFrame {
    private JTextField[] vectorInputs = new JTextField[6];
    private JLabel[] resultLabels = new JLabel[4];
    private DecimalFormat df = new DecimalFormat("0.00");

    public VectorCalculator() {
        setTitle("Обчислення норм, відстані та кута між векторами Мірошниченко Олександри");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 2, 5, 5));

        add(new JLabel("Вектор 1 (x1, x2, x3):"));
        for (int i = 0; i < 3; i++) {
            vectorInputs[i] = new JTextField();
            add(vectorInputs[i]);
        }

        add(new JLabel("Вектор 2 (x1, x2, x3):"));
        for (int i = 3; i < 6; i++) {
            vectorInputs[i] = new JTextField();
            add(vectorInputs[i]);
        }

        JButton calculateButton = new JButton("Обчислити");
        calculateButton.setBackground(Color.GREEN);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(new CalculateListener());
        add(calculateButton);

        JButton clearButton = new JButton("Очистити");
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields());
        add(clearButton);

        String[] labels = {"||V1||", "||V2||", "Відстань", "Кут (градуси)"};
        for (int i = 0; i < labels.length; i++) {
            add(new JLabel(labels[i]));
            resultLabels[i] = new JLabel("-");
            add(resultLabels[i]);
        }

        setVisible(true);
    }

    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double[] v1 = new double[3];
                double[] v2 = new double[3];
                for (int i = 0; i < 3; i++) {
                    v1[i] = Double.parseDouble(vectorInputs[i].getText());
                    v2[i] = Double.parseDouble(vectorInputs[i + 3].getText());
                }

                double normV1 = euclideanNorm(v1);
                double normV2 = euclideanNorm(v2);
                double distance = vectorDistance(v1, v2);
                String angle = angleBetweenVectors(v1, v2);

                resultLabels[0].setText(df.format(normV1));
                resultLabels[1].setText(df.format(normV2));
                resultLabels[2].setText(df.format(distance));
                resultLabels[3].setText(angle);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Будь ласка, введіть коректні числові значення.", "Помилка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private double euclideanNorm(double[] v) {
        return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]);
    }

    private double vectorDistance(double[] v1, double[] v2) {
        return Math.sqrt(Math.pow(v2[0] - v1[0], 2) + Math.pow(v2[1] - v1[1], 2) + Math.pow(v2[2] - v1[2], 2));
    }

    private String angleBetweenVectors(double[] v1, double[] v2) {
        double dotProduct = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2];
        double normV1 = euclideanNorm(v1);
        double normV2 = euclideanNorm(v2);
        if (normV1 == 0 || normV2 == 0) {
            return "Неможливо обчислити";
        }
        double angleRad = Math.acos(dotProduct / (normV1 * normV2));
        return df.format(Math.toDegrees(angleRad));
    }

    private void clearFields() {
        for (JTextField field : vectorInputs) {
            field.setText("");
        }
        for (JLabel label : resultLabels) {
            label.setText("-");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VectorCalculator::new);
    }
}
