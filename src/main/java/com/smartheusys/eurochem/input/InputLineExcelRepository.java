package com.smartheusys.eurochem.input;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartheusys.eurochem.excel.InputExcelService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;

import java.util.List;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class InputLineExcelRepository implements InputLineRepository {
    private final InputExcelService inputExcelService;
    private final Logger logger;

    private List<InputLine> cachedInputLines;

    @Override
    public List<InputLine> getInputLines() {
        if (cachedInputLines == null) {
            readInputLines();
        }

        logger.debug("Returning cached input lines.");

        return cachedInputLines;
    }

    private void readInputLines() {
        logger.debug("Reading input lines from Excel file.");

        cachedInputLines = Lists.newArrayList();
        //The first line are the headers
        for (int i = 1; i < inputExcelService.getLastRowIndex(); i++) {
            Cell fProductCell = inputExcelService.getCell("F_PRODUCT", i);
            String fProduct = "";
            if (fProductCell != null)
                fProduct = fProductCell.getStringCellValue();

            Cell dataCodeTextCell = inputExcelService.getCell("Data Code Text", i);
            String dataCodeText = "";
            if (dataCodeTextCell != null)
                dataCodeText = dataCodeTextCell.getStringCellValue();

            Cell fDataCell = inputExcelService.getCell("F_DATA", i);
            String fData = "";
            if (fDataCell != null)
                fData = fDataCell.getStringCellValue();

            Cell fBTextLineCell = inputExcelService.getCell("F_B_TEXT_LINE", i);
            String fBTextLine = "";
            if (fBTextLineCell != null)
                fBTextLine = fBTextLineCell.getStringCellValue();

            Cell fRepSequenceCell = inputExcelService.getCell("F_REP_SEQUENCE", i);
            String fRepSequence = "";
            if (fRepSequenceCell != null)
                fRepSequence = String.valueOf(fRepSequenceCell.getNumericCellValue());

            InputLine inputLine = new InputLine();
            inputLine.setFProduct(fProduct);
            inputLine.setDataCodeText(dataCodeText);
            inputLine.setFData(fData);
            inputLine.setFBTextLine(fBTextLine);
            inputLine.setFRepSequence(fRepSequence);

            cachedInputLines.add(inputLine);
        }
    }
}
