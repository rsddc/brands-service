package com.weshopifyplatform.service;

import com.weshopifyplatform.bean.BrandsBean;
import com.weshopifyplatform.model.Brands;
import com.weshopifyplatform.repo.BrandsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BrandsServiceImpl implements BrandsService{
    private BrandsRepository brandsRepository;
    @Override
    public BrandsBean createBrands(BrandsBean brandsBean) {
        Brands brands = beanToEntityConvert(brandsBean);
        // insert method in mongo repo works only for new record else throw duplicate id exception
        brands = brandsRepository.insert(brands);
        brandsBean = entityToBeanConverter(brands);
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
        var list = brandsRepository.findAll();
        if(!CollectionUtils.isEmpty(list))
            return list.parallelStream().map(this::entityToBeanConverter).toList();
        throw new RuntimeException("No Brand found!!");
    }

    @Override
    public BrandsBean findBrandsById(int id) {
        var optionalBrand = brandsRepository.findById(id).get();
        if(optionalBrand != null) return entityToBeanConverter(optionalBrand);
        throw new RuntimeException("no Brand");
    }

    @Override
    public void deleteBrandsById(int id) {
        long beforeDelete = brandsRepository.count();
        brandsRepository.deleteById(id);
        long afterDelete = brandsRepository.count();
        if(afterDelete == beforeDelete)
        throw new RuntimeException("either brand not exists or invalid id:- "+id);
    }

    private BrandsBean entityToBeanConverter(Brands brands) {
        return BrandsBean.builder().id(brands.getId())
                .name(brands.getName()).logoPath(brands.getLogoPath())
                .categories(brands.getCategories()).build();
    }

    private Brands beanToEntityConvert(BrandsBean brandsBean) {

        return Brands.builder().id(brandsBean.getId()).
                               name(brandsBean.getName()).logoPath(brandsBean.getLogoPath()).
                categories(brandsBean.getCategories()).build();
    }
}
