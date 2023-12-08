package com.cbidici.filepreview.service.thumbnail;

import com.cbidici.filepreview.config.AppConfig;
import com.cbidici.filepreview.model.domain.ResourceDomain;
import com.cbidici.filepreview.model.enm.ResourceType;
import com.cbidici.filepreview.service.FileService;
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
    this.thumbnailDimension = new Dimension(appConfig.getThumbnailsWidth(), appConfig.getThumbnailsWidth());
  }

  public void generate(ResourceDomain resource) {
    if(fileService.findFile(Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.THUMBNAILS).resolve(resource.getPath()+".jpg")).isEmpty()) {
      fileService.createDirectories(Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.THUMBNAILS).resolve(resource.getPath()+".jpg").getParent());
      generateThumbnail(resource);
    }
  }

  public abstract Set<ResourceType> getSupportedTypes();

  protected abstract void generateThumbnail(ResourceDomain resource);
}
