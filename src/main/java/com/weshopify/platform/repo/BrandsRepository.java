package com.weshopify.platform.repo;

import com.weshopify.platform.model.Brands;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends MongoRepository<Brands, String> {
}
