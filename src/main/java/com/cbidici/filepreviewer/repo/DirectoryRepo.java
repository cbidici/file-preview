package com.cbidici.filepreviewer.repo;

import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.entity.FileEntity;
import lombok.Builder;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface DirectoryRepo {
    List<String> getFilePaths(String path);
}
