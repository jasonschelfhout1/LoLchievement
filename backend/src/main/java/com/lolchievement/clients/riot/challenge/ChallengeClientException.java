package com.lolchievement.clients.riot.challenge;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChallengeClientException extends RuntimeException {
    private final String operation;
    private final HttpStatus httpStatus;

    public ChallengeClientException(String message, Throwable cause) {
        this(message, cause, null, null);
    }

    public ChallengeClientException(String message, Throwable cause, String operation, HttpStatus httpStatus) {
        super(message, cause);
        this.operation = operation;
        this.httpStatus = httpStatus;
    }
}