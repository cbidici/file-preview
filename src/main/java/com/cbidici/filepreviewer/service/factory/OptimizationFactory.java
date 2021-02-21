package com.cbidici.filepreviewer.service.factory;

import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.OptimizationService;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OptimizationFactory implements InitializingBean {
    private Map<FileType, OptimizationService> optimizationServiceMap = new HashMap<>();
    private List<OptimizationService> optimizationServiceList;

    @Autowired
    public OptimizationFactory(List<OptimizationService> optimizationServiceList) {
        this.optimizationServiceList = optimizationServiceList;
    }

    public OptimizationService getOptimizationService(FileType type) {
        return optimizationServiceMap.get(type);
    }

    @Override
    public void afterPropertiesSet() {
        optimizationServiceList.forEach(
                optimizationService -> optimizationService.getSupportedTypes().forEach(
                        fileType -> optimizationServiceMap.put(fileType, optimizationService)
                )
        );
    }
}
