package com.cbidici.filepreviewer.service.thumbnail;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.FileService;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class VoidThumbnailService extends ThumbnailService {

  public VoidThumbnailService(AppConfig appConfig, FileService fileService) {
    super(appConfig, fileService);
  }

  @Override
  public Set<ResourceType> getSupportedTypes() {
    return Set.of(ResourceType.DIRECTORY);
  }

  @Override
  protected void generateThumbnail(ResourceDomain resource) {
  }
}
