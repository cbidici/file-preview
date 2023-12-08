package com.cbidici.filepreview.service.priview;

import com.cbidici.filepreview.config.AppConfig;
import com.cbidici.filepreview.model.domain.ResourceDomain;
import com.cbidici.filepreview.model.enm.ResourceType;
import com.cbidici.filepreview.service.FileService;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class VoidPreviewService extends PreviewService {

  public VoidPreviewService(AppConfig appConfig, FileService fileService) {
    super(appConfig, fileService);
  }

  @Override
  public Set<ResourceType> getSupportedTypes() {
    return Set.of(ResourceType.DIRECTORY, ResourceType.VIDEO_QUICKTIME, ResourceType.VIDEO_MP4);
  }

  @Override
  protected void generatePreview(ResourceDomain resource) {
  }
}
