package com.smartheusys.eurochem.excel;

import lombok.Data;

/**
 * This class represents a column of an input excel sheet. The first line is always the header.
 * As we don't know if the table structure of the WERCS export will ever change, we want to be flexible.
 * This means that we create Column objects and then use them to read out the data. This way we are
 * independent of the column order and the other columns.
 */
@Data
public class TableColumn {
    private final int cellIndex;
    private final String columnName;
}
