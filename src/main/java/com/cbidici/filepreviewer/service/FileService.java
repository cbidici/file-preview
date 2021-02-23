package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileService {

    FileDomain getFile(String path);

    String getThumbnailPath(String path);

    String getOptimizedPath(String path, Integer optimizedWidth);

    String getAbsolutePathOf(String path);

    void writeToFile(String targetFilePath, BufferedImage bufferedImage);
}
