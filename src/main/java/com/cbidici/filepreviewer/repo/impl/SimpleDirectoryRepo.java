package com.cbidici.filepreviewer.repo.impl;

import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.model.entity.FileEntity;
import com.cbidici.filepreviewer.repo.DirectoryRepo;
import com.google.common.hash.Hashing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleDirectoryRepo implements DirectoryRepo {

    private Path rootDirectoryPath;

    @Autowired
    public SimpleDirectoryRepo(String rootDirectoryPath) {
        this.rootDirectoryPath = Path.of(rootDirectoryPath);
    }

    @Override
    public List<String> getFilePaths(String relativeDirectoryPathStr) {
        List<String> filePaths = new ArrayList<>();

        Path relativeDirectoryPath = Path.of(relativeDirectoryPathStr);
        File directory = this.rootDirectoryPath.resolve(relativeDirectoryPath).toFile();

        if(!directory.isDirectory()){
            return filePaths;
        }

        for(File file : directory.listFiles()) {
            filePaths.add(relativeDirectoryPath.resolve(file.getName()).toString());
        }

        return filePaths;
    }

}
