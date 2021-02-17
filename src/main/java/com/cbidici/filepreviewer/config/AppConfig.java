package com.cbidici.filepreviewer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${file.repo.path}")
    private String rootDirectoryPath;

    @Value("${file.repo.thumbnail.directory}")
    private String thumbnailDirectoryName;

    @Value("${file.repo.thumbnail.width}")
    private int thumbnailWidth;

    @Bean
    public String rootDirectoryPath(){
        return this.rootDirectoryPath;
    }

    @Bean
    public String thumbnailDirectoryName() {
        return this.thumbnailDirectoryName;
    }

    @Bean
    public int thumbnailWidth() {
        return this.thumbnailWidth;
    }
}
