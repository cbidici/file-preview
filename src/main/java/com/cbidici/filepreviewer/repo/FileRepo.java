package com.cbidici.filepreviewer.repo;

import com.cbidici.filepreviewer.model.domain.FileDomain;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface FileRepo {

    List<FileDomain> getFiles(String path) throws IOException, NoSuchAlgorithmException;

}
