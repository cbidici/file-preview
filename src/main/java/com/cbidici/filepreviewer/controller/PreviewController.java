package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.model.response.BreadCrumbResponse;
import com.cbidici.filepreviewer.model.response.ResourceResponse;
import com.cbidici.filepreviewer.model.response.factory.ResourceResponseFactory;
import com.cbidici.filepreviewer.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class PreviewController {

    private final ResourceService resourceService;
    private final ResourceResponseFactory factory;

    @GetMapping("/resources/**")
    public String resources(HttpServletRequest request, Model model) {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/resources/").length == 1 ? "" : requestURL.split("/resources/")[1];

        StringBuilder breadCrumbUrlBuilder = new StringBuilder();
        breadCrumbUrlBuilder.append("/resources");
        String[] directories = path.split("/");
        List<BreadCrumbResponse> breadCrumbResponseList = new ArrayList<>();
        breadCrumbResponseList.add(BreadCrumbResponse.builder().name("Home").url(breadCrumbUrlBuilder.toString()).build());
        for(String directory : directories) {
            if(!directory.isEmpty()) {
                breadCrumbUrlBuilder.append("/").append(URLDecoder.decode(directory, StandardCharsets.UTF_8));
                breadCrumbResponseList.add(BreadCrumbResponse.builder().name(URLDecoder.decode(directory, StandardCharsets.UTF_8)).url(breadCrumbUrlBuilder.toString()).build());
            }
        }

        List<ResourceResponse> contents = resourceService.getChildren(URLDecoder.decode(path, StandardCharsets.UTF_8))
                .stream()
                .map(factory::getResourceResponse)
                .collect(Collectors.toList());

        model.addAttribute("breadCrumb", breadCrumbResponseList);
        model.addAttribute("resources", contents);
        return "resources";
    }
}
