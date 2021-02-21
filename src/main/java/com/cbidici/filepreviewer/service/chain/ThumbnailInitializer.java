package com.cbidici.filepreviewer.service.chain;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.service.ThumbnailService;
import com.cbidici.filepreviewer.service.factory.ThumbnailServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class ThumbnailInitializer extends ContentInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(ThumbnailInitializer.class);

    private final ThumbnailServiceFactory thumbnailServiceFactory;

    @Autowired
    public ThumbnailInitializer(ThumbnailServiceFactory thumbnailServiceFactory) {
        this.thumbnailServiceFactory = thumbnailServiceFactory;
    }

    @Override
    protected void initialize(ContentDomain content) {
        if(!content.hasFile()) {
            return;
        }

        ThumbnailService thumbnailService = thumbnailServiceFactory.getThumbnailService(content.getFile().getType());
        if (thumbnailService == null) {
            LOG.warn("No thumbnail service available for file type {}", content.getFile().getType());
            return;
        }

        content.injectThumbnail(thumbnailService.getThumbnail(content.getPath()));
    }
}
