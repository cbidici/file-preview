package com.cbidici.filepreviewer.service.impl.optimization;

import com.cbidici.filepreviewer.exception.FileEntityNotFoundException;
import com.cbidici.filepreviewer.exception.MultimediaServiceBusinessException;
import com.cbidici.filepreviewer.model.domain.FileDomain;
import com.cbidici.filepreviewer.model.domain.OptimizedDomain;
import com.cbidici.filepreviewer.model.enm.FileType;
import com.cbidici.filepreviewer.service.FileService;
import com.cbidici.filepreviewer.service.OptimizationService;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.imageio.ImageIO;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class ExtendedImageOptimizationService extends OptimizationService {

    private static final Logger LOG = LoggerFactory.getLogger(ExtendedImageOptimizationService.class);

    @Autowired
    public ExtendedImageOptimizationService(@Qualifier("optimizedWidths") List<Integer> optimizedWidths, FileService fileService) {
        super(optimizedWidths, fileService);
    }

    @Override
    public List<OptimizedDomain> getOptimized(String path)  {
        List<OptimizedDomain> optimizedList = new ArrayList<>();
        for(Integer optimizedWidth : optimizedWidths) {
            String optimizedPath = fileService.getOptimizedPath(path, optimizedWidth);
            var heicOptimizedPath = optimizedPath.substring(0, optimizedPath.lastIndexOf(".")) + ".heic.jpg";
            FileDomain optimizedFile;
            try {
                optimizedFile = fileService.getFile(heicOptimizedPath);
            } catch (FileEntityNotFoundException e) {
                optimizedFile = generateAndSaveOptimized(path, heicOptimizedPath, optimizedWidth);
            }

            optimizedList.add(OptimizedDomain.builder().size(optimizedWidth).file(optimizedFile).build());
        }

        return optimizedList;
    }

    @Override
    protected FileDomain generateAndSaveOptimized(String sourceFilePath, String targetFilePath, Integer optimizedWidth) {
        FileDomain result = null;
        try{
            BufferedImage bufferedImage = generateOptimized(sourceFilePath, optimizedWidth);
            fileService.writeToFile(targetFilePath, bufferedImage);
            result = fileService.getFile(targetFilePath);
        } catch (MultimediaServiceBusinessException ex) {
            LOG.error("Could not get create optimized image", ex);
        }

        return result;
    }

    private BufferedImage generateOptimized(String sourceFilePath, Integer optimizedWidth) {
        Dimension dimension = new Dimension(optimizedWidth, optimizedWidth);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream inputStream = null;
        try {
            try {
                inputStream = new FileInputStream(fileService.getAbsolutePathOf(sourceFilePath));
                IMOperation op = new IMOperation();
                Pipe pipeIn  = new Pipe(inputStream,null);
                Pipe pipeOut  = new Pipe(null, baos);
                op.addImage("-");
                op.resize(dimension.width, dimension.height);
                op.addImage("jpeg:-");
                ConvertCmd convert = new ConvertCmd();
                convert.setOutputConsumer(pipeOut);
                convert.setInputProvider(pipeIn);
                convert.run(op);
                baos.flush();
                byte[] bytes = baos.toByteArray();
                InputStream is = new ByteArrayInputStream(bytes);
                return ImageIO.read(is);
            } catch (IOException | InterruptedException | IM4JavaException e) {
                throw new MultimediaServiceBusinessException(e);
            } finally {
                baos.close();
                inputStream.close();
            }
        } catch (IOException ex) {
            throw new MultimediaServiceBusinessException(ex);
        }
    }

    @Override
    public Set<FileType> getSupportedTypes() {
        return Set.of(FileType.IMAGE_HEIC);
    }
}
