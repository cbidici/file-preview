package com.cbidici.filepreviewer.service.chain;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class FileInitializer extends ContentInitializer {

    private final FileService fileService;

    @Autowired
    public FileInitializer(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    protected void initialize(ContentDomain content) {
        content.injectFile(fileService.getFile(content.getPath()));
    }
}
