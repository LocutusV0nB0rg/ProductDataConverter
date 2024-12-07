package com.smartheusys.eurochem.ui;

import javax.swing.*;
import java.awt.*;

public class InputFilePanel extends JPanel {

        private JButton chooseInputFileButton;
        private JTextField inputFilePathTextField;

        public InputFilePanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));

            chooseInputFileButton = new JButton("Choose input file");
            chooseInputFileButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.showOpenDialog(this);
                inputFilePathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            });
            chooseInputFileButton.setSize(30, 1);

            inputFilePathTextField = new JTextField();
            inputFilePathTextField.setColumns(50);

            add(chooseInputFileButton);
            add(Box.createHorizontalStrut(15));
            add(inputFilePathTextField);
        }

        public String getInputFilePath() {
            return inputFilePathTextField.getText();
        }
}
