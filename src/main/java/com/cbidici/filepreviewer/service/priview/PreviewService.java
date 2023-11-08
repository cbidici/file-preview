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
    this.previewDimension = new Dimension(appConfig.getPreviewsWidth(), appConfig.getPreviewsWidth());
  }

  public void generate(ResourceDomain resource) {
    if(fileService.findFile(Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.PREVIEWS).resolve(resource.getPath()+".jpg")).isEmpty()) {
      fileService.createDirectories(Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.PREVIEWS).resolve(resource.getPath()+".jpg").getParent());
      generatePreview(resource);
    }
  }

  public abstract Set<ResourceType> getSupportedTypes();

  protected abstract void generatePreview(ResourceDomain resource);
}
