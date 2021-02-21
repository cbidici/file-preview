package com.cbidici.filepreviewer.model.domain;

import com.cbidici.filepreviewer.model.enm.FileType;
import lombok.Getter;

import java.nio.file.Path;

@Getter
public class ContentDomain {

    private String path;

    private FileDomain file;
    private ThumbnailDomain thumbnail;
    private UrlDomain url;
    private OptimizedDomain optimized;

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

    public void injectOptimized(OptimizedDomain optimized) {
        if (this.optimized == null) {
            this.optimized = optimized;
        }
    }

    public boolean hasFile(){
        if (this.file == null) {
            return false;
        }

        if (this.file.getType() == FileType.DIRECTORY) {
            return false;
        }

        return true;
    }
}
