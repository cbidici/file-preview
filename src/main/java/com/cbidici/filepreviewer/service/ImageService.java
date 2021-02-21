package com.cbidici.filepreviewer.service;

import java.awt.image.BufferedImage;

public interface ImageService {
    public BufferedImage getMaxSized(BufferedImage originalImage, int maxWidth, int maxHeight);
}
