package com.cbidici.filepreview.service.priview;

import com.cbidici.filepreview.model.enm.ResourceType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PreviewServiceFactory {

  private final List<PreviewService> previewServices;

  public Optional<PreviewService> getService(ResourceType type) {
    return previewServices.stream()
        .filter(service -> service.getSupportedTypes().contains(type))
        .findFirst();
  }

}
