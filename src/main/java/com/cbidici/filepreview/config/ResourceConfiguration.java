package com.cbidici.filepreview.config;

import com.cbidici.filepreview.controller.ResourcePathResolver;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.EncodedResourceResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration
@RequiredArgsConstructor
public class ResourceConfiguration extends WebMvcConfigurationSupport {

  private final AppConfig appConfig;
  private final ResourcePathResolver resourcePathResolver;

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("forward:/index.html");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/**")
        .addResourceLocations("classpath:/static/");

    registry.addResourceHandler("/" + AppConfig.FILE_URL_PATH + "/**")
        .addResourceLocations("file:"+appConfig.getFilesPath()+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(resourcePathResolver)
        .addResolver(new PathResourceResolver());

    registry.addResourceHandler("/" + AppConfig.PREVIEWS + "/**")
        .addResourceLocations("file:"+ Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.PREVIEWS)+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(resourcePathResolver)
        .addResolver(new PathResourceResolver());

    registry.addResourceHandler("/" + AppConfig.THUMBNAILS + "/**")
        .addResourceLocations("file:"+Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.THUMBNAILS)+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(resourcePathResolver)
        .addResolver(new PathResourceResolver());
  }
}
