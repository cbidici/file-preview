package com.cbidici.filepreviewer.service.multimedia;

import com.cbidici.filepreviewer.exception.VideoProcessingException;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Optional;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.springframework.stereotype.Service;

import static org.bytedeco.opencv.global.opencv_core.cvFlip;
import static org.bytedeco.opencv.global.opencv_core.cvTranspose;


@Service
public class VideoService {

  public BufferedImage getFirstFrame(Path videoPath) {
    BufferedImage result;
    try(FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath.toFile())) {
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
      throw new VideoProcessingException(videoPath.toString(), e);
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
