package com.cbidici.filepreviewer.service.factory;

import com.cbidici.filepreviewer.service.chain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class ContentInitializerFactory {

    private ApplicationContext applicationContext;

    @Autowired
    public ContentInitializerFactory(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ContentInitializer getContentInitializerChain(){
        ContentInitializer fileInitializer = applicationContext.getBean(FileInitializer.class);
        ContentInitializer thumbnailInitializer = applicationContext.getBean(ThumbnailInitializer.class);
        ContentInitializer optimizedInitializer = applicationContext.getBean(OptimizedInitializer.class);

        fileInitializer.setNextProcessor(thumbnailInitializer);
        thumbnailInitializer.setNextProcessor(optimizedInitializer);

        return fileInitializer;
    }

    public ContentInitializer getOptimizedContentInitializerChain() {
        ContentInitializer fileInitializer = applicationContext.getBean(FileInitializer.class);
        ContentInitializer thumbnailInitializer = applicationContext.getBean(ThumbnailInitializer.class);
        ContentInitializer optimizedGenerator = applicationContext.getBean(OptimizedGenerator.class);
        ContentInitializer optimizedInitializer = applicationContext.getBean(OptimizedInitializer.class);

        fileInitializer.setNextProcessor(thumbnailInitializer);
        thumbnailInitializer.setNextProcessor(optimizedGenerator);
        optimizedGenerator.setNextProcessor(optimizedInitializer);
        return fileInitializer;
    }

}
