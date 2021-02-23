package com.cbidici.filepreviewer.config;

import com.cbidici.filepreviewer.interceptor.ResourceOptimizationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
public class ResourceConfiguration extends WebMvcConfigurationSupport {

    private final String rootPath;
    private final String thumbnailDirectoryName;
    private final String optimizedDirectoryName;
    private final ResourceOptimizationInterceptor resourceOptimizationInterceptor;

    @Autowired
    public ResourceConfiguration(String rootDirectoryPath, String thumbnailDirectoryName, String optimizedDirectoryName, ResourceOptimizationInterceptor resourceOptimizationInterceptor){
        this.rootPath = rootDirectoryPath;
        this.thumbnailDirectoryName = thumbnailDirectoryName;
        this.optimizedDirectoryName = optimizedDirectoryName;
        this.resourceOptimizationInterceptor = resourceOptimizationInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:"+rootPath+"/")
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourceOptimizationInterceptor)
                .addPathPatterns("/resources/**")
                .excludePathPatterns("/resources/"+this.thumbnailDirectoryName+"/**")
                .excludePathPatterns("/resources/"+this.optimizedDirectoryName+"/**");
    }
}
