package com.smartheusys.eurochem.excel;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartheusys.eurochem.FileService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class InputExcelService {
    private final FileService fileService;
    private final Logger logger;

    private Sheet mainInputDataSheet = null;

    private final List<TableColumn> tableColumnList = Lists.newArrayList();

    public void initiateExcelService() throws IOException, InvalidFormatException {
        logger.info("Initiating Excel service...");

        Workbook workbook = WorkbookFactory.create(new File(fileService.getInputFile()));
        workbook.forEach(sheet -> {
            if (sheet.getSheetName().equalsIgnoreCase("Tabelle1")) {
                mainInputDataSheet = sheet;
            }
        });

        if (mainInputDataSheet == null) {
            throw new IOException("The given input files does not contain a sheet matching \"Tabelle1\".");
        }

        Row headerRow = mainInputDataSheet.getRow(0);
        headerRow.forEach(cell -> tableColumnList.add(new TableColumn(cell.getColumnIndex(), cell.getStringCellValue())));
        logger.debug("Total Columns found: {}", tableColumnList.size());

        logger.info("Excel service initiated.");
    }

    public Cell getCell(String columnName, int rowIndex) throws RequestedCellOutsideOfDefinedAreaException {
        Optional<TableColumn> columnOptional = tableColumnList.stream().
                filter(tableColumn -> tableColumn.getColumnName().equals(columnName)).findFirst();

        if (columnOptional.isPresent()) {
            TableColumn tableColumn = columnOptional.get();

            return mainInputDataSheet.getRow(rowIndex).getCell(tableColumn.getCellIndex());
        } else {
            throw new RequestedCellOutsideOfDefinedAreaException(
                    "Requested position does not exist: columnName=" + columnName + " , rowIndex=" + rowIndex);
        }
    }

    public int getLastRowIndex() {
        return mainInputDataSheet.getLastRowNum();
    }

    public String getCellValueAsString(Cell cell) {
        String returnValue;
        try {
            returnValue = cell.getStringCellValue();
        } catch (IllegalStateException exception) {
            DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
            returnValue = formatter.formatCellValue(cell);
        } catch (NullPointerException exception) {
            returnValue = "";
        }
        return returnValue;
    }
}
