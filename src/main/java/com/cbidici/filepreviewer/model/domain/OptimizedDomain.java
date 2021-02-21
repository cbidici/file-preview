package com.cbidici.filepreviewer.model.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class OptimizedDomain {
    private List<FileDomain> files;
}
