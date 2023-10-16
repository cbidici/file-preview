package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.service.thumbnail.ThumbnailServiceFactory;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Order(2)
@Slf4j
public class ThumbnailInitializer implements ResourceInitializer{
  private final ThumbnailServiceFactory factory;
  private final ThumbnailInitializerThreadPoolExecutor executor;

  @Override
  public void init(List<ResourceDomain> resources) {
    try {
      resources.forEach(resource -> executor.execute(() -> init(resource)));
      executor.shutdown();
      executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new MultimediaServiceBusinessException(e);
    } finally {
      executor.shutdown();
    }
  }

  private void init(ResourceDomain resource) {
    var service = factory.getService(resource.getType());
    if(service.isPresent()) {
      service.get().generate(resource);
      resource.getAttributes().put("thumbnailUrl", thumbnailUrl(resource.getPath()));
    } else {
      log.warn("Thumbnail service not found for file {} with type {}", resource.getPath(), resource.getType());
    }
  }

  private String thumbnailUrl(String path) {
    return Path.of("/").resolve(AppConfig.FILE_URL_PATH).resolve(path).resolve("thumbnail").toString();
  }
}
