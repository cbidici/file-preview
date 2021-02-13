package com.cbidici.filepreviewer.service;

import com.cbidici.filepreviewer.model.domain.FileDomain;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileService {
    List<FileDomain> getFiles(String path) throws IOException, NoSuchAlgorithmException;
}
