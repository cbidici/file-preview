package com.cbidici.filepreviewer.exception;

public class VideoProcessingException extends MultimediaServiceBusinessException {
    public VideoProcessingException(String path, Throwable t) {
        super(String.format("Video can not be processed %s", path), t);
    }
}
