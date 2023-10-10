package com.cbidici.filepreviewer.model.response.factory;

import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.response.ResourceResponse;
import com.cbidici.filepreviewer.model.response.ResourceViewType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ResourceResponseFactory {

  public ResourceResponse getResourceResponse(ResourceDomain resource) {
    return ResourceResponse.builder()
        .path(resource.getPath())
        .name(resource.getName())
        .type(ResourceViewType.from(resource.getType()).name())
        .url(getUrl(resource))
        .thumbnailUrl(getThumbnailUrl(resource))
        .previewUrl(getPreviewUrl(resource))
        .build();
  }

  private String getUrl(ResourceDomain resource) {
    return resource.getAttributes().get("url");
  }

  private String getThumbnailUrl(ResourceDomain resource) {
    return resource.getAttributes().get("thumbnailUrl");
  }

  private String getPreviewUrl(ResourceDomain resource) {
    return resource.getAttributes().get("previewUrl");
  }
}
