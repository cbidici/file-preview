package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.service.priview.PreviewServiceFactory;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Order(3)
@RequiredArgsConstructor
@Slf4j
public class PreviewInitializer implements ResourceInitializer, PreInitializer {

  private final PreviewServiceFactory factory;
  private final TaskExecutor executor;

  @Override
  public void init(List<ResourceDomain> resources) {
    resources.forEach(resource -> resource.getAttributes().put("previewUrl", previewUrl(resource.getPath())));
    resources.forEach(resource -> executor.execute(() -> generate(resource)));
  }

  private void generate(ResourceDomain resource) {
    var service = factory.getService(resource.getType());
    if(service.isPresent()) {
      service.get().generate(resource);
    } else {
      log.warn("Preview service not found for file {} with type {}", resource.getPath(), resource.getType());
    }
  }

  private String previewUrl(String path) {
    return Path.of("/").resolve(AppConfig.PREVIEWS).resolve(path).toString();
  }

  @Override
  public void preInit(List<ResourceDomain> resources) {
    init(resources);
  }
}
