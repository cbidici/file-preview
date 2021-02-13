package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.model.dto.BreadCrumbDto;
import com.cbidici.filepreviewer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PreviewController {

    FileService fileService;

    @Autowired
    public PreviewController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/gallery";
    }

    @GetMapping("/gallery/**")
    public String gallery(HttpServletRequest request, Model model) throws IOException, NoSuchAlgorithmException {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/gallery").length == 1 ? "" : requestURL.split("/gallery")[1];

        StringBuilder breadCrumbUrlBuilder = new StringBuilder();
        breadCrumbUrlBuilder.append("/gallery");
        String[] directories = path.split("/");
        List<BreadCrumbDto> breadCrumbDtoList = new ArrayList<>();
        breadCrumbDtoList.add(new BreadCrumbDto("Home", breadCrumbUrlBuilder.toString()));
        for(String directory : directories) {
            if(!directory.isEmpty()) {
                breadCrumbUrlBuilder.append("/"+URLDecoder.decode(directory));
                breadCrumbDtoList.add(new BreadCrumbDto(URLDecoder.decode(directory,"UTF-8"), breadCrumbUrlBuilder.toString()));
            }
        }

        model.addAttribute("breadCrumb", breadCrumbDtoList);
        model.addAttribute("files", fileService.getFiles(URLDecoder.decode(path, "UTF-8")));
        return "gallery";
    }
}
