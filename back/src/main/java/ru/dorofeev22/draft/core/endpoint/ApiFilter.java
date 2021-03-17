package ru.dorofeev22.draft.core.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class ApiFilter implements Filter {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final ApiCallLoggingHelper apiCallLoggingHelper;
    
    public ApiFilter(ApiCallLoggingHelper apiCallLoggingHelper) {
        this.apiCallLoggingHelper = apiCallLoggingHelper;
    }
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);
        log.info(apiCallLoggingHelper.createRequestInfo(multiReadRequest));
        chain.doFilter(multiReadRequest, response);
    }
    
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
    
}