package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.ThumbnailDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
public abstract class ThumbnailService {
    protected int thumbnailWidth;
    protected FileService fileService;

    public ThumbnailService(int thumbnailWidth, FileService fileService) {
        this.thumbnailWidth = thumbnailWidth;
        this.fileService = fileService;
    }

    public ThumbnailDomain getThumbnail(String path)  {
        String thumbnailPath = fileService.getThumbnailPath(path);
        FileDomain thumbnailFile;
        try {
            thumbnailFile = fileService.getFile(thumbnailPath);
        } catch (FileEntityNotFoundException e) {
            thumbnailFile = generateAndSaveThumbnail(path, thumbnailPath);
        }

        return ThumbnailDomain.builder().file(thumbnailFile).build();
    }

    protected FileDomain generateAndSaveThumbnail(String sourceFilePath, String targetFilePath){
        FileDomain result = null;

        try{
            generateThumbnail(sourceFilePath, targetFilePath);
            result = fileService.getFile(targetFilePath);
        } catch (MultimediaServiceBusinessException ex) {
            log.error("Could not get frame from video {}", sourceFilePath, ex);
        }

        return result;
    }

    public abstract void generateThumbnail(String path, String targetPath);

    public abstract Set<FileType> getSupportedTypes();
}
