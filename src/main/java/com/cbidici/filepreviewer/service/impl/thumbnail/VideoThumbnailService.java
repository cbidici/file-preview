package com.cbidici.filepreviewer.service.impl.thumbnail;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Optional;
import java.util.Set;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.bytedeco.opencv.global.opencv_core.*;
import static org.bytedeco.opencv.global.opencv_imgproc.cvResize;

@Service
public class VideoThumbnailService extends ThumbnailService {

    @Autowired
    public VideoThumbnailService(int thumbnailWidth, FileService fileService) {
        super(thumbnailWidth, fileService);
    }

    @Override
    public BufferedImage generateThumbnail(String sourceFilePath) {
        BufferedImage result;
        try{
            FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(Path.of(fileService.getAbsolutePathOf(sourceFilePath)).toFile());
            grabber.setFormat("mp4");
            grabber.start();

            Frame frame = grabber.grabImage();

            OpenCVFrameConverter.ToIplImage ocvConverter = new OpenCVFrameConverter.ToIplImage();
            IplImage grabbedImage = ocvConverter.convertToIplImage(frame);
            IplImage resizedImage = resize(grabbedImage, thumbnailWidth);
            IplImage rotatedImage = rotate(resizedImage, Integer.parseInt(Optional.ofNullable(grabber.getVideoMetadata("rotate")).orElse("0")));

            Java2DFrameConverter j2converter = new Java2DFrameConverter();
            result = j2converter.getBufferedImage(ocvConverter.convert(rotatedImage));
            grabbedImage.release();
            resizedImage.release();
            rotatedImage.release();
            grabber.release();
            grabber.stop();
        } catch (Exception e){
            throw new MultimediaServiceBusinessException(e);
        }
        return result;
    }

    public IplImage rotate(IplImage src, int angle) {
        IplImage img;
        for(int i=0; i<angle/90; i++){
            img = IplImage.create(src.height(), src.width(), src.depth(), src.nChannels());
            cvTranspose(src, img);
            src = img;
        }

        if(angle == 90) {
            cvFlip(src, src, 1);
        }

        if(angle == 180) {
            cvFlip(src, src, 0);
        }

        if(angle == 270) {
            cvFlip(src, src, -1);
        }

        return src;
    }

    public IplImage resize(IplImage src, int maxSize) {
        double origWidth = src.width();
        double origHeight = src.height();
        double aspectRatio = origWidth/origHeight;

        int newWidth;
        int newHeight;

        if(origHeight>origWidth) {
            newHeight = maxSize;
            newWidth = (int) (newHeight*aspectRatio);
        }
        else if(origHeight< origWidth){
            newWidth = maxSize;
            newHeight = (int) (newWidth/aspectRatio);
        }
        else {
            newWidth = maxSize;
            newHeight = maxSize;
        }

        IplImage img = IplImage.create(newWidth, newHeight, src.depth(), src.nChannels());
        cvResize(src, img);
        return img;
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.VIDEO_MP4);
    }
}
