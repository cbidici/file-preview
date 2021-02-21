package com.cbidici.filepreviewer.interceptor;

import com.cbidici.filepreviewer.service.ContentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ResourceOptimizationInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceOptimizationInterceptor.class);

    private final ContentService contentService;

    @Autowired
    public ResourceOptimizationInterceptor(ContentService contentService) {
        this.contentService = contentService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURL = request.getRequestURL().toString();
        String path = requestURL.split("/resource/").length == 1 ? "" : requestURL.split("/resource/")[1];

        contentService.getContent(path);
        return true;
    }
}
