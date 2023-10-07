package com.cbidici.filepreviewer.repo.impl;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.model.entity.FileEntity;
import com.cbidici.filepreviewer.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
public class SimpleFileRepo implements FileRepo {

    private final Path rootDirectoryPath;
    private final String thumbnailDirectoryName;
    private final String optimizedDirectoryName;

    @Autowired
    public SimpleFileRepo(AppConfig appConfig) {
        this.rootDirectoryPath = Path.of(appConfig.getImagePath());
        this.thumbnailDirectoryName = appConfig.getThumbnailDirectory();
        this.optimizedDirectoryName = appConfig.getOptimizedDirectory();
    }

    @Override
    public FileEntity getFile(String relativeFilePathStr) {
        Path relativeFilePath = Path.of(relativeFilePathStr);
        Path absolutePath = this.rootDirectoryPath.resolve(relativeFilePath);
        File file = absolutePath.toFile();

        if(!file.exists()) {
            throw new FileEntityNotFoundException(absolutePath.toString());
        }

        FileType type = findFileType(file);
        if(null == type) {
            throw new MultimediaServiceBusinessException(
                String.format("Unsupported file %s with type name %s", file.getPath())
            );
        }

        return FileEntity.builder()
                .name(file.getName())
                .path(relativeFilePathStr)
                .directoryPath(file.isDirectory() ? "" : relativeFilePath.getParent().toString())
                .type(type)
                .build();
    }

    private FileType findFileType(File file) {
        try {
            String fileTypeName = Files.probeContentType(file.toPath());
            FileType type = FileType.getByName(fileTypeName);
            if(file.isDirectory()) {
                type = FileType.DIRECTORY;
            }

            if(null == type) {
                for (FileType iterateFileType : FileType.values()) {
                    String extension = file.getPath().substring(file.getPath().lastIndexOf('.')+1);
                    if(iterateFileType.name().equalsIgnoreCase(extension)) {
                        type = iterateFileType;
                    }
                }
            }

            return type;
        } catch (IOException e) {
            throw new MultimediaServiceBusinessException(e);
        }
    }

    @Override
    public String getOptimizedPath(String relativeFilePathStr, Integer optimizedWidth) {
        // TODO : Move directory ops to DirectoryRepo
        Path relativeFilePath = Path.of(relativeFilePathStr);
        String optimizedSubDirectoryName = generateThumbnailSubDirectoryName(relativeFilePath.getParent());
        Path optimizedDirectoryRelativePath = Path.of(optimizedDirectoryName).resolve(optimizedSubDirectoryName);
        Path absoluteOptimizedDirectoryPath = rootDirectoryPath.resolve(optimizedDirectoryRelativePath);

        if(!absoluteOptimizedDirectoryPath.toFile().exists()) {
            try {
                Files.createDirectories(absoluteOptimizedDirectoryPath);
            } catch (IOException e) {
                throw new MultimediaServiceBusinessException(e);
            }
        }

        String fileName = relativeFilePath.getFileName().toString();
        String extension = fileName.substring(fileName.lastIndexOf('.'));
        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf('.'));
        String optimizedFileName = fileNameWithoutExtension + "_" + optimizedWidth + extension;

        return optimizedDirectoryRelativePath.resolve(optimizedFileName).toString();
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
            String extension = targetFilePath.substring(targetFilePath.lastIndexOf(".")+1);
            File outputFile = rootDirectoryPath.resolve(targetFilePath).toFile();
            ImageIO.write(bufferedImage, extension, outputFile);
        } catch (Exception e) {
            throw new MultimediaServiceBusinessException(e);
        }

    }

    private String generateThumbnailSubDirectoryName(Path relativeFilePath) {
        return relativeFilePath.toString();
    }
}
