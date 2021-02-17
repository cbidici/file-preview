package com.cbidici.filepreviewer.model.entity;

import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileEntity {
    private final String path;
    private final String directoryPath;
    private final String name;
    private final FileType type;

    public FileDomain toDomain(){
        return FileDomain.builder()
                .path(this.path)
                .directoryPath(this.directoryPath)
                .name(this.name)
                .type(this.type)
                .build();
    }
}
