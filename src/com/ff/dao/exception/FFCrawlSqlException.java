package com.ff.dao.exception;

public class FFCrawlSqlException extends RuntimeException {
	private static final long serialVersionUID = -5779597861635131561L;

	public FFCrawlSqlException(final String message) {
		super(message);
	}

	public FFCrawlSqlException(final Exception e) {
		super(e.getClass() + ": " + e.getMessage());
	}

}
