import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BMIcalculatotor {
    private JPanel mainPanel;
    private JLabel bmicalculator;
    private JRadioButton englishRadioButton;
    private JRadioButton metricRadioButton;
    private JLabel weightlabal;
    private JTextField weightTextFiled;
    private JLabel heightlaabel;
    private JTextField heighttextfiled;
    private JButton calculatebmi;
    private JLabel yourbmi;
    private JTextField textFieldyourbmiresult;
    private JLabel catogorylabl;
    private JTextField catogoryresult;
    private JLabel BMIreferencelabel;
    private JButton CLEARButton;
    private JLabel bmireferencelabel;
    private JLabel Underweightlabel;
    private JTable table1;
    private JScrollPane bmitable;

    public BMIcalculatotor() {

        String[] columnNames = {"Category", "BMI range"};
        Object[][] data = {
                {"Underweight", "< 18.5"},
                {"Normal", "18.5 - 24.9"},
                {"Overweight", "25 - 29.9"},
                {"Obese", "30 or Greater"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table1.setModel(model);


        ButtonGroup unitGroup = new ButtonGroup();
        unitGroup.add(metricRadioButton);
        unitGroup.add(englishRadioButton);
        metricRadioButton.setSelected(true);


        setPlaceholder(weightTextFiled, "Enter your weight");
        setPlaceholder(heighttextfiled, "Enter your height");


        metricRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weightlabal.setText("Weight (kg):");
                heightlaabel.setText("Height (m):");
            }
        });

        englishRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                weightlabal.setText("Weight (lbs):");
                heightlaabel.setText("Height (in):");
            }
        });


        calculatebmi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double weight = Double.parseDouble(weightTextFiled.getText());
                    double height = Double.parseDouble(heighttextfiled.getText());
                    double bmi = 0;

                    if (metricRadioButton.isSelected()) {
                        bmi = weight / (height * height);
                    } else if (englishRadioButton.isSelected()) {
                        bmi = (weight * 703) / (height * height);
                    }

                    textFieldyourbmiresult.setText(String.format("%.2f", bmi));

                    String category;
                    if (bmi < 18.5) {
                        category = "Underweight";
                    } else if (bmi >= 18.5 && bmi <= 24.9) {
                        category = "Normal";
                    } else if (bmi >= 25 && bmi <= 29.9) {
                        category = "Overweight";
                    } else {
                        category = "Obese";
                    }
                    catogoryresult.setText(category);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric values for Weight and Height.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        CLEARButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                setPlaceholder(weightTextFiled, "Enter your weight");
                setPlaceholder(heighttextfiled, "Enter your height");

                textFieldyourbmiresult.setText("");
                catogoryresult.setText("");
                metricRadioButton.setSelected(true);
                weightlabal.setText("Weight (kg):");
                heightlaabel.setText("Height (m):");
            }
        });
    }


    private void setPlaceholder(JTextField textField, String placeholderText) {
        textField.setText(placeholderText);
        textField.setForeground(java.awt.Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusListener() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholderText)) {
                    textField.setText("");
                    textField.setForeground(java.awt.Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholderText);
                    textField.setForeground(java.awt.Color.GRAY);
                }
            }
        });
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("BMI Calculator");
        BMIcalculatotor calculator = new BMIcalculatotor();
        frame.setContentPane(calculator.mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450, 500);
        frame.setVisible(true);
    }
}
