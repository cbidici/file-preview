package com.cbidici.filepreviewer.model.domain;

import com.cbidici.filepreviewer.model.enm.FileType;
import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@Builder
public class FileDomain {
    private final String path;
    private final String directoryPath;
    private final String name;
    private final FileType type;
}
