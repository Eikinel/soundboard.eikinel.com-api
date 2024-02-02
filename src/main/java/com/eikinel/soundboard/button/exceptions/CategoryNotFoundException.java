package com.eikinel.soundboard.button.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryNotFoundException extends ResponseStatusException {
	public CategoryNotFoundException(HttpStatus status) {
		super(status);
	}

	public CategoryNotFoundException(HttpStatus status, String message) {
		super(status, message);
	}

	public CategoryNotFoundException(HttpStatus status, String message, Throwable cause) {
		super(status, message, cause);
	}

	public CategoryNotFoundException() {
		super(HttpStatus.NOT_FOUND);
	}
}
