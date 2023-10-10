package com.cbidici.filepreviewer.exception;

public class MultimediaServiceBusinessException extends RuntimeException{
    public MultimediaServiceBusinessException(String message) {
        super(message);
    }

    public MultimediaServiceBusinessException(Throwable t) {
        super(t);
    }

    public MultimediaServiceBusinessException(String message, Throwable t) {
        super(message, t);
    }
}
