package com.cbidici.filepreviewer.model.view;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentModelViewDto {
    private final String name;
    private final String relativePath;
    private final String type;
    private final String url;
    private final String thumbUrl;

    public static ContentModelViewDto fromDomain(ContentDomain content){
        return builder()
                .name(content.getFile().getName())
                .relativePath(content.getFile().getDirectoryPath())
                .type(content.getFile().getType() == FileType.DIRECTORY ? "directory" : "file")
                .url((content.getFile().getType() == FileType.DIRECTORY ? "" : "/resources/") + content.getFile().getPath())
                .thumbUrl("/resources/"+(content.getThumbnail() == null ? "" : content.getThumbnail().getFile().getPath()))
                .build();
    }
}
