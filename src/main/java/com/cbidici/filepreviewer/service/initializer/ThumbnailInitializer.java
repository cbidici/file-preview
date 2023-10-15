package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.service.thumbnail.ThumbnailServiceFactory;
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
@RequiredArgsConstructor
@Order(2)
@Slf4j
public class ThumbnailInitializer implements ResourceInitializer{

  private final AppConfig appConfig;
  private final ThumbnailServiceFactory factory;

  @Override
  public void init(List<ResourceDomain> resources) {
    ThreadPoolExecutor executor = new ThreadPoolExecutor(appConfig.getThumbnailThreadPoolSize(), appConfig.getThumbnailThreadPoolSize(), 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    try {
      resources.forEach(resource -> executor.execute(() -> init(resource, executor)));
      executor.shutdown();
      executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
    } catch (InterruptedException e) {
      throw new MultimediaServiceBusinessException(e);
    } finally {
      executor.shutdown();
    }
  }

  private void init(ResourceDomain resource, ThreadPoolExecutor executor) {
    log.info("Going to process : " + resource.getPath() + " Active Count:" + executor.getActiveCount() + " Get Queue Size:" + executor.getQueue().size());
    var service = factory.getService(resource.getType());
    if(service.isPresent()) {
      service.get().generate(resource);
      resource.getAttributes().put("thumbnailUrl", thumbnailUrl(resource.getPath()));
    }
  }

  private String thumbnailUrl(String path) {
    return Path.of("/").resolve(AppConfig.FILE_URL_PATH).resolve(path).resolve("thumbnail").toString();
  }
}
