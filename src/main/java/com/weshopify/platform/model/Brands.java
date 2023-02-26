package com.weshopify.platform.model;

import com.weshopify.platform.bean.CategoryBean;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
@Document(collection = "brands")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Brands implements Serializable {
    @Serial
    private static final long serialVersionUID = 3178975107325835060L;

    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private String logoPath;
    private Set<CategoryBean> categories;

}
