package com.smartheusys.eurochem.excel;

public class RequestedCellOutsideOfDefinedAreaException extends RuntimeException {
    public RequestedCellOutsideOfDefinedAreaException(String message) {
        super(message);
    }
}
