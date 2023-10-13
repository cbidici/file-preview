package com.cbidici.filepreviewer.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResourceResponse {
    private final String path;
    private final String name;
    private final String url;
    private final String thumbnailUrl;
    private final String thumbnailPath;
    private final String previewUrl;
    private final String previewPath;
    private final String type;
}
