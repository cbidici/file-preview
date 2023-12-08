package com.cbidici.filepreview.service.thumbnail;

import com.cbidici.filepreview.model.enm.ResourceType;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThumbnailServiceFactory {
  private final List<ThumbnailService> thumbnailServices;

  public Optional<ThumbnailService> getService(ResourceType type) {
    return thumbnailServices.stream()
        .filter(service -> service.getSupportedTypes().contains(type))
        .findFirst();
  }

}
