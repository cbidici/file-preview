package com.cbidici.filepreviewer.model.response;

import com.cbidici.filepreviewer.model.enm.ResourceType;

public enum ResourceViewType {
  IMAGE, VIDEO, DIRECTORY, UNSUPPORTED;

  public static ResourceViewType from(ResourceType type) {
    if(type == ResourceType.DIRECTORY) {
      return DIRECTORY;
    } else if(type.name().startsWith("IMAGE")) {
      return IMAGE;
    } else if (type.name().startsWith("VIDEO")) {
      return VIDEO;
    } else {
      return UNSUPPORTED;
    }
  }
}
