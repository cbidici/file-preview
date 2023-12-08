package com.cbidici.filepreview.service.initializer;

import com.cbidici.filepreview.model.domain.ResourceDomain;
import java.util.List;

public interface ResourceInitializer {
  void init(List<ResourceDomain> resources);
}
