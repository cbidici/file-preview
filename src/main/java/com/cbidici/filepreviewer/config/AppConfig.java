package com.cbidici.filepreviewer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AppConfig {

    @Value("${file.repo.path}")
    private String rootDirectoryPath;

    @Value("${file.repo.thumbnail.directory}")
    private String thumbnailDirectoryName;

    @Value("${file.repo.thumbnail.width}")
    private int thumbnailWidth;

    @Value("${file.repo.optimized.directory}")
    private String optimizedDirectoryName;

    @Value("${file.repo.optimized.width}")
    private List<Integer> optimizedWidths;

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

    @Bean
    public String optimizedDirectoryName() {
        return optimizedDirectoryName;
    }

    @Bean
    public List<Integer> optimizedWidths() {
        return optimizedWidths;
    }
}
