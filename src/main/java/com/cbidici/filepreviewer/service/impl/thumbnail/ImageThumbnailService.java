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
import java.io.IOException;
import java.util.Set;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageThumbnailService extends ThumbnailService {

  private final Dimension dimension;

  @Autowired
  public ImageThumbnailService(AppConfig appConfig, FileService fileService) {
    super(appConfig.getThumbnailWidth(), fileService);
    dimension = new Dimension(thumbnailWidth, thumbnailWidth);
  }

  @Override
  public ThumbnailDomain getThumbnail(String path) {
    String thumbnailPath = fileService.getThumbnailPath(path+".jpg");

    FileDomain thumbnailFile;
    try {
      thumbnailFile = fileService.getFile(thumbnailPath);
    } catch (FileEntityNotFoundException e) {
      thumbnailFile = generateAndSaveThumbnail(path, thumbnailPath);
    }

    return ThumbnailDomain.builder().file(thumbnailFile).build();
  }

  @Override
  public void generateThumbnail(String path, String targetPath) {
    try {
      IMOperation op = new IMOperation();
      op.autoOrient();
      op.addImage(fileService.getAbsolutePathOf(path));
      op.resize(dimension.width, dimension.height);
      op.addImage("jpeg:"+fileService.getAbsolutePathOf(targetPath));
      ConvertCmd convert = new ConvertCmd();
      convert.run(op);
    } catch (IOException | InterruptedException | IM4JavaException e) {
      throw new MultimediaServiceBusinessException(e);
    }
  }

  @Override
  public Set<FileType> getSupportedTypes() {
    return Set.of(FileType.IMAGE_HEIC, FileType.IMAGE_JPEG, FileType.IMAGE_PNG, FileType.ARW);
  }
}
