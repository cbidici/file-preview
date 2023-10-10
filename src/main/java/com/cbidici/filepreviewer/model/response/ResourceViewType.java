package com.cbidici.filepreviewer.model.response;

import com.cbidici.filepreviewer.model.enm.ResourceType;

public enum ResourceViewType {
  IMAGE, VIDEO, DIRECTORY, UNSUPPORTED;

  public static ResourceViewType from(ResourceType type) {
    return switch (type) {
      case DIRECTORY -> DIRECTORY;
      case ResourceType t && t.name().startsWith("IMAGE") -> IMAGE;
      case ResourceType t && t.name().startsWith("VIDEO") -> VIDEO;
      default -> UNSUPPORTED;
    };
  }
}
