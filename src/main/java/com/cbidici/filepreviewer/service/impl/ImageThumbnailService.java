package com.cbidici.filepreviewer.service.impl;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Set;

@Service
public class ImageThumbnailService extends ThumbnailService {

    private int thumbnailWidth;

    @Autowired
    public ImageThumbnailService(int thumbnailWidth, FileService fileService) {
        super(fileService);
        this.thumbnailWidth = thumbnailWidth;
    }


    @Override
    public BufferedImage generateThumbnail(String path) {
        try {
            return Thumbnails.of(fileService.getAbsolutePathOf(path)).size(thumbnailWidth, thumbnailWidth).asBufferedImage();
        }
        catch (Exception ex) {
            throw new MultimediaServiceBusinessException(ex);
        }

    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.IMAGE_JPEG);
    }
}
