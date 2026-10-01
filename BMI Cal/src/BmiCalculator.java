import javax.swing.*;
import javax.swing.table.DefaultTableModel; // Added for table data handling
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BmiCalculator {
    private JPanel BmiCalculator;
    private JTextArea BMICLACULATORTextArea;
    private JRadioButton englishRadioButton;
    private JRadioButton metricRadioButton;
    private JTextPane weightTextPane;
    private JTextArea textArea1; // Weight Input
    private JTextField heightTextField;
    private JTextArea textArea2; // Height Input
    private JButton CALCULATEBMIButton;
    private JTextArea YOURBMIISTextArea; // BMI Output
    private JTextArea CATEGORYTextArea;    // Category Output
    private JTable table1;                // Reference Table
    private JButton clearButton;
    private JTextField BMIREFERENCETextField;

    public BmiCalculator() {
        // 1. Group the radio buttons
        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(englishRadioButton);
        unitGroup.add(metricRadioButton);
        metricRadioButton.setSelected(true); // Default to Metric

        // 2. Populate data into table1
        setupReferenceTable();

        // 3. Add action listener to the Calculate button
        CALCULATEBMIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBMI();
            }
        });

        // 4. Add action listener to the Clear button
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    // Helper method to setup and fill table1
    private void setupReferenceTable() {
        // Define table column headers
        String[] columnNames = {"BMI Classification", "BMI Value Range"};

        // Define row data matching the NIH / Department of Health guidelines
        Object[][] data = {
                {"Underweight", "Less than 18.5"},
                {"Normal", "18.5 – 24.9"},
                {"Overweight", "25.0 – 29.9"},
                {"Obese", "30.0 or greater"}
        };

        // Create a model and apply it to table1
        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make the reference chart read-only for the user
            }
        };

        table1.setModel(model);
    }

    private void calculateBMI() {
        try {
            double weight = Double.parseDouble(textArea1.getText().trim());
            double height = Double.parseDouble(textArea2.getText().trim());
            double bmi = 0;

            if (englishRadioButton.isSelected()) {
                bmi = (weight * 703) / (height * height);
            } else if (metricRadioButton.isSelected()) {
                bmi = weight / (height * height);
            }

            String category;
            if (bmi < 18.5) {
                category = "Underweight";
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                category = "Normal";
            } else if (bmi >= 25.0 && bmi <= 29.9) {
                category = "Overweight";
            } else {
                category = "Obese";
            }

            YOURBMIISTextArea.setText("YOUR BMI IS: " + String.format("%.2f", bmi));
            CATEGORYTextArea.setText("CATEGORY: " + category);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(BmiCalculator,
                    "Please enter valid numeric values for Weight and Height.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        textArea1.setText("");
        textArea2.setText("");
        YOURBMIISTextArea.setText("YOUR BMI IS: ");
        CATEGORYTextArea.setText("CATEGORY: ");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("BmiCalculator");
        frame.setContentPane(new BmiCalculator().BmiCalculator);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
