package com.weshopifyplatform.service;

import com.weshopifyplatform.bean.BrandsBean;
import com.weshopifyplatform.bean.CategoryBean;
import com.weshopifyplatform.exception.ApiException;
import com.weshopifyplatform.model.Brands;
import com.weshopifyplatform.outbound.CategoriesApiClient;
import com.weshopifyplatform.repo.BrandsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BrandsServiceImpl implements BrandsService{
    private BrandsRepository brandsRepository;
    private CategoriesApiClient categoriesApiClient;
     @Override
    public BrandsBean createBrands(BrandsBean brandsBean) {
        Brands brand = beanToEntityConvert(brandsBean);
        // insert method in mongo repo works only for new record else throw duplicate id exception
        brand = brandsRepository.insert(brand);
        log.info("==========================New Brand saved as:-{} ====================================",brand);
        brandsBean = entityToBeanConverter(brand);
        return brandsBean;
    }

    @Override
    public BrandsBean updateBrands(BrandsBean brandsBean) {
        Brands brands = beanToEntityConvert(brandsBean);
        //save will do update, if id does not exist will call insert internally.
        brands = brandsRepository.save(brands);
        brandsBean = entityToBeanConverter(brands);
        return brandsBean;
    }

    @Override
    public List<BrandsBean> findAllBrands() {
        List<Brands> brandsList = brandsRepository.findAll();
        if(!CollectionUtils.isEmpty(brandsList))
            return brandsList.parallelStream().map(this::entityToBeanConverter).toList();
        throw new ApiException("No Brand found in Data base!!",
                HttpStatus.BAD_REQUEST.value(),LocalDateTime.now());
    }

    @Override
    public BrandsBean findBrandsById(String id) {
        var optionalBrand = brandsRepository.findById(id);
        if(optionalBrand.isPresent())
            return entityToBeanConverter(optionalBrand.get());
        throw new ApiException("No Brand exists in Data base for id :-"+id,
                HttpStatus.BAD_REQUEST.value(),LocalDateTime.now());
    }

    @Override
    public void deleteBrandsById(String id) {
        long beforeDelete = brandsRepository.count();
        brandsRepository.deleteById(id);
        long afterDelete = brandsRepository.count();
        if(afterDelete == beforeDelete)
            throw new ApiException("Unable to delete the brand, id :-"+id,
                    HttpStatus.BAD_REQUEST.value(),LocalDateTime.now());    }
    @Override
    public void deleteAllBrands(){
        brandsRepository.deleteAll();
    }

    private BrandsBean entityToBeanConverter(Brands brands) {

        BrandsBean brandsBean = new BrandsBean();
        brandsBean.setId(brands.getId());
        brandsBean.setName(brands.getName());
        brandsBean.setCategories(brands.getCategories());
        return brandsBean;
    }

    private Brands beanToEntityConvert(BrandsBean brandsBean) {
        Brands brands = new Brands();
        brands.setId(brandsBean.getId());
        brands.setName(brandsBean.getName());
        brands.setCategories(brandsBean.getCategories());
        return brands;
    }
}
