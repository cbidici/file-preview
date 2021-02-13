package com.cbidici.filepreviewer.service;

import com.google.common.hash.Hashing;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.tasks.UnsupportedFormatException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class DefaultThumbnailService implements ThumbnailService {

    @Value("${file.repo.path}")
    private String filesBase;

    @Value("${file.thumbnails.width}")
    private int width;


    @Override
    public String getThumbnailPath(String path) throws IOException {
        String thumbPath = generateThumbnailPath(path);
        File thumbFile = Path.of(filesBase + File.separatorChar + "thumb" + File.separatorChar + thumbPath).toFile();

        if(!thumbFile.exists()) {
            generateThumbnail(path, filesBase + File.separatorChar + "thumb" + File.separatorChar + thumbPath);
        }
        return "thumb" + File.separatorChar + thumbPath;
    }

    private void generateThumbnail(String path, String targetPath) throws IOException {
        Files.createDirectories(Paths.get(targetPath.substring(0, targetPath.lastIndexOf(File.separatorChar))));
        try {
            Thumbnails.of(filesBase+path).size(width, width).toFile(targetPath);
        }
        catch (UnsupportedFormatException ex) {
            // TODO : Add log
        }

    }

    private String generateThumbnailPath(String path) {
        String filePath = path.substring(0,path.lastIndexOf(File.separatorChar));
        String fileName = path.substring(path.lastIndexOf(File.separatorChar)+1);

        String thumbPath = Hashing.sha256().hashString(filePath, StandardCharsets.UTF_8).toString();
        return thumbPath + File.separatorChar + fileName;
    }
}
