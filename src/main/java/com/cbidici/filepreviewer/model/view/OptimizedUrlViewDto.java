package com.cbidici.filepreviewer.model.view;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptimizedUrlViewDto {
    private final int size;
    private final String url;
}
