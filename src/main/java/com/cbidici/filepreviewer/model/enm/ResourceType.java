package com.cbidici.filepreviewer.model.enm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ResourceType {
    DIRECTORY("directory", ""),
    IMAGE_JPEG("image/jpeg", "jpg"),
    IMAGE_PNG("image/png", "png"),
    IMAGE_HEIC("image/heic", "heic"),
    IMAGE_GIF("image/gif", "gif"),
    VIDEO_MP4("video/mp4", "mp4"),
    VIDEO_QUICKTIME("video/quicktime", "mov"),
    IMAGE_ARW("image/x-sony-arw", "arw"),
    UNSUPPORTED("unsupported", "unsupported");

    private static final Map<String, ResourceType> lookupName = new HashMap<>();
    private static final Map<String, ResourceType> lookupExtension = new HashMap<>();
    private final String name;
    private final String extension;

    static {
        lookupName.putAll(Arrays.stream(ResourceType.values())
            .collect(Collectors.toMap(ResourceType::getName, Function.identity())));
        lookupExtension.putAll(Arrays.stream(ResourceType.values())
            .collect(Collectors.toMap(ResourceType::getExtension, Function.identity())));
    }

    ResourceType(String name, String extension) {
        this.name = name;
        this.extension = extension;
    }

    public static ResourceType getByNameOrExtension(String name, String extension) {
        var type = lookupName.get(name);
        if(type == null) {
            log.warn("Could not find type with name {}", name);
            type = lookupExtension.get(extension.toLowerCase());
            if(type == null) {
                log.warn("Could not find type with extension {}", extension);
                type = UNSUPPORTED;
            }
        }
        return type;
        //return lookupName.getOrDefault(name, lookupExtension.getOrDefault(extension.toLowerCase(), UNSUPPORTED));
    }

    private String getName() {
        return this.name;
    }
    private String getExtension() {
        return this.extension;
    }
}
