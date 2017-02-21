package com.bluetree.service.domain;

public class PreviewException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public PreviewException(String message) {
	super(message);
    }

    public PreviewException(Throwable cause) {
	super(cause);
    }

    public PreviewException(String message, Throwable cause) {
	super(message, cause);
    }

}
