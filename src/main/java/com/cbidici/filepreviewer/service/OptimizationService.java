package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.OptimizedDomain;
import com.cbidici.filepreviewer.model.enm.FileType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class OptimizationService {

    protected List<Integer> optimizedWidths;
    protected FileService fileService;

    public OptimizationService(List<Integer> optimizedWidths, FileService fileService) {
        this.optimizedWidths = optimizedWidths;
        this.fileService = fileService;
    }

    public OptimizedDomain getOptimized(String path)  {
        List<FileDomain> optimizedFiles = new ArrayList<>();
        for(Integer optimizedWidth : optimizedWidths) {
            String optimizedPath = fileService.getOptimizedPath(path, optimizedWidth);

            FileDomain optimizedFile;
            try {
                optimizedFile = fileService.getFile(optimizedPath);
            } catch (FileEntityNotFoundException e) {
                optimizedFile = generateAndSaveOptimized(path, optimizedPath, optimizedWidth);
            }

            optimizedFiles.add(optimizedFile);
        }

        return OptimizedDomain.builder().files(optimizedFiles).build();
    }

    protected abstract FileDomain generateAndSaveOptimized(String sourceFilePath, String targetFilePath, Integer optimizedWidth);

    public abstract Set<FileType> getSupportedTypes();
}
