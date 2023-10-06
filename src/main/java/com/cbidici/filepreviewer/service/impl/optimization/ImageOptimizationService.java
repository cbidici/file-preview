package com.cbidici.filepreviewer.service.impl.optimization;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.ImageService;
import com.cbidici.filepreviewer.service.OptimizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
public class ImageOptimizationService extends OptimizationService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageOptimizationService.class);

    private final ImageService imageService;

    @Autowired
    public ImageOptimizationService(AppConfig appConfig, FileService fileService, ImageService imageService) {
        super(appConfig.getOptimizedWidths(), fileService);
        this.imageService = imageService;
    }

    @Override
    protected FileDomain generateAndSaveOptimized(String sourceFilePath, String targetFilePath, Integer optimizedWidth) {
        FileDomain result = null;

        try{
            BufferedImage bufferedImage = generateOptimized(sourceFilePath, optimizedWidth);
            fileService.writeToFile(targetFilePath, bufferedImage);
            result = fileService.getFile(targetFilePath);
        } catch (MultimediaServiceBusinessException ex) {
            LOG.error("Could not get create optimized image", ex);
        }

        return result;
    }

    private BufferedImage generateOptimized(String sourceFilePath, Integer optimizedWidth) {
        try{
            BufferedImage image = ImageIO.read(new File(fileService.getAbsolutePathOf(sourceFilePath)));
            return imageService.getMaxSized(image, optimizedWidth, optimizedWidth);
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
