package com.cbidici.filepreviewer.service.impl;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.service.VideoService;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import static org.bytedeco.opencv.global.opencv_core.cvFlip;
import static org.bytedeco.opencv.global.opencv_core.cvTranspose;

@Service
public class DefaultVideoService implements VideoService {

    @Override
    public BufferedImage getFirstFrame(File file) {
        BufferedImage result;
        try(FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(file)) {
            grabber.setFormat("mp4");
            grabber.start();
            try(
                Frame frame = grabber.grabImage();
                OpenCVFrameConverter.ToIplImage ocvConverter = new OpenCVFrameConverter.ToIplImage();
                IplImage grabbedImage = ocvConverter.convertToIplImage(frame);
                IplImage rotatedImage = rotate(grabbedImage, getRotate(grabber));
                Java2DFrameConverter j2converter = new Java2DFrameConverter()
            ) {
                result = j2converter.getBufferedImage(ocvConverter.convert(rotatedImage));
            }
        } catch (Exception e){
            throw new MultimediaServiceBusinessException(e);
        }
        return result;
    }

    private int getRotate(FFmpegFrameGrabber grabber) {
        return Optional.ofNullable(grabber.getVideoMetadata("rotate"))
            .map(Integer::parseInt)
            .orElse(Double.valueOf(grabber.getDisplayRotation()).intValue() * -1);
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
}
