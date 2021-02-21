package com.cbidici.filepreviewer.service;

import java.awt.image.BufferedImage;
import java.io.File;

public interface VideoService {
    public BufferedImage getFirstFrame(File file);
}
