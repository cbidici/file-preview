package com.cbidici.filepreviewer.service.impl;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.service.ImageService;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

import static org.bytedeco.opencv.global.opencv_imgproc.cvResize;

@Service
public class DefaultImageService implements ImageService {

    @Override
    public BufferedImage getMaxSized(BufferedImage originalImage, int maxWidth, int maxHeight) {
        try {
            OpenCVFrameConverter.ToIplImage ocvConverter = new OpenCVFrameConverter.ToIplImage();
            Java2DFrameConverter j2converter = new Java2DFrameConverter();
            IplImage img = ocvConverter.convert(j2converter.getFrame(originalImage));
            IplImage resizedImage = resizeToMax(img, maxWidth, maxHeight);
            return j2converter.getBufferedImage(ocvConverter.convert(resizedImage));
        } catch (Exception ex) {
            throw new MultimediaServiceBusinessException(ex);
        }
    }

    private IplImage resizeToMax(IplImage src, int maxWidth, int maxHeight) {
        double aspectRatio = src.width() / (double) src.height();

        int newWidth = src.width();
        int newHeight = src.height();

        if (newWidth > maxWidth) {
            newWidth = maxWidth;
            newHeight = (int) (newWidth / aspectRatio);
        }
        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) (newHeight * aspectRatio);
        }

        IplImage img = IplImage.create(newWidth, newHeight, src.depth(), src.nChannels());
        cvResize(src, img);
        return img;
    }
}
