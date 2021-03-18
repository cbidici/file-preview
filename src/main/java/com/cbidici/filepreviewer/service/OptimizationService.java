package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.OptimizedDomain;
import com.cbidici.filepreviewer.model.enm.FileType;

import java.util.*;

public abstract class OptimizationService {

    protected List<Integer> optimizedWidths;
    protected FileService fileService;

    public OptimizationService(List<Integer> optimizedWidths, FileService fileService) {
        this.optimizedWidths = optimizedWidths;
        this.fileService = fileService;
    }

    public List<OptimizedDomain> getOptimized(String path)  {
        List<OptimizedDomain> optimizedList = new ArrayList<>();
        for(Integer optimizedWidth : optimizedWidths) {
            String optimizedPath = fileService.getOptimizedPath(path, optimizedWidth);

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

    protected abstract FileDomain generateAndSaveOptimized(String sourceFilePath, String targetFilePath, Integer optimizedWidth);

    public abstract Set<FileType> getSupportedTypes();
}
