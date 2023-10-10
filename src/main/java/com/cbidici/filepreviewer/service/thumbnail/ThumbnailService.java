package com.cbidici.filepreviewer.service.thumbnail;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.FileService;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Set;

public abstract class ThumbnailService {

  private final AppConfig appConfig;
  protected final FileService fileService;
  protected final Dimension thumbnailDimension;

  public ThumbnailService(AppConfig appConfig, FileService fileService) {
    this.appConfig = appConfig;
    this.fileService = fileService;
    this.thumbnailDimension = new Dimension(appConfig.getThumbnailWidth(), appConfig.getThumbnailWidth());
  }

  public void generate(ResourceDomain resource) {
    if(fileService.findFile(getThumbnailPath(resource)).isEmpty()) {
      fileService.createDirectories(Path.of(getThumbnailPath(resource)).getParent().toString());
      generateThumbnail(resource);
    }
  }

  public abstract Set<ResourceType> getSupportedTypes();

  protected abstract void generateThumbnail(ResourceDomain resource);

  public String getThumbnailPath(ResourceDomain resource) {
    return Path.of(appConfig.getThumbnailDirectory()).resolve(resource.getPath())+".jpg";
  }
}
