package com.cbidici.filepreviewer.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public interface ThumbnailService {

    String getThumbnailPath(String path) throws NoSuchAlgorithmException, IOException;

}
