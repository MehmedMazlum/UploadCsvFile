package com.restapi.parsecsv.exception;

public class CsvGetFileException extends Exception{
    public CsvGetFileException(String errorMessage,Throwable err) {
        super(errorMessage,err);
    }

    public CsvGetFileException(String errorMessage) {
        super(errorMessage);
    }
}
