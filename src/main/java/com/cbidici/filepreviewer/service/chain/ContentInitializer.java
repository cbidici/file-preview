package com.cbidici.filepreviewer.service.chain;

import com.cbidici.filepreviewer.model.domain.ContentDomain;

import java.io.IOException;

public abstract class ContentInitializer {

    protected ContentInitializer nextInitializer;

    public void process(ContentDomain contentDomain) throws IOException {
        this.initialize(contentDomain);

        if(this.nextInitializer != null) {
            this.nextInitializer.process(contentDomain);
        }
    }

    public void setNextProcessor(ContentInitializer nextInitializer) {
        this.nextInitializer = nextInitializer;
    }

    protected abstract void initialize(ContentDomain contentDomain) throws IOException;
}
