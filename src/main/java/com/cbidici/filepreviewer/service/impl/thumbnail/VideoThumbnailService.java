package com.cbidici.filepreviewer.service.impl.thumbnail;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Set;

@Service
public class VideoThumbnailService extends ThumbnailService {

    @Autowired
    public VideoThumbnailService(FileService fileService) {
        super(fileService);
    }

    @Override
    public BufferedImage generateThumbnail(String sourceFilePath) {
        BufferedImage result;
        try{
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(Path.of(fileService.getAbsolutePathOf(sourceFilePath)).toFile());
            grabber.setFormat("mp4");
            grabber.start();
            Frame frame = grabber.grabImage();

            Java2DFrameConverter converter = new Java2DFrameConverter();
            result = converter.getBufferedImage(frame);
            grabber.stop();
        } catch (Exception e){
            throw new MultimediaServiceBusinessException(e);
        }
        return result;
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.VIDEO_MP4);
    }
}
