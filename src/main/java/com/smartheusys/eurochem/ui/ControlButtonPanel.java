package com.smartheusys.eurochem.ui;

import com.smartheusys.eurochem.ProductDataConverter;
import com.smartheusys.eurochem.output.OutputLine;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class ControlButtonPanel extends JPanel {
    private InputFilePanel inputFilePanel;
    private OutputFilePanel outputFilePanel;

    private JButton startProcessingButton;

    public ControlButtonPanel(ProductDataConverter productDataConverter, Logger logger) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        startProcessingButton = new JButton("Start processing");
        startProcessingButton.addActionListener(e -> {
            String inputFilePath = inputFilePanel.getInputFilePath();
            String outputDirectoryPath = outputFilePanel.getOutputDirectoryPath();
            String outputFileName = outputFilePanel.getOutputFileName();

            if (outputDirectoryPath.endsWith("/")) {
                outputDirectoryPath = outputDirectoryPath.substring(0, outputDirectoryPath.length() - 1);
            }
            String outputFile = outputDirectoryPath + "/" + outputFileName;

            productDataConverter.startProcessing(inputFilePath, outputFile);
        });

        inputFilePanel = new InputFilePanel();
        outputFilePanel = new OutputFilePanel();

        add(inputFilePanel);
        add(outputFilePanel);
        add(startProcessingButton);
    }

}
