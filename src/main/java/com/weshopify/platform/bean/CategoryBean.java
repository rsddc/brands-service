package com.weshopify.platform.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBean implements Serializable {


    @Serial
    private static final long serialVersionUID = 5845357022681520824L;
    private int id;
    private String name;
    private String alias;
    private String imagePath;
    private boolean enabled;
    private int parent;

}
