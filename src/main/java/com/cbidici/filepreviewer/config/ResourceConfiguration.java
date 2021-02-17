package com.cbidici.filepreviewer.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class ResourceConfiguration extends WebMvcConfigurationSupport {

    private String rootPath;

    @Autowired
    public ResourceConfiguration(String rootDirectoryPath){
        this.rootPath = rootDirectoryPath;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("file:"+rootPath+"/");
    }
}
