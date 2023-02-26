package com.weshopify.platform.service;

import com.weshopify.platform.bean.CategoryBean;
import com.weshopify.platform.bean.BrandsBean;

import java.util.List;
import java.util.Set;

public interface BrandsService {

    BrandsBean createBrands(BrandsBean brandsBean);
    BrandsBean updateBrands(BrandsBean brandsBean);

    List<BrandsBean> findAllBrands();

    BrandsBean findBrandsById(String id);

    void deleteBrandsById(String id);


    void deleteAllBrands();

    Set<CategoryBean> findCategoryById(BrandsBean brandsBean);
}
