package com.cbidici.filepreviewer.repo.impl;

import com.cbidici.filepreviewer.config.AppConfig;
import com.cbidici.filepreviewer.repo.DirectoryRepo;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleDirectoryRepo implements DirectoryRepo {

    private final Path rootDirectoryPath;
    private final Path thumbnailDirectoryPath;
    private final Path optimizedDirectoryPath;

    @Autowired
    public SimpleDirectoryRepo(AppConfig appConfig) {
        this.rootDirectoryPath = Path.of(appConfig.getImagePath());
        this.thumbnailDirectoryPath = this.rootDirectoryPath.resolve(appConfig.getThumbnailDirectory());
        this.optimizedDirectoryPath = this.rootDirectoryPath.resolve(appConfig.getOptimizedDirectory());
    }

    @Override
    public List<String> getFilePaths(String relativeDirectoryPathStr) {
        List<String> filePaths = new ArrayList<>();

        Path relativeDirectoryPath = Path.of(relativeDirectoryPathStr);
        File directory = this.rootDirectoryPath.resolve(relativeDirectoryPath).toFile();

        if(!directory.isDirectory()){
            return filePaths;
        }

        for(File file : Objects.requireNonNull(directory.listFiles())) {
            if(!file.getName().startsWith(".")
                && !file.getPath().equals(thumbnailDirectoryPath.toString())
                && !file.getPath().equals(optimizedDirectoryPath.toString())) {
                filePaths.add(relativeDirectoryPath.resolve(file.getName()).toString());
            }
        }

        return filePaths;
    }

}
