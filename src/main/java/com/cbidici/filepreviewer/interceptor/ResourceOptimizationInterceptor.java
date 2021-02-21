package com.cbidici.filepreviewer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ResourceOptimizationInterceptor implements HandlerInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceOptimizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LOG.error("Going to process");
        return true;
    }
}
