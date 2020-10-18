package com.eikinel.soundboard.button.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TagNotFoundException extends ResponseStatusException {
    public TagNotFoundException(HttpStatus status) { super(status); }
    public TagNotFoundException(HttpStatus status, String message) { super(status, message); }
    public TagNotFoundException(HttpStatus status, String message, Throwable cause) { super(status, message, cause); }

    public TagNotFoundException() {
        super(HttpStatus.NOT_FOUND);
    }
}
