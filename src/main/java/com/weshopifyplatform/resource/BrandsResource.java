package com.weshopifyplatform.resource;

import com.weshopifyplatform.bean.BrandsBean;
import com.weshopifyplatform.service.BrandsService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@SecurityScheme(name = "brands-api", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
@OpenAPIDefinition(info = @Info(title = "Brand API", version = "2.0", description = "Brand Resource"))
public class BrandsResource {
    private BrandsService brandsService;
    @PostMapping("/brands")
    public ResponseEntity<BrandsBean> newBrand(@RequestBody @NonNull BrandsBean brandsBean) {
        var newBrandBean = brandsService.createBrands(brandsBean);
        return ResponseEntity.ok(newBrandBean);
    }
    @PutMapping("/brands")
    public ResponseEntity<BrandsBean> editBrand(@RequestBody @NonNull BrandsBean brandsBean) {
        var newBrandBean = brandsService.updateBrands(brandsBean);
        return ResponseEntity.ok(newBrandBean);
    }
    @GetMapping("/brands")
    public ResponseEntity<List<BrandsBean>> fetchAllBrands() {
      log.info("Fetching all brands ");
        var brandsBeanList = brandsService.findAllBrands();
        return ResponseEntity.ok(brandsBeanList);
    }

    @GetMapping("/brands/{id}")
    public ResponseEntity<BrandsBean> fetchBrandsById(int id) {
        var brandsBean = brandsService.findBrandsById(id);
        return ResponseEntity.ok(brandsBean);
    }
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<String> deleteBrandsById(int id) {
        brandsService.deleteBrandsById(id);
        return ResponseEntity.ok("id" + id + " deleted successfully");
    }


}
