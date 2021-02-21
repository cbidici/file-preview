package com.cbidici.filepreviewer.service.factory;

import com.cbidici.filepreviewer.service.chain.ContentInitializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContentInitializerFactory implements InitializingBean {

    private ContentInitializer fileInitializer;
    private ContentInitializer thumbnailInitializer;
    private ContentInitializer optimizedInitializer;

    public ContentInitializer getContentInitializerChain(){
        return fileInitializer;
    }

    @Autowired
    public void setFileInitializer(ContentInitializer fileInitializer) {
        this.fileInitializer = fileInitializer;
    }

    @Autowired
    public void setThumbnailInitializer(ContentInitializer thumbnailInitializer) {
        this.thumbnailInitializer = thumbnailInitializer;
    }

    @Autowired
    public void setOptimizedInitializer(ContentInitializer optimizedInitializer) {
        this.optimizedInitializer = optimizedInitializer;
    }

    @Override
    public void afterPropertiesSet() {
        this.fileInitializer.setNextProcessor(this.thumbnailInitializer);
        this.thumbnailInitializer.setNextProcessor(this.optimizedInitializer);
    }
}
