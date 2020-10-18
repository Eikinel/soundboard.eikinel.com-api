package com.eikinel.soundboard.button.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TagFieldNotFoundException extends ResponseStatusException {
    public TagFieldNotFoundException(HttpStatus status) { super(status); }
    public TagFieldNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
    public TagFieldNotFoundException(HttpStatus status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
