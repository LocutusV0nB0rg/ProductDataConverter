package com.smartheusys.eurochem.output;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smartheusys.eurochem.FileService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
@RequiredArgsConstructor(onConstructor__ = @Inject)
public class OutputLineExcelRepository implements OutputLineRepository {
    private final Logger logger;
    private final FileService fileService;

    private final String OUTPUT_SHEET_NAME = "Products";

    private Workbook workbook = null;

    // We have to start at 2 since we have a header and the unit row.
    private int rowIndex = 2;

    private List<ColumnCombination> uniqueCombinations = new ArrayList<>();

    @Override
    public void saveOutputLines(List<OutputLine> outputLines) {
        createWorksheetIfNotExists(outputLines);
        outputLines.forEach(this::addLineToSheet);
        autoformatWorkbook();
        System.out.println("Added all to sheet. Now writing...");
        saveWorksheetToFile();
        System.out.println("Finished writing to file.");
    }

    private void autoformatWorkbook() {
        Sheet sheet = workbook.getSheet(OUTPUT_SHEET_NAME);
        for (int i = 0; i < uniqueCombinations.size() + 1; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void saveWorksheetToFile() {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileService.getOutputFile());
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void addLineToSheet(OutputLine outputLine) {
        Row newRow = workbook.getSheet(OUTPUT_SHEET_NAME).createRow(rowIndex);

        Cell fProductCell = newRow.createCell(0);
        fProductCell.setCellValue(outputLine.getFProduct());

        for (OutputLine.Column column : outputLine.getValues()) {
            ColumnCombination columnCombination = uniqueCombinations.stream()
                    .filter(combination ->
                            combination.getColumnName().equals(column.getColumnName()) &&
                                    combination.getColumnUnit().equals(column.getColumnUnit()))
                    .findFirst()
                    .orElse(null);

            Cell newCell = newRow.createCell(columnCombination.getIndex());
            newCell.setCellValue(column.getColumnValue());
        }
        rowIndex++;
    }

    private void createWorksheetIfNotExists(List<OutputLine> outputLines) {


        if (workbook != null) {
            return;
        }

        workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet(OUTPUT_SHEET_NAME);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 10);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

        CellStyle unitCellStyle = workbook.createCellStyle();
        unitCellStyle.setFont(headerFont);
        unitCellStyle.setFillBackgroundColor(IndexedColors.GREY_25_PERCENT.getIndex());

        Row headerRow = sheet.createRow(0);
        uniqueCombinations = outputLines.stream()
                .flatMap(outputLine -> outputLine.getValues().stream())
                .map(column -> new ColumnCombination(column.getColumnName(), column.getColumnUnit()))
                .distinct()
                .sorted(Comparator.comparing(ColumnCombination::getColumnName)
                        .thenComparing(ColumnCombination::getColumnUnit))// Sorting by name, then unit
                .collect(Collectors.toList());

        Cell fProductCell = headerRow.createCell(0);
        fProductCell.setCellValue("F_PRODUCT");
        fProductCell.setCellStyle(headerCellStyle);

        int index = 1;
        for (ColumnCombination uniqueCombination : uniqueCombinations) {
            Cell cell = headerRow.createCell(index);
            cell.setCellValue(uniqueCombination.getColumnName());
            cell.setCellStyle(headerCellStyle);

            uniqueCombination.setIndex(index);

            index++;
        }

        Row unitRow = sheet.createRow(1);
        index = 1;
        for (ColumnCombination uniqueCombination : uniqueCombinations) {
            Cell cell = unitRow.createCell(index);
            cell.setCellValue(uniqueCombination.getColumnUnit());
            cell.setCellStyle(unitCellStyle);
            index++;
        }
    }

    @Data
    static class ColumnCombination {
        private final String columnName;
        private final String columnUnit;

        private Integer index;
    }
}