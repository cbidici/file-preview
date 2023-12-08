package com.cbidici.filepreview.exception;

public class DirectoryNotFoundException extends FilePreviewServiceBusinessException {

    public DirectoryNotFoundException(String path) {
        super(String.format("Directory Not Found %s", path));
    }
}
