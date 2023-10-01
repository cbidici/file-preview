package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.model.view.BreadCrumbViewDto;
import com.cbidici.filepreviewer.model.view.ContentModelViewDto;
import com.cbidici.filepreviewer.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PreviewController {

    ContentService contentService;

    @Autowired
    public PreviewController(ContentService contentService) {
        this.contentService = contentService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/gallery";
    }

    @GetMapping("/gallery/**")
    public String gallery(HttpServletRequest request, Model model) throws IOException {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/gallery/").length == 1 ? "" : requestURL.split("/gallery/")[1];

        StringBuilder breadCrumbUrlBuilder = new StringBuilder();
        breadCrumbUrlBuilder.append("/gallery");
        String[] directories = path.split("/");
        List<BreadCrumbViewDto> breadCrumbViewDtoList = new ArrayList<>();
        breadCrumbViewDtoList.add(new BreadCrumbViewDto("Home", breadCrumbUrlBuilder.toString()));
        for(String directory : directories) {
            if(!directory.isEmpty()) {
                breadCrumbUrlBuilder.append("/").append(URLDecoder.decode(directory, StandardCharsets.UTF_8));
                breadCrumbViewDtoList.add(new BreadCrumbViewDto(URLDecoder.decode(directory, StandardCharsets.UTF_8), breadCrumbUrlBuilder.toString()));
            }
        }

        List<ContentModelViewDto> contents = contentService.getContents(URLDecoder.decode(path, StandardCharsets.UTF_8))
                .stream()
                .map(ContentModelViewDto::fromDomain)
                .collect(Collectors.toList());

        model.addAttribute("breadCrumb", breadCrumbViewDtoList);
        model.addAttribute("files", contents);
        return "gallery";
    }
}
