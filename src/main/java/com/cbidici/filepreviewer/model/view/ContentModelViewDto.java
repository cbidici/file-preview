package com.cbidici.filepreviewer.model.view;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.model.domain.OptimizedDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ContentModelViewDto {
    private final String name;
    private final String relativePath;
    private final String type;
    private final String url;
    private final List<OptimizedUrlViewDto> optimizedUrls;
    private final String thumbUrl;

    public static ContentModelViewDto fromDomain(ContentDomain content){
        String type = "directory";
        if(content.getFile().getType().getName().startsWith("image")) {
            type = "image";
        }
        else if(content.getFile().getType().getName().startsWith("video")) {
            type = "video";
        }

        List<OptimizedUrlViewDto> optimizedUrlViewDtoList = new ArrayList<>();
        if (content.getFile().getType() != FileType.DIRECTORY && content.getOptimized()!=null) {
            for(OptimizedDomain optimized : content.getOptimized()) {
                optimizedUrlViewDtoList.add(OptimizedUrlViewDto.builder().size(optimized.getSize()).url("/resources/"+optimized.getFile().getPath()).build());
            }
        }

        return builder()
                .name(content.getFile().getName())
                .relativePath(content.getFile().getDirectoryPath())
                .type(type)
                .url((content.getFile().getType() == FileType.DIRECTORY ? "" : "/resources/") + content.getFile().getPath())
                .optimizedUrls(optimizedUrlViewDtoList)
                .thumbUrl("/resources/"+(content.getThumbnail() == null || content.getThumbnail().getFile() == null ? "" : content.getThumbnail().getFile().getPath()))
                .build();
    }
}
