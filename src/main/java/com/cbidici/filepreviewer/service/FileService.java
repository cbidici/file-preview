package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.DirectoryNotFoundException;
import com.cbidici.filepreviewer.exception.FileNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileService {

  private final AppConfig appConfig;

  public File getFile(Path path) {
    File file = path.toFile();
    if (!file.exists() || !isValidPath(path)) {
      throw new FileNotFoundException(path.toString());
    }
    return file;
  }

  public Optional<File> findFile(Path path) {
    File file = path.toFile();
    if (!file.exists() || !isValidPath(path)) {
      return Optional.empty();
    }
    return Optional.of(file);
  }

  @Cacheable(cacheNames = {"fileCache"}, key = "#path")
  public List<File> getChildren(String path) {
    File directory = Path.of(path).toFile();

    if (!directory.isDirectory() || !isValidPath(directory.toPath())) {
      throw new DirectoryNotFoundException(path);
    }
    return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
        .filter(Predicate.not(File::isHidden))
        .toList();
  }

  public String getFileTypeName(File file) {
    try {
      String fileTypeName = Files.probeContentType(file.toPath());
      if(null == fileTypeName) {
        fileTypeName = new Tika().detect(file);
      }
      return fileTypeName;
    } catch (IOException e) {
      log.error("Failed to read type of resource {}", file.getPath(), e);
      throw new MultimediaServiceBusinessException(e);
    }
  }

  private boolean isValidPath(Path path) {
    try {
      return path.toFile().getCanonicalPath().startsWith(Path.of(appConfig.getFilesPath()).toFile().getCanonicalPath()) ||
          path.toFile().getCanonicalPath().startsWith(Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.THUMBNAILS).toFile().getCanonicalPath()) ||
          path.toFile().getCanonicalPath().startsWith(Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.PREVIEWS).toFile().getCanonicalPath());
    } catch (IOException io) {
      return false;
    }
  }

  public void createDirectories(Path path) {
    try {
      if(!path.toFile().exists()) {
        Files.createDirectories(path);
      }
    } catch (IOException ex) {
      log.error("Could not create directory {}", path.toFile(), ex);
    }
  }
}
