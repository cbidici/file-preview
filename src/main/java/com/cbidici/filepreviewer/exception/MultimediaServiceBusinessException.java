package com.cbidici.filepreviewer.exception;

public class MultimediaServiceBusinessException extends RuntimeException{
    public MultimediaServiceBusinessException() {
        super();
    }

    public MultimediaServiceBusinessException(String message) {
        super(message);
    }

    public MultimediaServiceBusinessException(Throwable t) {
        super(t);
    }
}
