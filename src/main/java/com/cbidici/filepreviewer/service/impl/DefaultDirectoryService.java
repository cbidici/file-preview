package com.cbidici.filepreviewer.service.impl;

import com.cbidici.filepreviewer.repo.DirectoryRepo;
import com.cbidici.filepreviewer.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultDirectoryService implements DirectoryService {

    private final DirectoryRepo directoryRepo;

    @Autowired
    public DefaultDirectoryService(DirectoryRepo directoryRepo) {
        this.directoryRepo = directoryRepo;
    }

    @Override
    public List<String> getFilePaths(String path) {
        return directoryRepo.getFilePaths(path);
    }
}
