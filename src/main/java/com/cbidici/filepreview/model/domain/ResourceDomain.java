package com.cbidici.filepreview.model.domain;

import com.cbidici.filepreview.model.enm.ResourceType;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResourceDomain {
    private final String path;
    private final String name;
    private final ResourceType type;
    private final Map<String, String> attributes;
}
