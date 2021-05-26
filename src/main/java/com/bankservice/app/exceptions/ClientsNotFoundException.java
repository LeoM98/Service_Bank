package com.bankservice.app.exceptions;

public class ClientsNotFoundException extends RuntimeException{
    public ClientsNotFoundException(String message) {
        super(message);
    }
}
