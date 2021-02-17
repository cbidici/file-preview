package com.cbidici.filepreviewer.service.impl;

import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.entity.FileEntity;
import com.cbidici.filepreviewer.repo.FileRepo;
import com.cbidici.filepreviewer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;

@Service
public class DefaultFileService implements FileService {

    private final FileRepo fileRepo;

    @Autowired
    public DefaultFileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Override
    public FileDomain getFile(String path) {
        FileEntity file = fileRepo.getFile(path);

        return file.toDomain();
    }

    @Override
    public String getThumbnailPath(String filePath) {
        return fileRepo.getThumbnailPath(filePath);
    }

    @Override
    public String getAbsolutePathOf(String path) {
        return fileRepo.getAbsolutePathOf(path);
    }

    @Override
    public void writeToFile(String targetFilePath, BufferedImage bufferedImage) {
        fileRepo.writeToFile(targetFilePath, bufferedImage);
    }
}
