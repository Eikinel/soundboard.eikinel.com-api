package com.eikinel.soundboard.button.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ButtonNotFoundException extends ResponseStatusException {
    public ButtonNotFoundException(HttpStatus status) { super(status); }
    public ButtonNotFoundException(HttpStatus status, String message) { super(status, message); }
    public ButtonNotFoundException(HttpStatus status, String message, Throwable cause) { super(status, message, cause); }
}
