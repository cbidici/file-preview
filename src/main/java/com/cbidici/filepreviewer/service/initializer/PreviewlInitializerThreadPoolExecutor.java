package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.config.AppConfig;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.springframework.stereotype.Service;

@Service
public class PreviewlInitializerThreadPoolExecutor extends ThreadPoolExecutor {
  private static final int CORE_POOL_SIZE = 1;
  private static final int KEEP_ALIVE_TIME = 10;


  public PreviewlInitializerThreadPoolExecutor(AppConfig appConfig) {
    super(CORE_POOL_SIZE, appConfig.getThumbnailThreadPoolSize(), KEEP_ALIVE_TIME, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
  }
}
