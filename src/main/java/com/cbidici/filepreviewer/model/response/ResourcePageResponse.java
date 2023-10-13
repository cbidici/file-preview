package com.cbidici.filepreviewer.model.response;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResourcePageResponse {
  private final List<BreadCrumbResponse> breadCrumbs;
  private final List<ResourceResponse> resources;
}
