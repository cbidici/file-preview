package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.repo.FileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class DefaultFileService implements FileService {

    private final FileRepo fileRepo;

    @Autowired
    public DefaultFileService(FileRepo fileRepo) {
        this.fileRepo = fileRepo;
    }

    @Override
    public List<FileDomain> getFiles(String path) throws IOException, NoSuchAlgorithmException {
        return fileRepo.getFiles(path);
    }
}
