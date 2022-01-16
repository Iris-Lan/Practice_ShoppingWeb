package com.Iris.demo.model;

import com.Iris.demo.entity.Products;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaveOrderReqModel implements Serializable {

    @NotNull
    private List<Products> products;

    @NotBlank
    private  String memberUUID;

}
