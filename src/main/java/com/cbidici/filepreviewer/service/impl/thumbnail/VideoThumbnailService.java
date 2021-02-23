package com.cbidici.filepreviewer.service.impl.thumbnail;

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
    public VideoThumbnailService(int thumbnailWidth, FileService fileService, VideoService videoService, ImageService imageService) {
        super(thumbnailWidth, fileService);
        this.videoService = videoService;
        this.imageService = imageService;
    }

    @Override
    public BufferedImage generateThumbnail(String sourceFilePath) {
        BufferedImage firstFrame = videoService.getFirstFrame(Path.of(fileService.getAbsolutePathOf(sourceFilePath)).toFile());
        return imageService.getMaxSized(firstFrame, thumbnailWidth, thumbnailWidth);
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.VIDEO_MP4, FileType.VIDEO_QUICKTIME);
    }
}
