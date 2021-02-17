package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.ThumbnailDomain;
import com.cbidici.filepreviewer.model.enm.FileType;

import java.awt.image.BufferedImage;
import java.util.Set;

public abstract class ThumbnailService {

    protected FileService fileService;

    public ThumbnailService(FileService fileService) {
        this.fileService = fileService;
    }

    public ThumbnailDomain getThumbnail(String path)  {
        String thumbnailPath = fileService.getThumbnailPath(path);

        FileDomain thumbnailFile = null;
        try {
            thumbnailFile = fileService.getFile(thumbnailPath);
        } catch (FileEntityNotFoundException e) {
            generateAndSaveThumbnail(path, thumbnailPath);
            thumbnailFile = fileService.getFile(thumbnailPath);
        }

        return ThumbnailDomain.builder().file(thumbnailFile).build();
    }

    private void generateAndSaveThumbnail(String sourceFilePath, String targetFilePath){
        BufferedImage bufferedImage = generateThumbnail(sourceFilePath);
        fileService.writeToFile(targetFilePath, bufferedImage);
    }

    public abstract BufferedImage generateThumbnail(String sourceFilePath);
    public abstract Set<FileType> getSupportedTypes();
}
