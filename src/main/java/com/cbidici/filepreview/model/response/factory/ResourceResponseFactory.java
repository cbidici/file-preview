package com.cbidici.filepreview.model.response.factory;

import com.cbidici.filepreview.model.domain.ResourceDomain;
import com.cbidici.filepreview.model.response.ResourceResponse;
import com.cbidici.filepreview.model.response.ResourceViewType;
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
        .url(resource.getAttributes().get("url"))
        .thumbnailUrl(resource.getAttributes().get("thumbnailUrl"))
        .previewUrl(resource.getAttributes().get("previewUrl"))
        .build();
  }
}
