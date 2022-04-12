package com.example.project_final_2.exception;

public class DataNotFoundException extends RuntimeException {

    private int responseCode;

    public DataNotFoundException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
