package com.lolchievement.clients.exceptions;

public class PlayerClientException extends RuntimeException {
    public PlayerClientException(String message,  Throwable cause) {
        super(message, cause);
    }
}
