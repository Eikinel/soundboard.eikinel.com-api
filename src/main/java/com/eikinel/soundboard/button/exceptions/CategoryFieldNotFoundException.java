package com.eikinel.soundboard.button.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CategoryFieldNotFoundException extends ResponseStatusException {
	public CategoryFieldNotFoundException(HttpStatus status) {
		super(status);
	}

	public CategoryFieldNotFoundException(HttpStatus status, String message) {
		super(status, message);
	}

	public CategoryFieldNotFoundException(HttpStatus status, String message, Throwable cause) {
		super(status, message, cause);
	}
}
