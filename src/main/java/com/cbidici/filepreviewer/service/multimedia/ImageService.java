package com.cbidici.filepreviewer.service.multimedia;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ImageService {

  public void resize(Path sourceImagePath, Path targetImagePath, Dimension dimension) {
    try {
      IMOperation op = new IMOperation();
      op.autoOrient();
      op.addImage(sourceImagePath.toString());
      op.resize(dimension.width, dimension.height);
      op.addImage("jpeg:" + targetImagePath.toString());
      ConvertCmd convert = new ConvertCmd();
      convert.run(op);
    } catch (IOException | InterruptedException | IM4JavaException e) {
      throw new MultimediaServiceBusinessException(e);
    }
  }

  public void resizeSave(BufferedImage image, Dimension dimension, Path targetPath) {
    try {
      var resizedImage = resize(image, dimension);
      String extension = targetPath.toString().substring(targetPath.toString().lastIndexOf(".")+1);
      ImageIO.write(resizedImage, extension, targetPath.toFile());
    } catch (IOException ex) {
      throw new MultimediaServiceBusinessException(
          String.format("Could not resize image and save image to %s", targetPath), ex);
    }

  }

  private BufferedImage resize(BufferedImage image, Dimension targetDimension) {
    Dimension originalDimension = new Dimension(image.getWidth(), image.getHeight());
    Dimension scaledDimension = getScaledDimension(originalDimension, targetDimension);
    BufferedImage resizedImage = new BufferedImage(scaledDimension.width, scaledDimension.height, image.getType());
    Graphics2D graphics2D = resizedImage.createGraphics();
    graphics2D.drawImage(image, 0, 0, scaledDimension.width, scaledDimension.height, null);
    graphics2D.dispose();
    return resizedImage;
  }

  // TODO : Check that is this the only way to keep aspect ratio
  private Dimension getScaledDimension(Dimension imageSize, Dimension boundary) {
    double widthRatio = boundary.getWidth() / imageSize.getWidth();
    double heightRatio = boundary.getHeight() / imageSize.getHeight();
    double ratio = Math.min(widthRatio, heightRatio);
    return new Dimension((int) (imageSize.width  * ratio),
        (int) (imageSize.height * ratio));
  }
}
