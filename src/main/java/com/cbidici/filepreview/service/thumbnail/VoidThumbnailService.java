package com.cbidici.filepreview.service.thumbnail;

import com.cbidici.filepreview.config.AppConfig;
import com.cbidici.filepreview.model.domain.ResourceDomain;
import com.cbidici.filepreview.model.enm.ResourceType;
import com.cbidici.filepreview.service.FileService;
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
