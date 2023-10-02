package com.cbidici.filepreviewer.repo.impl;

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
    public SimpleDirectoryRepo(String rootDirectoryPath, String thumbnailDirectoryName, String optimizedDirectoryName) {
        this.rootDirectoryPath = Path.of(rootDirectoryPath);
        this.thumbnailDirectoryPath = this.rootDirectoryPath.resolve(thumbnailDirectoryName);
        this.optimizedDirectoryPath = this.rootDirectoryPath.resolve(optimizedDirectoryName);
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
