package com.cbidici.filepreviewer.config;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.multimedia-service")
@Data
public class AppConfig {
    private String imagePath;
    private String thumbnailDirectory;
    private int thumbnailWidth;
    private String optimizedDirectory;
    private List<Integer> optimizedWidths;
}
