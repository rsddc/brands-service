package com.weshopifyplatform.outbound;

import com.weshopifyplatform.bean.CategoryBean;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CategoriesApiClient {
    private RestTemplate restTemplate;
    @Value("${services.categories.base-uri}")
    private String categoryUrl;
    private RedisTemplate<String,String> redisTemplate;
    private HashOperations<String,String,String> hashOperations;
    private String accessToken;
    public CategoriesApiClient(RestTemplate restTemplate, RedisTemplate<String, String> redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
        hashOperations = this.redisTemplate.opsForHash();
    }

    //Fetch categories by id. findCategoryByid.
    public Set<CategoryBean> fetchCategoryById(Set<CategoryBean> categoryBeanUI, HttpServletRequest httpServletRequest){
        //prepare header. with bearer token
        accessToken = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);

        Set<CategoryBean> categoryBeanObj = categoryBeanUI.stream().mapToInt(CategoryBean::getId).
                mapToObj(this::getResponseEntity).collect(Collectors.toSet());
        return categoryBeanObj;
    }

    private CategoryBean getResponseEntity(int categoryId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION,accessToken);
        HttpEntity<String> requestBody = new HttpEntity<>(headers);
        //Rest template exchange call. Don't worry about performance brand creation is not frequent operation.
        ParameterizedTypeReference<CategoryBean> typeRef = new ParameterizedTypeReference<CategoryBean>() {};
        ResponseEntity<CategoryBean> exchange = restTemplate.exchange(categoryUrl+"/"+categoryId,
                HttpMethod.GET, requestBody, typeRef);
        Objects.requireNonNull(exchange);
        if(HttpStatus.OK.value() == exchange.getStatusCode().value()){
            CategoryBean body =  exchange.getBody();
            return body;
        }
        return null;
    }







}
