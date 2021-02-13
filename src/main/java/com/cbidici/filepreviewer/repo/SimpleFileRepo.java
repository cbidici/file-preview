package com.cbidici.filepreviewer.repo;

import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Component
public class SimpleFileRepo implements FileRepo {


    @Value("${file.repo.path}")
    private String basePath;

    private ThumbnailService thumbnailService;

    @Autowired
    public SimpleFileRepo(ThumbnailService thumbnailService) {
        this.thumbnailService = thumbnailService;
    }

    @Override
    public List<FileDomain> getFiles(String path) throws IOException, NoSuchAlgorithmException {
        List<FileDomain> result = new ArrayList<>();

        File folder = Path.of(basePath+File.separator+path).toFile();
        File[] files = folder.listFiles();

        if(files == null) {
            return List.of();
        }

        for(File f : files) {
            if(f.getName().equals("thumb")) {
                continue;
            }

            FileDomain.Builder builder = new FileDomain.Builder()
                    .name(f.getName())
                    .relativePath(path+"/"+f.getName())
                    .type(f.isFile() ? "file" : "directory")
                    .url("/resources/"+path+"/"+f.getName());

            if (f.isFile()) {
                builder.thumbUrl("/resources/"+thumbnailService.getThumbnailPath(path+"/"+f.getName()));
            }

            result.add(builder.build());
        }

        return result;
    }
}
