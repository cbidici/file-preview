package com.cbidici.filepreviewer.model.enm;

import java.util.HashMap;
import java.util.Map;

public enum FileType {
    DIRECTORY("directory"),
    IMAGE_JPEG("image/jpeg"),
    IMAGE_PNG("image/png"),
    VIDEO_MP4("video/mp4"),
    VIDEO_QUICKTIME("video/quicktime");

    private static final Map<String, FileType> lookup = new HashMap<String, FileType>();
    private final String name;

    static {
        for (FileType t : FileType.values()) {
            lookup.put(t.name, t);
        }
    }

    FileType(String name) {
        this.name = name;
    }

    public static FileType getByName(String name) {
        return lookup.get(name);
    }

    public String getName() {
        return name;
    }
}
