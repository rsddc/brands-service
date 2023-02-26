package com.weshopify.platform.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandsBean implements Serializable {



    @Serial
    private static final long serialVersionUID = -717245561605299015L;
    private String id;
    private String name;
    private String logoPath;
    private Set<CategoryBean> categories;

}
