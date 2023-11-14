package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.service.priview.PreviewServiceFactory;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(3)
@RequiredArgsConstructor
@Slf4j
public class PreviewInitializer implements ResourceInitializer {

  private final PreviewServiceFactory factory;
  private final AppConfig appConfig;

  @Override
  public void init(List<ResourceDomain> resources) {
    ThreadPoolExecutor previewInitExecutor = new ThreadPoolExecutor(appConfig.getThumbnailsThreadPoolSize(), appConfig.getThumbnailsThreadPoolSize(), 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    try {
      resources.forEach(resource -> previewInitExecutor.execute(() -> init(resource)));
      previewInitExecutor.shutdown();
      previewInitExecutor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new MultimediaServiceBusinessException(e);
    } finally {
      previewInitExecutor.shutdown();
    }
  }

  private void init(ResourceDomain resource) {
    var service = factory.getService(resource.getType());
    if(service.isPresent()) {
      resource.getAttributes().put("previewUrl", previewUrl(resource.getPath()));
      service.get().generate(resource);
    } else {
      log.warn("Preview service not found for file {} with type {}", resource.getPath(), resource.getType());
    }
  }

  private String previewUrl(String path) {
    return Path.of("/").resolve(AppConfig.PREVIEWS).resolve(path).toString();
  }
}
