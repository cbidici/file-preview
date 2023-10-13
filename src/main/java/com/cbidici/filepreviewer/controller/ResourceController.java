package com.cbidici.filepreviewer.controller;

import com.cbidici.filepreviewer.model.response.ResourcePageResponse;
import com.cbidici.filepreviewer.model.response.ResourceResponse;
import com.cbidici.filepreviewer.model.response.factory.ResourceResponseFactory;
import com.cbidici.filepreviewer.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceController {

  private final ResourceService service;
  private final ResourceResponseFactory factory;

  @GetMapping({"", "/**"})
  public ResponseEntity<ResourcePageResponse> resources(HttpServletRequest request) {
    String requestURL = request.getRequestURL().toString();
    String path = requestURL.split("/resources/").length == 1 ? "" : requestURL.split("/resources/")[1];

    List<ResourceResponse> contents = service.getChildren(URLDecoder.decode(path, StandardCharsets.UTF_8))
        .stream()
        .map(factory::getResourceResponse)
        .collect(Collectors.toList());

    return ResponseEntity.ok(ResourcePageResponse.builder().resources(contents).build());
  }

}
