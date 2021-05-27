package com.bankservice.app.exceptions;

public enum ExceptionsCode {

    ARGUMENTO_INVALIDO("E-001", "Invalid argument");

    private final String code;
    private final String description;

    ExceptionsCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
