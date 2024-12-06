package com.smartheusys.eurochem;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartheusys.eurochem.input.InputLine;
import com.smartheusys.eurochem.output.OutputLine;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class ConversionService {
    private final Logger logger;

    public List<OutputLine> convertInputLinesToOutputLines(List<InputLine> inputLines) {
        logger.info("Attempting to convert {} input lines to output lines.", inputLines.size());

        List<OutputLine> outputLines = new ArrayList<>();

        Map<String, List<InputLine>> inputLinesByProduct = inputLines.stream().collect(Collectors.groupingBy(InputLine::getFProduct));
        for (Map.Entry<String, List<InputLine>> fProductToLines : inputLinesByProduct.entrySet()) {
            OutputLine outputLine = new OutputLine();
            outputLine.setFProduct(fProductToLines.getKey());

            List<InputLine> linesOfProduct = fProductToLines.getValue();
            Map<String, List<InputLine>> fRepSequenceToEntries = linesOfProduct.stream().collect(Collectors.groupingBy(InputLine::getFRepSequence));
            for (Map.Entry<String, List<InputLine>> fRepSequenceToEntriesEntry : fRepSequenceToEntries.entrySet()) {
                List<InputLine> entriesOfOneDataPoint = fRepSequenceToEntriesEntry.getValue();

                if (entriesOfOneDataPoint.size() != 3) {
                    logger.trace("Expected 3 entries for product {} and rep sequence {}, but got {}", outputLine.getFProduct(), fRepSequenceToEntriesEntry.getKey(), entriesOfOneDataPoint.size());
                    logger.trace("Not processing this data point. Ommited F_REP_SEQUENCE: {}", fRepSequenceToEntriesEntry.getKey());
                    continue;
                }

                String outputColumnName = null, outputColumnUnit = null, outputValue = null;

                for (InputLine entry : entriesOfOneDataPoint) {
                    String dataCodeText = entry.getDataCodeText();
                    if (dataCodeText.contains("value") || dataCodeText.contains("Value")) {
                        // we have found the value of the data point
                        outputValue = entry.getFData();
                    } else if (dataCodeText.contains("unit") || dataCodeText.contains("Unit")) {
                        // we have found the unit of the data point
                        outputColumnUnit = entry.getFData();
                    } else {
                        // we have found the name of the data point
                        outputColumnName = entry.getFBTextLine();
                    }
                }

                if (outputColumnName == null) {
                    logger.warn("No F_B_TEXT_LINE column name found for product {} and rep sequence {}", outputLine.getFProduct(), fRepSequenceToEntriesEntry.getKey());
                }

                if (outputColumnUnit == null) {
                    logger.warn("No F_DATA unit found for product {} and rep sequence {}", outputLine.getFProduct(), fRepSequenceToEntriesEntry.getKey());
                }

                if (outputValue == null) {
                    logger.warn("No F_DATA value found for product {} and rep sequence {}", outputLine.getFProduct(), fRepSequenceToEntriesEntry.getKey());
                }

                outputLine.getValues().add(new OutputLine.Column(outputColumnName, outputColumnUnit, outputValue));
            }
            outputLines.add(outputLine);
        }

        logger.info("Successfully converted {} input lines to {} output lines.", inputLines.size(), outputLines.size());

        return outputLines;
    }
}
