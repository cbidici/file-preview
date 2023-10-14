package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.DirectoryNotFoundException;
import com.cbidici.filepreviewer.exception.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;

@Component
@RequiredArgsConstructor
@Slf4j
public class FileService {

  private final AppConfig appConfig;

  public File getFile(String path) {
    File file = Path.of(appConfig.getRootPath()).resolve(path).toFile();
    if (!file.exists()) {
      throw new FileNotFoundException(path);
    }
    return file;
  }

  public Optional<File> findFile(String path) {
    File file = Path.of(appConfig.getRootPath()).resolve(path).toFile();
    if (!file.exists() || !isValidPath(path)) {
      return Optional.empty();
    }
    return Optional.of(file);
  }

  public List<File> getChildren(String path) {
    File directory = Path.of(appConfig.getRootPath()).resolve(path).toFile();

    if (!directory.isDirectory() || !isValidPath(path)) {
      throw new DirectoryNotFoundException(path);
    }
    return Arrays.stream(Objects.requireNonNull(directory.listFiles()))
        .filter(Predicate.not(File::isHidden))
        .filter(file -> isReservedDirectory().negate().test(file))
        .toList();
  }

  private boolean isValidPath(String path) {
    try {
      Path rootPath = Path.of(appConfig.getRootPath());
      Path requestedPath = rootPath.resolve(path);
      return requestedPath.toFile().getCanonicalPath().startsWith(rootPath.toFile().getCanonicalPath());
    } catch (IOException io) {
      return false;
    }
  }

  private Predicate<File> isReservedDirectory() {
    return (file) ->
        file.getPath().equals(getAbsolutePath(appConfig.getPreviewDirectory()).toString())
            || file.getPath().equals(getAbsolutePath(appConfig.getThumbnailDirectory()).toString());
  }

  public Path getAbsolutePath(String path) {
    return Path.of(appConfig.getRootPath()).resolve(path);
  }

  public void createDirectories(String path) {
    var directoryPath = Path.of(appConfig.getRootPath()).resolve(path);
    try {
      if(!directoryPath.toFile().exists()) {
        Files.createDirectories(directoryPath);
      }
    } catch (IOException ex) {
      log.error("Could not create directory {}", directoryPath.toFile(), ex);
    }
  }
}
