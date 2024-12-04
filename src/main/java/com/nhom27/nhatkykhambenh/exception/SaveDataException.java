package com.nhom27.nhatkykhambenh.exception;

public class SaveDataException extends RuntimeException {
    public SaveDataException(String message) {
        super(message);
    }

    public SaveDataException(String message, Throwable cause) { // Constructor mới
        super(message, cause);
    }
}

