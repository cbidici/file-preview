package com.cbidici.filepreviewer.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.multimedia-service")
@Data
public class AppConfig {

    public static final String FILE_URL_PATH = "files";

    private String rootPath;
    private String thumbnailDirectory;
    private int thumbnailWidth;
    private String previewDirectory;
    private int previewWidth;
}
