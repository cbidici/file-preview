package com.cbidici.filepreview.service;

import com.cbidici.filepreview.config.AppConfig;
import com.cbidici.filepreview.model.domain.ResourceDomain;
import com.cbidici.filepreview.model.enm.ResourceType;
import com.cbidici.filepreview.service.initializer.ResourceInitializer;
import java.io.File;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

  private final List<ResourceInitializer> initializers;
  private final FileService fileService;
  private final AppConfig appConfig;

  public ResourceDomain getResource(String path) {
    var file = fileService.getFile(Path.of(appConfig.getFilesPath()).resolve(path));
    return ResourceDomain.builder()
        .name(file.getName())
        .type(findResourceType(file))
        .path(Path.of(path).toString())
        .build();
  }

  public List<ResourceDomain> getChildren(String path, int offset, int size) {
    var allResources = fileService.getChildren(Path.of(appConfig.getFilesPath()).resolve(path).toString()).stream()
        .sorted(Comparator.comparing(File::getName))
        .toList();

    var resources = allResources.stream()
        .skip(offset)
        .limit(size)
        .map(file -> ResourceDomain.builder()
            .name(file.getName())
            .type(findResourceType(file))
            .path(Path.of(path).resolve(file.getName()).toString())
            .attributes(new HashMap<>())
            .build())
        .toList();

    initializers.forEach(initializer -> initializer.init(resources));
    return resources;
  }

  private ResourceType findResourceType(File file) {
    if (file.isDirectory()) {
      return ResourceType.DIRECTORY;
    }

    String fileTypeName = fileService.getFileTypeName(file);
    String extension = file.getPath().substring(file.getPath().lastIndexOf('.') + 1);
    return ResourceType.getByNameOrExtension(fileTypeName, extension);
  }
}
