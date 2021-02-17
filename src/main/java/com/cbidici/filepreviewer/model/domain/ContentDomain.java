package com.cbidici.filepreviewer.model.domain;

import lombok.Getter;

import java.nio.file.Path;

@Getter
public class ContentDomain {

    private String path;

    private FileDomain file;
    private ThumbnailDomain thumbnail;
    private UrlDomain url;

    public ContentDomain(String path) {
        this.path = path;
    }

    public void injectFile(FileDomain file){
        if(this.file == null) {
            this.file = file;
        }
    }

    public void injectThumbnail(ThumbnailDomain thumbnail) {
        if(this.thumbnail == null) {
            this.thumbnail = thumbnail;
        }
    }

    public void injectUrl(UrlDomain url) {
        if(this.url == null) {
            this.url = url;
        }
    }
}
