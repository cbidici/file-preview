package com.cbidici.filepreviewer.service.priview;

import static com.cbidici.filepreviewer.model.enm.ResourceType.IMAGE_ARW;
import static com.cbidici.filepreviewer.model.enm.ResourceType.IMAGE_GIF;
import static com.cbidici.filepreviewer.model.enm.ResourceType.IMAGE_HEIC;
import static com.cbidici.filepreviewer.model.enm.ResourceType.IMAGE_JPEG;
import static com.cbidici.filepreviewer.model.enm.ResourceType.IMAGE_PNG;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.multimedia.ImageService;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class ImagePreviewService extends PreviewService{

  private final ImageService imageService;
  private final AppConfig appConfig;

  public ImagePreviewService(
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
  protected void generatePreview(ResourceDomain resource) {
    imageService.resize(
        Path.of(appConfig.getFilesPath()).resolve(resource.getPath()),
        Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.PREVIEWS).resolve(resource.getPath()+".jpg"),
        previewDimension
    );
  }
}
