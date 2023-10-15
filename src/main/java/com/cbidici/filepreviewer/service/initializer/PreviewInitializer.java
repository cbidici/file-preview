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
    resources.forEach(resource -> resource.getAttributes().put("previewUrl", previewUrl(resource.getPath())));

    new Thread(() -> {
      ThreadPoolExecutor executor = new ThreadPoolExecutor(appConfig.getPreviewThreadPoolSize(),
          appConfig.getPreviewThreadPoolSize(), 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
      try {
        resources.forEach(resource -> executor.execute(() -> generate(resource, executor)));
        executor.shutdown();
        executor.awaitTermination(Integer.MAX_VALUE, TimeUnit.MILLISECONDS);
      } catch (InterruptedException e) {
        throw new MultimediaServiceBusinessException(e);
      } finally {
        executor.shutdown();
      }
    }).start();
  }

  private void generate(ResourceDomain resource, ThreadPoolExecutor executor) {
    log.info("Going to process : " + resource.getPath() + " Active Count:" + executor.getActiveCount() + " Get Queue Size:" + executor.getQueue().size());
    var service = factory.getService(resource.getType());
    service.ifPresent(previewService -> previewService.generate(resource));
  }

  private String previewUrl(String path) {
    return Path.of("/").resolve(AppConfig.FILE_URL_PATH).resolve(path).resolve("preview").toString();
  }
}
