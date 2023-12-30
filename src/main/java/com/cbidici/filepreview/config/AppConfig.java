package com.cbidici.filepreview.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.file-preview")
@Data
public class AppConfig {

    public static final String FILE_URL_PATH = "files";
    public static final String THUMBNAILS = "thumbnails";
    public static final String PREVIEWS = "previews";

    private String filesPath;
    private String systemFilesPath;
    private int thumbnailsWidth;
    private int previewsWidth;
}
