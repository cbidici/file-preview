package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.model.domain.OptimizedDomain;
import com.cbidici.filepreviewer.model.enm.FileType;

import java.awt.image.BufferedImage;
import java.util.Set;

public abstract class OptimizationService {

    public OptimizedDomain getOptimized(String path){
        return null;
    }

    public abstract BufferedImage generateOptimizedFiles(String sourceFilePath);
    public abstract Set<FileType> getSupportedTypes();
}
