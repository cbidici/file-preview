package com.cbidici.filepreview.model.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BreadCrumbResponse {
    private final String name;
    private final String url;
}
