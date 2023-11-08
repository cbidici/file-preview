package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.initializer.PreInitializer;
import com.cbidici.filepreviewer.service.initializer.ResourceInitializer;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResourceService {

  private final List<ResourceInitializer> initializers;
  private final List<PreInitializer> preInitializers;
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
        .map(file -> ResourceDomain.builder()
            .name(file.getName())
            .type(findResourceType(file))
            .path(Path.of(path).resolve(file.getName()).toString())
            .attributes(new HashMap<>())
            .build())
        .sorted(Comparator.comparing(ResourceDomain::getName))
        .toList();

    var resources = allResources.stream()
        .skip(offset)
        .limit(size)
        .toList();

    var nextResources = allResources.stream()
        .skip(offset+size)
        .limit(size)
        .toList();

    initializers.forEach(initializer -> initializer.init(resources));
    preInitializers.forEach(initializer -> initializer.preInit(nextResources));
    return resources;
  }

  private ResourceType findResourceType(File file) {
    if (file.isDirectory()) {
      return ResourceType.DIRECTORY;
    }
    try {
      String fileTypeName = Files.probeContentType(file.toPath());
      if(null == fileTypeName) {
        fileTypeName = new Tika().detect(file);
      }
      String extension = file.getPath().substring(file.getPath().lastIndexOf('.') + 1);
      return ResourceType.getByNameOrExtension(fileTypeName, extension);
    } catch (IOException e) {
      log.error("Failed to read type of resource {}", file.getPath(), e);
      throw new MultimediaServiceBusinessException(e);
    }
  }
}
