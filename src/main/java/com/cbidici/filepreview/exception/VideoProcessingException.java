package com.cbidici.filepreview.exception;

public class VideoProcessingException extends FilePreviewServiceBusinessException {
    public VideoProcessingException(String path, Throwable t) {
        super(String.format("Video can not be processed %s", path), t);
    }
}
