package com.cbidici.filepreviewer.model.domain;

public class FileDomain {
    private final String name;
    private final String relativePath;
    private final String type;
    private final String url;
    private final String thumbUrl;

    public FileDomain(Builder builder) {
        this.name = builder.name;
        this.relativePath = builder.relativePath;
        this.type = builder.type;
        this.url = builder.url;
        this.thumbUrl = builder.thumbUrl;
    }

    public String getName() {
        return name;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public String getType() {
        return type;
    }

    public String getUrl() {
        return url;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public static class Builder {
        private String name;
        private String relativePath;
        private String type;
        private String url;
        private String thumbUrl;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder relativePath(String relativePath) {
            this.relativePath = relativePath;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder url(String  url) {
            this.url = url;
            return this;
        }

        public Builder thumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
            return this;
        }

        public FileDomain build() {
            return new FileDomain(this);
        }
    }
}
