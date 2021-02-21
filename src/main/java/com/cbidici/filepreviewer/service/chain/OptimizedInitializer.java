package com.cbidici.filepreviewer.service.chain;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.service.OptimizationService;
import com.cbidici.filepreviewer.service.factory.OptimizationFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class OptimizedInitializer extends ContentInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(OptimizedInitializer.class);

    private final OptimizationFactory optimizationFactory;

    @Autowired
    public OptimizedInitializer(OptimizationFactory optimizationFactory) {
        this.optimizationFactory = optimizationFactory;
    }

    @Override
    protected void initialize(ContentDomain content) {
        if(!content.hasFile()) {
            return;
        }

        OptimizationService optimizationService = optimizationFactory.getOptimizationService(content.getFile().getType());
        if (optimizationService == null) {
            LOG.warn("No optimization service available for file type {}", content.getFile().getType());
            return;
        }

        content.injectOptimized(optimizationService.getOptimized(content.getPath()));
    }
}
