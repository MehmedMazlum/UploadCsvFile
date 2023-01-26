package com.restapi.parsecsv.exception;

public class CsvDeleteFileException extends Exception {
    public CsvDeleteFileException(String errorMessage ,Throwable err) {
        super(errorMessage,err);
    }
}
