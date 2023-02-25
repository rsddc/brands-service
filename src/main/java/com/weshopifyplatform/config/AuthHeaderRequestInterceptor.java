package com.weshopifyplatform.config;

import com.weshopifyplatform.exception.ApiException;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Configuration
@Slf4j
public class AuthHeaderRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {



        /** First aquire it and add it in template request. */
        log.info("===============Inside:-"+this.getClass().getName()+" apply token in feign client request header=========");
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
       if(requestAttributes == null){
           log.info("========request attributes are null===============");
           return;
       }
        HttpServletRequest  httpServletRequest = requestAttributes.getRequest();
       if(httpServletRequest == null){
           log.info("=================http servlet request is null==================");
           return;
       }
        String jwtAccessToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.hasText(jwtAccessToken))
            template.header(HttpHeaders.AUTHORIZATION,jwtAccessToken);
        else throw new ApiException("No token set in feign header for category service call",
                HttpStatus.UNAUTHORIZED.value(), LocalDateTime.now());

    }
}
