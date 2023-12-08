package com.cbidici.filepreview.exception;

public class FileNotFoundException extends FilePreviewServiceBusinessException {

    public FileNotFoundException(String path) {
        super(String.format("File Not Found %s", path));
    }
}
