package com.cbidici.filepreviewer.config;

import com.cbidici.filepreviewer.interceptor.ResourceOptimizationInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.resource.EncodedResourceResolver;

@Configuration
@RequiredArgsConstructor
public class ResourceConfiguration extends WebMvcConfigurationSupport {

    private final AppConfig appConfig;
    private final ResourceOptimizationInterceptor resourceOptimizationInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("file:"+appConfig.getImagePath()+"/")
                .resourceChain(true)
                .addResolver(new EncodedResourceResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(resourceOptimizationInterceptor)
                .addPathPatterns("/resources/**")
                .excludePathPatterns("/resources/"+appConfig.getThumbnailDirectory()+"/**")
                .excludePathPatterns("/resources/"+appConfig.getOptimizedDirectory()+"/**");
    }
}
