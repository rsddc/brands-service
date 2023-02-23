package com.weshopifyplatform.resource;

import com.weshopifyplatform.bean.BrandsBean;
import com.weshopifyplatform.bean.CategoryBean;
import com.weshopifyplatform.outbound.CategoriesApiClient;
import com.weshopifyplatform.outbound.CategoryFeignClient;
import com.weshopifyplatform.service.BrandsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@Slf4j
@SecurityRequirement(name = "brands-api")
public class BrandsResource {
    private BrandsService brandsService;

    private CategoriesApiClient categoriesApiClient;
//    private CategoryFeignClient categoryFeignClient;


    @PostMapping("/brands")
    public ResponseEntity<BrandsBean> newBrand(@RequestBody @NonNull BrandsBean brandsBean,
                                               HttpServletRequest httpServletRequest) {
        Set<CategoryBean> categoryBean = categoriesApiClient.fetchCategoryById(brandsBean.getCategories(), httpServletRequest);
//        Set<CategoryBean> categoryBean = categoryFeignClient.findCategoryById(brandsBean.getCategories(),
//                categoryFeignClient.authHeader(httpServletRequest));
        brandsBean.setCategories(categoryBean);
        BrandsBean newBrandBean = brandsService.createBrands(brandsBean);
        return ResponseEntity.ok(newBrandBean);
    }
    @PutMapping("/brands")
    public ResponseEntity<BrandsBean> editBrand(@RequestBody @NonNull BrandsBean brandsBean) {
        var newBrandBean = brandsService.updateBrands(brandsBean);
        return ResponseEntity.ok(newBrandBean);
    }
    @GetMapping("/brands")
    public ResponseEntity<List<BrandsBean>> fetchAllBrands() {
      log.info("====================Fetching all brands=============================");
        List<BrandsBean> brandsBeanList = brandsService.findAllBrands();
        return ResponseEntity.ok(brandsBeanList);
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandsBean> fetchBrandsById(@PathVariable String id) {
        var brandsBean = brandsService.findBrandsById(id);
        return ResponseEntity.ok(brandsBean);
    }
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<String> deleteBrandsById(@PathVariable String id) {
        brandsService.deleteBrandsById(id);
        return ResponseEntity.ok("id" + id + " deleted successfully");
    }

    @DeleteMapping("/brands/")
    public ResponseEntity<String> deleteAllBrands() {
        brandsService.deleteAllBrands();
        return ResponseEntity.ok("All Brands deleted successfully");
    }

}
