package com.cbidici.filepreviewer.model.enm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ResourceType {
    DIRECTORY("directory"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    IMAGE_HEIC("image/heic"),
    IMAGE_GIF("image/gif"),
    VIDEO_MP4("video/mp4"),
    VIDEO_QUICKTIME("video/quicktime"),
    IMAGE_ARW("image/x-sony-arw"),
    UNSUPPORTED("unsupported");

    private static final Map<String, ResourceType> lookup = new HashMap<>();
    private final String name;

    static {
        lookup.putAll(Arrays.stream(ResourceType.values())
            .collect(Collectors.toMap(ResourceType::getName, Function.identity())));
    }

    ResourceType(String name) {
        this.name = name;
    }

    public static ResourceType getByName(String name) {
        return lookup.getOrDefault(name, UNSUPPORTED);
    }

    private String getName() {
        return this.name;
    }
}
