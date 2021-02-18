package com.cbidici.filepreviewer.service.impl.thumbnail;

import java.awt.image.BufferedImage;
import java.util.Set;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.bytedeco.opencv.global.opencv_imgproc.cvResize;
import static org.bytedeco.opencv.global.opencv_imgcodecs.cvLoadImage;

@Service
public class ImageThumbnailService extends ThumbnailService {

    @Autowired
    public ImageThumbnailService(int thumbnailWidth, FileService fileService) {
        super(thumbnailWidth, fileService);
    }


    @Override
    public BufferedImage generateThumbnail(String path) {
        try {
            IplImage img = cvLoadImage(fileService.getAbsolutePathOf(path));
            IplImage resizedImage = resize(img, thumbnailWidth);
            OpenCVFrameConverter.ToIplImage ocvConverter = new OpenCVFrameConverter.ToIplImage();
            Java2DFrameConverter j2converter = new Java2DFrameConverter();
            return j2converter.getBufferedImage(ocvConverter.convert(resizedImage));
        }
        catch (Exception ex) {
            throw new MultimediaServiceBusinessException(ex);
        }

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
        return Set.of(FileType.IMAGE_JPEG);
    }
}
