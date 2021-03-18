package com.cbidici.filepreviewer.model.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OptimizedDomain {
    private int size;
    private final FileDomain file;
}
