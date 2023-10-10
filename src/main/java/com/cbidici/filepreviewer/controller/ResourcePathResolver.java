package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.service.ResourceService;
import com.cbidici.filepreviewer.service.priview.PreviewServiceFactory;
import com.cbidici.filepreviewer.service.thumbnail.ThumbnailServiceFactory;
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
  private final ThumbnailServiceFactory thumbnailServiceFactory;
  private final PreviewServiceFactory previewServiceFactory;
  private final ResourceService resourceService;

  @Override
  @Nullable
  public Resource resolveResource(@Nullable HttpServletRequest request, @NonNull String requestPath,
      @NonNull List<? extends Resource> locations, @NonNull ResourceResolverChain chain) {

    var systemDirectories = Set.of("thumbnail", "preview");
    requestPath = URLDecoder.decode(requestPath, StandardCharsets.UTF_8);
    if(requestPath.contains("/") && systemDirectories.contains(requestPath.substring(requestPath.lastIndexOf("/")+1))) {
      String resourceType = requestPath.substring(requestPath.lastIndexOf("/")+1);
      String resourcePath = requestPath.substring(0, requestPath.lastIndexOf("/"));
      requestPath= getResourcePath(resourceType, resourcePath);
    }

    return chain.resolveResource(request, requestPath, locations);
  }

  private String getResourcePath(String type, String path) {
    var resource = resourceService.getResource(path);
    if(type.equals("thumbnail")) {
      return thumbnailServiceFactory.getService(resource.getType()).map(service -> service.getThumbnailPath(resource)).orElse(path);
    } else if(type.equals("preview")) {
      var previewService = previewServiceFactory.getService(resource.getType());
      if(previewService.isPresent()) {
        previewService.get().generate(resource);
        return previewService.get().getPreviewPath(resource);
      }
    }
    return path;
  }
}
