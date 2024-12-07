package com.smartheusys.eurochem.ui;

import javax.swing.*;
import java.awt.*;

public class OutputFilePanel extends JPanel {

        private JButton chooseOutputDirectoryButton;
        private JTextField outputDirectoryPathTextField;
        private JTextField outputFileNameTextField;

        public OutputFilePanel() {
            setLayout(new FlowLayout(FlowLayout.LEFT, 0, 10));

            chooseOutputDirectoryButton = new JButton("Choose output directory");
            chooseOutputDirectoryButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.showOpenDialog(this);
                outputDirectoryPathTextField.setText(fileChooser.getSelectedFile().getAbsolutePath());
            });

            outputDirectoryPathTextField = new JTextField();
            outputDirectoryPathTextField.setColumns(30);

            outputFileNameTextField = new JTextField("output.xls");
            outputFileNameTextField.setColumns(20);


            add(chooseOutputDirectoryButton);
            add(Box.createHorizontalStrut(15));
            add(outputDirectoryPathTextField);
            add(Box.createHorizontalStrut(15));
            add(outputFileNameTextField);
        }

        public String getOutputDirectoryPath() {
            return outputDirectoryPathTextField.getText();
        }

        public String getOutputFileName() {
            return outputFileNameTextField.getText();
        }
}
