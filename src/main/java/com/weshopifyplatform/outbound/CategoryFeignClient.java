package com.weshopifyplatform.outbound;

import com.weshopifyplatform.bean.CategoryBean;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.HashMap;
import java.util.Map;

@FeignClient(value = "CategoryFeignClient",url = "http://localhost:5016")
public interface CategoryFeignClient {
   @GetMapping("/categories/{id}")
   ResponseEntity<CategoryBean> findCategoryById(String id, @RequestHeader final Map<String, String> headerMap);

    default   Map<String,String> authHeader(HttpServletRequest httpServletRequest){
       httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
       Map<String,String> authHeader = new HashMap<>();
       authHeader.put(HttpHeaders.AUTHORIZATION,httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
      return authHeader;
   }
}
