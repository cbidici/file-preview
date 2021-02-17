package com.cbidici.filepreviewer.service.chain;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileInitializer extends ContentInitializer {

    private final FileService fileService;

    @Autowired
    public FileInitializer(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    protected void initialize(ContentDomain content) throws IOException {
        content.injectFile(fileService.getFile(content.getPath()));
    }
}
