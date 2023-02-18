package com.weshopifyplatform.service;

import com.weshopifyplatform.bean.BrandsBean;

import java.util.List;

public interface BrandsService {

    public BrandsBean createBrands(BrandsBean brandsBean);
    public BrandsBean updateBrands(BrandsBean brandsBean);

    public List<BrandsBean> findAllBrands();

    public BrandsBean findBrandsById(int id);

    public void deleteBrandsById(int id);



}
