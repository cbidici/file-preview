package com.cbidici.filepreviewer.exception;

public class FileNotFoundException extends MultimediaServiceBusinessException {

    public FileNotFoundException(String path) {
        super(String.format("File Not Found %s", path));
    }
}
