package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order(2)
@Slf4j
public class ThumbnailInitializer implements ResourceInitializer{

  @Override
  public void init(List<ResourceDomain> resources) {
    resources.forEach(this::init);
  }

  private void init(ResourceDomain resource) {
    resource.getAttributes().put("thumbnailUrl", thumbnailUrl(resource.getPath()));
  }

  private String thumbnailUrl(String path) {
    return Path.of("/").resolve(AppConfig.THUMBNAILS).resolve(path).toString();
  }
}
