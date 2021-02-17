package com.cbidici.filepreviewer.model.enm;

import java.util.HashMap;
import java.util.Map;

public enum FileType {
    DIRECTORY("directory"),
    IMAGE_TIFF("image/tiff"),
    IMAGE_JPEG("image/jpeg");

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
}
