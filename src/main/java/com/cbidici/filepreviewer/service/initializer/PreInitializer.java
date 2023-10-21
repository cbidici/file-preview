package com.cbidici.filepreviewer.service.initializer;

import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import java.util.List;

public interface PreInitializer {
  void preInit(List<ResourceDomain> resources);
}
