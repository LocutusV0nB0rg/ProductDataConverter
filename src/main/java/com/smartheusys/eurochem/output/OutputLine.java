package com.smartheusys.eurochem.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * As we don't know if the composition of the fertilisers will ever change, we want to be flexible.
 * This means in this case that we have a map of values. Certain stuff is fixed, like the product number,
 * but all data contents of the fertiliser will be flexible.
 */
@Data
public class OutputLine {
    private String fProduct;
    private List<OutputLine.Column> values = new ArrayList<>();

    @Data
    public static class Column {
        private final String columnName;
        private final String columnUnit;
        private final String columnValue;
    }
}
