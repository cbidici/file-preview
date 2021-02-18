package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.ThumbnailDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.util.Set;

public abstract class ThumbnailService {

    private static final Logger LOG = LoggerFactory.getLogger(ThumbnailService.class);

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

    private FileDomain generateAndSaveThumbnail(String sourceFilePath, String targetFilePath){
        FileDomain result = null;

        try{
            BufferedImage bufferedImage = generateThumbnail(sourceFilePath);
            fileService.writeToFile(targetFilePath, bufferedImage);
            result = fileService.getFile(targetFilePath);
        } catch (MultimediaServiceBusinessException ex) {
            LOG.error("Could not get frame from video", ex);
        }

        return result;
    }

    public abstract BufferedImage generateThumbnail(String sourceFilePath);
    public abstract Set<FileType> getSupportedTypes();
}
