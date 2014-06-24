package com.ff.dao.exception;

public final class AuthException extends RuntimeException {

	private static final long serialVersionUID = -1034854800437619086L;

	public AuthException(final String message) {
		super(message);
	}

	public AuthException(final Exception e) {
		super(e.getClass() + ": " + e.getMessage());
	}

}
