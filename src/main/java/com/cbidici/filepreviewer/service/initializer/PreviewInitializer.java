package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.service.priview.PreviewServiceFactory;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

@Service
@Order(3)
@RequiredArgsConstructor
@Slf4j
public class PreviewInitializer implements ResourceInitializer {

  private final PreviewServiceFactory factory;
  private final ThreadPoolTaskExecutor taskExecutor;
  @Override
  public void init(List<ResourceDomain> resources) {
    resources.forEach(this::init);
  }

  private void init(ResourceDomain resource) {
    var service = factory.getService(resource.getType());
    if(service.isPresent()) {
      taskExecutor.execute(() -> service.get().generate(resource));
      resource.getAttributes().put("previewUrl", previewUrl(resource.getPath()));
    }
  }

  private String previewUrl(String path) {
    return Path.of("/").resolve(AppConfig.FILE_URL_PATH).resolve(path).resolve("preview").toString();
  }
}
