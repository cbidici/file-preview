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
@Order(3)
@RequiredArgsConstructor
@Slf4j
public class PreviewInitializer implements ResourceInitializer {

  @Override
  public void init(List<ResourceDomain> resources) {
    resources.forEach(this::init);
  }

  private void init(ResourceDomain resource) {
    resource.getAttributes().put("previewUrl", previewUrl(resource.getPath()));
  }

  private String previewUrl(String path) {
    return Path.of("/").resolve(AppConfig.PREVIEWS).resolve(path).toString();
  }
}
