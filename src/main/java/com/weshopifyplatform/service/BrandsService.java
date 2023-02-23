package com.weshopifyplatform.service;

import com.weshopifyplatform.bean.BrandsBean;

import java.util.List;

public interface BrandsService {

    BrandsBean createBrands(BrandsBean brandsBean);
    BrandsBean updateBrands(BrandsBean brandsBean);

    List<BrandsBean> findAllBrands();

    BrandsBean findBrandsById(String id);

    void deleteBrandsById(String id);


    void deleteAllBrands();
}
