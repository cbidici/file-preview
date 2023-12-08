package com.cbidici.filepreview.controller;

import com.cbidici.filepreview.model.enm.ResourceType;
import com.cbidici.filepreview.service.ResourceService;
import jakarta.servlet.http.HttpServletRequest;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {
  private final ResourceService service;
  @GetMapping("/resources/init/**")
  public ResponseEntity<Void> resources(HttpServletRequest request) {
    String requestURL = request.getRequestURL().toString();
    String path = requestURL.split("/resources/init/").length == 1 ? "" : requestURL.split("/resources/init/")[1];

    init(Path.of(URLDecoder.decode(path, StandardCharsets.UTF_8)));
    return ResponseEntity.ok().build();
  }

  private void init(Path path) {
    var resources = service.getChildren(path.toString(),0, Integer.MAX_VALUE);
    resources.stream().filter(r -> r.getType() == ResourceType.DIRECTORY).forEach(r -> init(Path.of(r.getPath())));
  }
}
