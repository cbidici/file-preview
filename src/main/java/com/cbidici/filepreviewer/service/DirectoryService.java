package com.cbidici.filepreviewer.service;

import java.io.IOException;
import java.util.List;

public interface DirectoryService {
    List<String> getFilePaths(String path);
}
