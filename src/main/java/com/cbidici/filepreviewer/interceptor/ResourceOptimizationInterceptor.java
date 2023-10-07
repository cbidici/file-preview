package com.cbidici.filepreviewer.interceptor;

import com.cbidici.filepreviewer.service.ContentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class ResourceOptimizationInterceptor implements HandlerInterceptor {

    private final ContentService contentService;

    @Autowired
    public ResourceOptimizationInterceptor(ContentService contentService) {
        this.contentService = contentService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/resources/").length == 1 ? "" : requestURL.split("/resources/")[1];

        contentService.getContent(URLDecoder.decode(path, StandardCharsets.UTF_8));
        return true;
    }
}
