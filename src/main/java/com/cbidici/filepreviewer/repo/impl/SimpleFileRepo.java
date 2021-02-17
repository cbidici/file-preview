package com.cbidici.filepreviewer.repo.impl;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.model.entity.FileEntity;
import com.cbidici.filepreviewer.repo.FileRepo;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class SimpleFileRepo implements FileRepo {

    private Path rootDirectoryPath;
    private String thumbnailDirectoryName;

    @Autowired
    public SimpleFileRepo(String rootDirectoryPath, String thumbnailDirectoryName) {
        this.rootDirectoryPath = Path.of(rootDirectoryPath);
        this.thumbnailDirectoryName = thumbnailDirectoryName;
    }

    @Override
    public FileEntity getFile(String relativeFilePathStr) {
        Path relativeFilePath = Path.of(relativeFilePathStr);
        File file = this.rootDirectoryPath.resolve(relativeFilePath).toFile();

        if(!file.exists()) {
            throw new FileEntityNotFoundException();
        }

        try {
            FileType type = FileType.getByName(Files.probeContentType(file.toPath()));

            return FileEntity.builder()
                    .name(file.getName())
                    .path(relativeFilePathStr)
                    .directoryPath(file.isDirectory() ? "" : relativeFilePath.getParent().toString())
                    .type(file.isDirectory() ? FileType.DIRECTORY : type)
                    .build();
        } catch (IOException e) {
            throw new MultimediaServiceBusinessException(e);
        }
    }


    @Override
    public String getThumbnailPath(String relativeFilePathStr) {
        // TODO : Move directory ops to DirectoryRepo
        Path relativeFilePath = Path.of(relativeFilePathStr);
        String thumbnailSubDirectoryName = generateThumbnailSubDirectoryName(relativeFilePath.getParent());
        Path thumbnailDirectoryRelativePath = Path.of(thumbnailDirectoryName).resolve(thumbnailSubDirectoryName);
        Path absoluteThumbnailDirectoryPath = rootDirectoryPath.resolve(thumbnailDirectoryRelativePath);

        if(!absoluteThumbnailDirectoryPath.toFile().exists()) {
            try {
                Files.createDirectories(absoluteThumbnailDirectoryPath);
            } catch (IOException e) {
                throw new MultimediaServiceBusinessException(e);
            }
        }

        return thumbnailDirectoryRelativePath.resolve(relativeFilePath.getFileName()).toString();
    }

    @Override
    public String getAbsolutePathOf(String relativePath) {
        return rootDirectoryPath.resolve(relativePath).toString();
    }

    @Override
    public void writeToFile(String targetFilePath, BufferedImage bufferedImage) {
        try {
            File outputFile = rootDirectoryPath.resolve(targetFilePath).toFile();
            // TODO magic jpeg
            ImageIO.write(bufferedImage, "jpeg", outputFile);
        } catch (Exception e) {
            throw new MultimediaServiceBusinessException(e);
        }

    }

    private String generateThumbnailSubDirectoryName(Path relativeFilePath) {
        return Hashing.sha256().hashString(relativeFilePath.toString(), StandardCharsets.UTF_8).toString();
    }
}
