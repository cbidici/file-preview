package com.cbidici.filepreview.model.entity;

import com.cbidici.filepreview.model.enm.ResourceType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileEntity {
    private final String path;
    private final String name;
    private final ResourceType type;
    private final String absolutePath;
}
