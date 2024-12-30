package com.smartheusys.eurochem;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartheusys.eurochem.excel.InputExcelService;
import com.smartheusys.eurochem.input.InputLineService;
import com.smartheusys.eurochem.output.OutputLine;
import com.smartheusys.eurochem.output.OutputLineService;
import com.smartheusys.eurochem.ui.PDCUI;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class ProductDataConverter {

    private final Logger logger;
    private final FileService fileService;

    private final InputLineService inputLineService;
    private final OutputLineService outputLineService;

    private final InputExcelService inputExcelService;
    private final ConversionService conversionService;

    public void startApplication(String[] args) {
        logger.info("Starting application...");

        logger.debug("Setting UI system like look and feel.");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            logger.warn("Error while setting UI system look and feel: {}", e.getMessage());
            logger.warn("Trace: ", e);
        }
        logger.info("Starting UI.");
        PDCUI ui = new PDCUI(this, logger);
        logger.info("UI started.");
    }

    public void startProcessing(String inputFilePath, String outputFile) {
        logger.info("Preparing files...");
        logger.info("Input file: {}", inputFilePath);
        logger.info("Output file: {}", outputFile);
        fileService.setInputFile(inputFilePath);

        fileService.setOutputFile(outputFile);
        fileService.deleteFileIfExists(outputFile);

        try {
            inputExcelService.initiateExcelService();
        } catch (IOException | InvalidFormatException e) {
            JOptionPane.showMessageDialog(new JFrame(), "Error while initiating Excel Service: " + e.getMessage(), "Fatal error.",
                    JOptionPane.ERROR_MESSAGE);
            logger.fatal("Error while initiating Excel service: {}", e.getMessage());
            logger.fatal("Exiting application.");
            return;
        }

        List<OutputLine> outputLineList = conversionService.convertInputLinesToOutputLines(inputLineService.getInputLines());
        outputLineService.saveOutputLines(outputLineList);

        logger.info("Conversion run finished.");
    }
}
