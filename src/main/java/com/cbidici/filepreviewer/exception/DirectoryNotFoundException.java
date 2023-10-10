package com.cbidici.filepreviewer.exception;

public class DirectoryNotFoundException extends MultimediaServiceBusinessException {

    public DirectoryNotFoundException(String path) {
        super(String.format("Directory Not Found %s", path));
    }
}
