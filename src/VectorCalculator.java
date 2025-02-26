import javax.swing.*; // Імпорт бібліотеки для створення графічного інтерфейсу
import java.awt.*; // Імпорт бібліотеки для роботи з графічними елементами
import java.awt.event.ActionEvent; // Імпорт класу для обробки подій
import java.awt.event.ActionListener; // Імпорт інтерфейсу для обробки подій
import java.text.DecimalFormat; // Імпорт класу для форматування чисел

public class VectorCalculator extends JFrame {
    private JTextField[] vectorInputs = new JTextField[6]; // Масив полів введення для координат двох векторів
    private JLabel[] resultLabels = new JLabel[4]; // Масив міток для відображення результатів
    private DecimalFormat df = new DecimalFormat("0.00"); // Формат чисел до двох знаків після коми

    public VectorCalculator() {
        setTitle("Обчислення норм, відстані та кута між векторами Мірошниченко Олександри"); // Заголовок вікна
        setSize(500, 400); // Встановлення розміру вікна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Завершення програми при закритті вікна
        setLayout(new GridLayout(10, 2, 5, 5)); // Встановлення менеджера компоновки для розміщення елементів

        add(new JLabel("Вектор 1 (x1, x2, x3):")); // Додавання мітки для першого вектора
        for (int i = 0; i < 3; i++) {
            vectorInputs[i] = new JTextField(); // Створення текстових полів для введення координат першого вектора
            add(vectorInputs[i]);
        }

        add(new JLabel("Вектор 2 (x1, x2, x3):")); // Додавання мітки для другого вектора
        for (int i = 3; i < 6; i++) {
            vectorInputs[i] = new JTextField(); // Створення текстових полів для введення координат другого вектора
            add(vectorInputs[i]);
        }

        JButton calculateButton = new JButton("Обчислити"); // Кнопка для запуску обчислень
        calculateButton.setBackground(Color.GREEN);
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(new CalculateListener()); // Додавання обробника подій
        add(calculateButton);

        JButton clearButton = new JButton("Очистити"); // Кнопка для очищення полів
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> clearFields()); // Додавання обробника подій
        add(clearButton);

        String[] labels = {"||V1||", "||V2||", "Відстань", "Кут (градуси)"}; // Мітки для результатів
        for (int i = 0; i < labels.length; i++) {
            add(new JLabel(labels[i])); // Додавання мітки
            resultLabels[i] = new JLabel("-"); // Створення мітки для результату
            add(resultLabels[i]);
        }

        setVisible(true); // Робимо вікно видимим
    }

    private class CalculateListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                double[] v1 = new double[3];
                double[] v2 = new double[3];
                for (int i = 0; i < 3; i++) {
                    v1[i] = Double.parseDouble(vectorInputs[i].getText()); // Зчитуємо координати першого вектора
                    v2[i] = Double.parseDouble(vectorInputs[i + 3].getText()); // Зчитуємо координати другого вектора
                }

                double normV1 = euclideanNorm(v1); // Обчислення норми першого вектора
                double normV2 = euclideanNorm(v2); // Обчислення норми другого вектора
                double distance = vectorDistance(v1, v2); // Обчислення відстані між векторами
                String angle = angleBetweenVectors(v1, v2); // Обчислення кута між векторами

                resultLabels[0].setText(df.format(normV1)); // Виведення норми першого вектора
                resultLabels[1].setText(df.format(normV2)); // Виведення норми другого вектора
                resultLabels[2].setText(df.format(distance)); // Виведення відстані
                resultLabels[3].setText(angle); // Виведення кута
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Будь ласка, введіть коректні числові значення.", "Помилка", JOptionPane.ERROR_MESSAGE); // Виведення повідомлення про помилку
            }
        }
    }

    private double euclideanNorm(double[] v) {
        return Math.sqrt(v[0] * v[0] + v[1] * v[1] + v[2] * v[2]); // Обчислення евклідової норми
    }

    private double vectorDistance(double[] v1, double[] v2) {
        return Math.sqrt(Math.pow(v2[0] - v1[0], 2) + Math.pow(v2[1] - v1[1], 2) + Math.pow(v2[2] - v1[2], 2)); // Обчислення евклідової відстані між векторами
    }

    private String angleBetweenVectors(double[] v1, double[] v2) {
        double dotProduct = v1[0] * v2[0] + v1[1] * v2[1] + v1[2] * v2[2]; // Обчислення скалярного добутку
        double normV1 = euclideanNorm(v1);
        double normV2 = euclideanNorm(v2);
        if (normV1 == 0 || normV2 == 0) {
            return "Неможливо обчислити"; // Перевірка на нульові вектори
        }
        double angleRad = Math.acos(dotProduct / (normV1 * normV2)); // Обчислення кута в радіанах
        return df.format(Math.toDegrees(angleRad)); // Конвертація в градуси та форматування
    }

    private void clearFields() {
        for (JTextField field : vectorInputs) {
            field.setText(""); // Очищення полів введення
        }
        for (JLabel label : resultLabels) {
            label.setText("-"); // Скидання міток результатів
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VectorCalculator::new); // Запуск програми в потоці GUI
    }
}

