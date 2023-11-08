package com.cbidici.filepreviewer.service.thumbnail;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.multimedia.ImageService;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

import static com.cbidici.filepreviewer.model.enm.ResourceType.*;

@Service
public class ImageThumbnailService extends ThumbnailService {
  private final ImageService imageService;
  private final AppConfig appConfig;

  public ImageThumbnailService(
      AppConfig appConfig, FileService fileService, ImageService imageService
  ) {
    super(appConfig, fileService);
    this.appConfig = appConfig;
    this.imageService = imageService;
  }

  @Override
  public Set<ResourceType> getSupportedTypes() {
    return Set.of(IMAGE_ARW, IMAGE_JPEG, IMAGE_PNG, IMAGE_HEIC, IMAGE_GIF);
  }

  @Override
  protected void generateThumbnail(ResourceDomain resource) {
    imageService.resize(
        Path.of(appConfig.getFilesPath()).resolve(resource.getPath()),
        Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.THUMBNAILS).resolve(resource.getPath()+".jpg"),
        thumbnailDimension
    );
  }
}
