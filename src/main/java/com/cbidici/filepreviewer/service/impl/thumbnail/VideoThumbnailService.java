package com.cbidici.filepreviewer.service.impl.thumbnail;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.ThumbnailDomain;
import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.util.Set;

import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ImageService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import com.cbidici.filepreviewer.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VideoThumbnailService extends ThumbnailService {

    private final VideoService videoService;
    private final ImageService imageService;

    @Autowired
    public VideoThumbnailService(AppConfig appConfig, FileService fileService, VideoService videoService, ImageService imageService) {
        super(appConfig.getThumbnailWidth(), fileService);
        this.videoService = videoService;
        this.imageService = imageService;

    }

    @Override
    public ThumbnailDomain getThumbnail(String path)  {
        String thumbnailPath = fileService.getThumbnailPath(path)+".jpg";
        FileDomain thumbnailFile;
        try {
            thumbnailFile = fileService.getFile(thumbnailPath);
        } catch (FileEntityNotFoundException e) {
            thumbnailFile = generateAndSaveThumbnail(path, thumbnailPath);
        }

        return ThumbnailDomain.builder().file(thumbnailFile).build();
    }

    @Override
    public void generateThumbnail(String sourceFilePath, String targetFilePath) {
        BufferedImage firstFrame = videoService.getFirstFrame(Path.of(fileService.getAbsolutePathOf(sourceFilePath)).toFile());
        BufferedImage resizedFrame = imageService.getMaxSized(firstFrame, thumbnailWidth, thumbnailWidth);
        fileService.writeToFile(targetFilePath, resizedFrame);
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.VIDEO_MP4, FileType.VIDEO_QUICKTIME);
    }
}
