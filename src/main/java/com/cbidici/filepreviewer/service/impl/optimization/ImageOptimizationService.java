package com.cbidici.filepreviewer.service.impl.optimization;

import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.OptimizationService;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImageOptimizationService extends OptimizationService {

    @Override
    public BufferedImage generateOptimizedFiles(String sourceFilePath) {
        return null;
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return new HashSet<>();
    }
}
