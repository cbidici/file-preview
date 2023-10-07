package com.cbidici.filepreviewer.exception;

public class FileEntityNotFoundException extends MultimediaServiceBusinessException {

    public FileEntityNotFoundException(String path) {
        super(path);
    }

    public FileEntityNotFoundException(Throwable t) {
        super(t);
    }
}
