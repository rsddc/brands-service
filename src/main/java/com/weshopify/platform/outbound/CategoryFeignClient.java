package com.weshopify.platform.outbound;

import com.weshopify.platform.bean.CategoryBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client in spring boot 3 has issues with spring-cloud-version.
 * Even this inerceptor class in config package has issue of thread local.
 * In order to set the token into the header it uses thread local in the call of
 * request attributes . But due to feign configuration it shows attribute null
 * because of internal mismatch due to thread local.get.
 *
 * If nothing is working than try this:-
 * Remove interceptor class from config and hystrix enabled true from pom.
 * Try to set feign client header manually with the help of below commented method.
 * set it like @RequestHeader(k,v) pass in findBycategoryId method of feign call.
 * Lots of refcorting would be done. Follow narsi:--53 where security applied to
 * rest and feign.
 *
 * So current impl is working 100% but threadlocal provides attributes internally.
 * if due to thread attributes are null so it will be failed.
 *
 * In case of debug:- debug both brand and fetchAllCategoryById method in category service.
 * or if required only put a debug in category service only. in case of putmapping it may be helpful.
 *
 * Feign client is in use in case of post and put. to fetch the categories.
 *
 */

//@FeignClient(value = "CategoryFeignClient",url = "http://localhost:5016")
//session52-at 2:03:35. To call it via eureka service registry use service id in eureka as value
//instead of url . also put the name attribute.
@FeignClient(value = "WESHOPIFY-CATEGORY-SERVICE",name = "WESHOPIFY-CATEGORY-SERVICE")
public interface CategoryFeignClient {
   @GetMapping("/categories/{id}")
   ResponseEntity<CategoryBean> findCategoryById(@PathVariable("id") int id);

//    default   Map<String,String> authHeader(HttpServletRequest httpServletRequest){
//       httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
//       Map<String,String> authHeader = new HashMap<>();
//       authHeader.put(HttpHeaders.AUTHORIZATION,httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
//      return authHeader;
//   }
}
