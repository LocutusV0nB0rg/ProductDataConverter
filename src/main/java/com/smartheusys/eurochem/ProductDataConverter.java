package com.smartheusys.eurochem;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartheusys.eurochem.excel.InputExcelService;
import com.smartheusys.eurochem.input.InputLineService;
import com.smartheusys.eurochem.output.OutputLine;
import com.smartheusys.eurochem.output.OutputLineService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
        logger.info("Arguments: {}", Arrays.toString(args));

        if (args.length != 1) {
            logger.fatal("You must provide an input file");
            logger.fatal("Usage: java -jar ProductDataConverter.jar <inputfile>.xlsx");
            logger.fatal("Exiting application.");
            return;
        }

        String inputFile = args[0];
        String outputFile = "output.xls";

        logger.info("Preparing files...");
        logger.debug("Input file: {}", inputFile);
        fileService.setInputFile(inputFile);
        fileService.setOutputFile(outputFile);

        fileService.deleteFileIfExists(outputFile);

        try {
            inputExcelService.initiateExcelService();
        } catch (IOException | InvalidFormatException e) {
            logger.fatal("Error while initiating Excel service: {}", e.getMessage());
            logger.fatal("Exiting application.");
            return;
        }

        List<OutputLine> outputLineList = conversionService.convertInputLinesToOutputLines(inputLineService.getInputLines());
        outputLineService.saveOutputLines(outputLineList);

        logger.info("Application finished.");
    }
}
