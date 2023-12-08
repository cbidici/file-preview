package com.cbidici.filepreview.service.initializer;

import com.cbidici.filepreview.config.AppConfig;
import com.cbidici.filepreview.model.domain.ResourceDomain;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order(1)
public class UrlInitializer implements ResourceInitializer {

  @Override
  public void init(List<ResourceDomain> resources) {
    resources.forEach(this::init);
  }

  private void init(ResourceDomain resource) {
    resource.getAttributes().put("url", url(resource.getPath()));
  }

  private String url(String path) {
    return Path.of("/").resolve(AppConfig.FILE_URL_PATH).resolve(path).toString();
  }
}
