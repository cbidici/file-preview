package com.cbidici.filepreviewer.service.thumbnail;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.model.domain.ResourceDomain;
import com.cbidici.filepreviewer.model.enm.ResourceType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.multimedia.ImageService;
import com.cbidici.filepreviewer.service.multimedia.VideoService;
import java.awt.image.BufferedImage;
import java.util.Set;
import org.springframework.stereotype.Service;

import static com.cbidici.filepreviewer.model.enm.ResourceType.*;


@Service
public class VideoThumbnailService extends ThumbnailService {

  private final VideoService videoService;
  private final ImageService imageService;

  public VideoThumbnailService(
      AppConfig appConfig, FileService fileService,
      VideoService videoService, ImageService imageService
  ) {
    super(appConfig, fileService);
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
        .getFirstFrame(fileService.getAbsolutePath(resource.getPath()));
    imageService.resizeSave(
        firstFrame, thumbnailDimension,
        fileService.getAbsolutePath(getThumbnailPath(resource))
    );
  }
}