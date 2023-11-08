package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.service.ResourceService;
import com.cbidici.filepreviewer.service.priview.PreviewServiceFactory;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

@Component
@RequiredArgsConstructor
public class ResourcePathResolver extends PathResourceResolver {
  private final PreviewServiceFactory previewServiceFactory;
  private final ResourceService resourceService;

  @Override
  @Nullable
  public Resource resolveResource(@Nullable HttpServletRequest request, @NonNull String requestPath,
      @NonNull List<? extends Resource> locations, @NonNull ResourceResolverChain chain) {

    var systemDirectories = Set.of(AppConfig.THUMBNAILS, AppConfig.PREVIEWS);
    var resourcePath = URLDecoder.decode(requestPath, StandardCharsets.UTF_8);
    if(requestPath.contains("/") && systemDirectories.contains(request.getRequestURI().substring(1, request.getRequestURI().indexOf("/",1)))) {
      String resourceType = request.getRequestURI().substring(1, request.getRequestURI().indexOf("/",1));
      requestPath = getResourcePath(resourceType, resourcePath);
    }

    return chain.resolveResource(request, requestPath, locations);
  }

  private String getResourcePath(String type, String path) {
    if(type.equals("thumbnails")) {
      return path + ".jpg";
    } else if(type.equals("previews")) {
      var resource = resourceService.getResource(path);
      var previewService = previewServiceFactory.getService(resource.getType());
      if(previewService.isPresent()) {
        previewService.get().generate(resource);
        return path + ".jpg";
      }
    }
    return path;
  }
}
