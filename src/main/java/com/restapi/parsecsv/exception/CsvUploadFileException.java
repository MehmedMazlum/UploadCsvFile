package com.restapi.parsecsv.exception;

public class CsvUploadFileException extends Exception {
    public CsvUploadFileException(String errorMessage,Throwable err) {
        super(errorMessage,err);
    }
}
