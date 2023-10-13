package com.cbidici.filepreviewer.config;

import com.cbidici.filepreviewer.controller.ResourcePathResolver;
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
        .addResourceLocations("file:"+appConfig.getRootPath()+"/")
        .resourceChain(true)
        .addResolver(new EncodedResourceResolver())
        .addResolver(resourcePathResolver)
        .addResolver(new PathResourceResolver());
  }
}
