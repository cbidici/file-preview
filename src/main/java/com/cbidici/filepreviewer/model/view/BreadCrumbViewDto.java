package com.cbidici.filepreviewer.model.view;

public class BreadCrumbViewDto {
    private String name;
    private String url;

    public BreadCrumbViewDto(String name, String url) {
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
