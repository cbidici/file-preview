package com.cbidici.filepreviewer.service.impl.thumbnail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ImageService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

@Service
public class ImageThumbnailService extends ThumbnailService {

    private final ImageService imageService;

    @Autowired
    public ImageThumbnailService(int thumbnailWidth, FileService fileService, ImageService imageService) {
        super(thumbnailWidth, fileService);
        this.imageService = imageService;
    }

    @Override
    public BufferedImage generateThumbnail(String path) {
        try{
            BufferedImage image = ImageIO.read(new File(fileService.getAbsolutePathOf(path)));
            return imageService.getMaxSized(image, thumbnailWidth, thumbnailWidth);
        }
        catch (IOException ex) {
            throw new MultimediaServiceBusinessException(ex);
        }
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.IMAGE_JPEG, FileType.IMAGE_PNG);
    }
}
