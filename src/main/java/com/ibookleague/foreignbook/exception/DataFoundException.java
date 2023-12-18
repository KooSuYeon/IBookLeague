package com.ibookleague.foreignbook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataFoundException extends RuntimeException {
    private static final long serialVersionID = 1L;
    public DataFoundException(String message) {
        super(message);
    }
}
