package com.cbidici.filepreviewer.service.impl.optimization;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.OptimizedDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.OptimizationService;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageOptimizationService extends OptimizationService {

    private static final Logger LOG = LoggerFactory.getLogger(ImageOptimizationService.class);

    @Autowired
    public ImageOptimizationService(AppConfig appConfig, FileService fileService) {
        super(appConfig.getOptimizedWidths(), fileService);
    }

    @Override
    public List<OptimizedDomain> getOptimized(String path)  {
        List<OptimizedDomain> optimizedList = new ArrayList<>();
        for(Integer optimizedWidth : optimizedWidths) {
            String optimizedPath = fileService.getOptimizedPath(path, optimizedWidth)+".jpg";
            FileDomain optimizedFile;
            try {
                optimizedFile = fileService.getFile(optimizedPath);
            } catch (FileEntityNotFoundException e) {
                optimizedFile = generateAndSaveOptimized(path, optimizedPath, optimizedWidth);
            }

            optimizedList.add(OptimizedDomain.builder().size(optimizedWidth).file(optimizedFile).build());
        }

        return optimizedList;
    }

    @Override
    protected FileDomain generateAndSaveOptimized(String sourceFilePath, String targetFilePath, Integer optimizedWidth) {
        FileDomain result = null;
        try{
            generateOptimized(sourceFilePath, targetFilePath, optimizedWidth);
            result = fileService.getFile(targetFilePath);
        } catch (MultimediaServiceBusinessException ex) {
            LOG.error("Could not get create optimized image", ex);
        }

        return result;
    }

    private void generateOptimized(String sourceFilePath, String targetFilePath, Integer optimizedWidth) {
        Dimension dimension = new Dimension(optimizedWidth, optimizedWidth);
        try {
            IMOperation op = new IMOperation();
            op.autoOrient();
            op.addImage(fileService.getAbsolutePathOf(sourceFilePath));
            op.resize(dimension.width, dimension.height);
            op.addImage("jpeg:"+fileService.getAbsolutePathOf(targetFilePath));
            ConvertCmd convert = new ConvertCmd();
            convert.run(op);
        } catch (IOException | InterruptedException | IM4JavaException e) {
            throw new MultimediaServiceBusinessException(e);
        }
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.IMAGE_HEIC, FileType.IMAGE_JPEG, FileType.IMAGE_PNG, FileType.ARW);
    }
}
