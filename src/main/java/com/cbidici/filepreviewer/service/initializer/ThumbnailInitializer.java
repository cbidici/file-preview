package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.service.thumbnail.ThumbnailServiceFactory;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order(2)
public class ThumbnailInitializer implements ResourceInitializer{

  private final ThumbnailServiceFactory factory;

  @Override
  public void init(List<ResourceDomain> resources) {
    resources.forEach(this::init);
  }

  private void init(ResourceDomain resource) {
    var service = factory.getService(resource.getType());
    if(service.isPresent()) {
      service.get().generate(resource);
      resource.getAttributes().put("thumbnailUrl", previewUrl(resource.getPath()));
    }
  }

  private String previewUrl(String path) {
    return Path.of("/").resolve(AppConfig.FILE_URL_PATH).resolve(path).resolve("thumbnail").toString();
  }
}
