package com.cbidici.filepreviewer.model.domain;

import lombok.Builder;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@Builder
public class ThumbnailDomain {
    private FileDomain file;
}
