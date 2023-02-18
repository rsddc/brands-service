package com.weshopifyplatform.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;
@Document("brands")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
public class Brands implements Serializable {
    @Serial
    private static final long serialVersionUID = 3178975107325835060L;

    @Id
    private int id;

    private String name;
    private String logoPath;
    private Set<String> categories;




}
