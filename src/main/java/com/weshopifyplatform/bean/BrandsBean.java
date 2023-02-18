package com.weshopifyplatform.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class BrandsBean {

    private int id;
    private String name;
    private String logoPath;
    private Set<String> categories;

}
