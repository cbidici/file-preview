package com.cbidici.filepreview.service.thumbnail;

import com.cbidici.filepreview.config.AppConfig;
import com.cbidici.filepreview.model.domain.ResourceDomain;
import com.cbidici.filepreview.model.enm.ResourceType;
import com.cbidici.filepreview.service.FileService;
import com.cbidici.filepreview.service.multimedia.ImageService;
import com.cbidici.filepreview.service.multimedia.VideoService;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Set;
import org.springframework.stereotype.Service;

import static com.cbidici.filepreview.model.enm.ResourceType.*;


@Service
public class VideoThumbnailService extends ThumbnailService {

  private final VideoService videoService;
  private final ImageService imageService;
  private final AppConfig appConfig;

  public VideoThumbnailService(
      AppConfig appConfig, FileService fileService,
      VideoService videoService, ImageService imageService
  ) {
    super(appConfig, fileService);
    this.appConfig = appConfig;
    this.imageService = imageService;
    this.videoService = videoService;
  }

  @Override
  public Set<ResourceType> getSupportedTypes() {
    return Set.of(VIDEO_MP4, VIDEO_QUICKTIME);
  }

  @Override
  protected void generateThumbnail(ResourceDomain resource) {
    BufferedImage firstFrame = videoService
        .getFirstFrame(Path.of(appConfig.getFilesPath()).resolve(resource.getPath()));
    imageService.resizeSave(
        firstFrame, thumbnailDimension,
        Path.of(appConfig.getSystemFilesPath()).resolve(AppConfig.THUMBNAILS).resolve(resource.getPath()+".jpg")
    );
  }
}
