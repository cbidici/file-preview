package com.cbidici.filepreviewer.model.dto;

public class BreadCrumbDto {
    private String name;
    private String url;

    public BreadCrumbDto(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
