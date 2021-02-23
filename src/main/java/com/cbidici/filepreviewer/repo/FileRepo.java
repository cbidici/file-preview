package com.cbidici.filepreviewer.repo;

import com.cbidici.filepreviewer.model.entity.FileEntity;

import java.awt.image.BufferedImage;

public interface FileRepo {
    FileEntity getFile(String relativeFilePathStr);
    String getThumbnailPath(String relativeFilePathStr);
    String getOptimizedPath(String relativeFilePathStr, Integer optimizedWidth);
    String getAbsolutePathOf(String relativePath);
    void writeToFile(String targetFilePath, BufferedImage bufferedImage);
}

