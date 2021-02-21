package com.cbidici.filepreviewer.service.impl;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.service.ContentService;
import com.cbidici.filepreviewer.service.DirectoryService;
import com.cbidici.filepreviewer.service.factory.ContentInitializerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultContentService implements ContentService {

    private final ContentInitializerFactory contentInitializerFactory;
    private final DirectoryService directoryService;

    @Autowired
    public DefaultContentService(ContentInitializerFactory contentInitializerFactory, DirectoryService directoryService) {
        this.contentInitializerFactory = contentInitializerFactory;
        this.directoryService = directoryService;
    }

    @Override
    public List<ContentDomain> getContents(String path) {
        List<ContentDomain> contents = new ArrayList<>();
        List<String> filePaths = directoryService.getFilePaths(path);

        for (String filePath: filePaths) {
            ContentDomain content = new ContentDomain(filePath);
            contentInitializerFactory.getContentInitializerChain().process(content);
            contents.add(content);
        }

        return contents;
    }

    @Override
    public ContentDomain getContent(String path) {
        ContentDomain content = new ContentDomain(path);
        contentInitializerFactory.getOptimizedContentInitializerChain().process(content);
        return content;
    }
}
