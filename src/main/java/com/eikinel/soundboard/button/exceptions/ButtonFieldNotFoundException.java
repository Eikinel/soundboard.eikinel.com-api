package com.eikinel.soundboard.button.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ButtonFieldNotFoundException extends ResponseStatusException {
    public ButtonFieldNotFoundException(HttpStatus status) { super(status); }
    public ButtonFieldNotFoundException(HttpStatus status, String message) {
        super(status, message);
    }
    public ButtonFieldNotFoundException(HttpStatus status, String message, Throwable cause) {
        super(status, message, cause);
    }
}
