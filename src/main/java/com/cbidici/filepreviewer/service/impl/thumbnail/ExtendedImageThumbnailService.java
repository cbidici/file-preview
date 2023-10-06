package com.cbidici.filepreviewer.service.impl.thumbnail;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.ThumbnailDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import javax.imageio.ImageIO;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExtendedImageThumbnailService extends ThumbnailService {

  private final Dimension dimension;

  @Autowired
  public ExtendedImageThumbnailService(AppConfig appConfig, FileService fileService) {
    super(appConfig.getThumbnailWidth(), fileService);
    dimension = new Dimension(thumbnailWidth, thumbnailWidth);
  }

  @Override
  public ThumbnailDomain getThumbnail(String path) {
    var heicThumbnailPath = path.substring(0, path.lastIndexOf(".")) + ".heic.jpg";
    String thumbnailPath = fileService.getThumbnailPath(heicThumbnailPath);

    FileDomain thumbnailFile;
    try {
      thumbnailFile = fileService.getFile(thumbnailPath);
    } catch (FileEntityNotFoundException e) {
      thumbnailFile = generateAndSaveThumbnail(path, thumbnailPath);
    }

    return ThumbnailDomain.builder().file(thumbnailFile).build();
  }

  @Override
  public BufferedImage generateThumbnail(String path) {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    InputStream inputStream = null;
    try {
      try {
        inputStream = new FileInputStream(fileService.getAbsolutePathOf(path));
        IMOperation op = new IMOperation();
        Pipe pipeIn  = new Pipe(inputStream,null);
        Pipe pipeOut  = new Pipe(null, baos);
        op.addImage("-");
        op.resize(dimension.width, dimension.height);
        op.addImage("jpeg:-");
        ConvertCmd convert = new ConvertCmd();
        convert.setOutputConsumer(pipeOut);
        convert.setInputProvider(pipeIn);
        convert.run(op);
        baos.flush();
        byte[] bytes = baos.toByteArray();
        InputStream is = new ByteArrayInputStream(bytes);
        return ImageIO.read(is);
      } catch (IOException | InterruptedException | IM4JavaException e) {
        throw new MultimediaServiceBusinessException(e);
      } finally {
        baos.close();
        inputStream.close();
      }
    } catch (IOException ex) {
      throw new MultimediaServiceBusinessException(ex);
    }
  }

  @Override
  public Set<FileType> getSupportedTypes() {
    return Set.of(FileType.IMAGE_HEIC);
  }
}
