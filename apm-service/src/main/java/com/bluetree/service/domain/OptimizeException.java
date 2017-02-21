package com.bluetree.service.domain;

public class OptimizeException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public OptimizeException(String message) {
	super(message);
    }

    public OptimizeException(Throwable cause) {
	super(cause);
    }

    public OptimizeException(String message, Throwable cause) {
	super(message, cause);
    }

}
