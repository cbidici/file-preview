package com.cbidici.filepreview.exception;

public class FilePreviewServiceBusinessException extends RuntimeException{
    public FilePreviewServiceBusinessException(String message) {
        super(message);
    }

    public FilePreviewServiceBusinessException(Throwable t) {
        super(t);
    }

    public FilePreviewServiceBusinessException(String message, Throwable t) {
        super(message, t);
    }
}
