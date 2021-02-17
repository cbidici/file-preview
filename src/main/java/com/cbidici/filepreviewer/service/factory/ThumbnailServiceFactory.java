package com.cbidici.filepreviewer.service.factory;

import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ThumbnailServiceFactory implements InitializingBean {

    private Map<FileType, ThumbnailService> thumbnailServiceMap = new HashMap<>();
    private List<ThumbnailService> thumbnailServiceList;

    @Autowired
    public ThumbnailServiceFactory(List<ThumbnailService> thumbnailServiceList) {
        this.thumbnailServiceList = thumbnailServiceList;
    }

    public ThumbnailService getThumbnailService(FileType type) {
        return thumbnailServiceMap.get(type);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        thumbnailServiceList.forEach(
                thumbnailService -> thumbnailService.getSupportedTypes().forEach(
                        fileType -> thumbnailServiceMap.put(fileType, thumbnailService)
                )
        );
    }
}
