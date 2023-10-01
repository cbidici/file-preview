package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.model.domain.ContentDomain;
import com.cbidici.filepreviewer.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Controller
@RequestMapping("/files")
public class FileController {

    private final ContentService contentService;

    @Autowired
    public FileController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/**")
    @ResponseBody
    public ResponseEntity<List<ContentDomain>> getFiles(HttpServletRequest request) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/files/").length == 1 ? "" : requestURL.split("/files/")[1];

        return new ResponseEntity<>(contentService.getContents(URLDecoder.decode(path, StandardCharsets.UTF_8)), HttpStatus.OK);
    }

}
