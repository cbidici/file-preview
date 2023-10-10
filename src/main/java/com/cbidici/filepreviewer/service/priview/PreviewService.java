package com.cbidici.filepreviewer.service.priview;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.FileService;
import java.awt.Dimension;
import java.nio.file.Path;
import java.util.Set;

public abstract class PreviewService {
  private final AppConfig appConfig;
  protected final FileService fileService;
  protected final Dimension previewDimension;

  public PreviewService(AppConfig appConfig, FileService fileService) {
    this.appConfig = appConfig;
    this.fileService = fileService;
    this.previewDimension = new Dimension(appConfig.getPreviewWidth(), appConfig.getPreviewWidth());
  }

  public void generate(ResourceDomain resource) {
    if(fileService.findFile(getPreviewPath(resource)).isEmpty()) {
      fileService.createDirectories(Path.of(getPreviewPath(resource)).getParent().toString());
      generatePreview(resource);
    }
  }

  public abstract Set<ResourceType> getSupportedTypes();

  protected abstract void generatePreview(ResourceDomain resource);

  public String getPreviewPath(ResourceDomain resource) {
    return Path.of(appConfig.getPreviewDirectory()).resolve(resource.getPath())+".jpg";
  }
}
