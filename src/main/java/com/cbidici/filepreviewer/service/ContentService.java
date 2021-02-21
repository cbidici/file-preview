package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.model.domain.ContentDomain;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ContentService {
    List<ContentDomain> getContents(String path);
    ContentDomain getContent(String path);
}
