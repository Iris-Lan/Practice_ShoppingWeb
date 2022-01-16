package com.Iris.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchOrderReqModel implements Serializable {

    private String orderId;

    private String productName;

    private LocalDateTime orderDate;

    // The parameters below are related to Page setting.
    @NotBlank
    private String ordering = "DESC"; //DESC or ASC

    @NotBlank
    private int displayPageNumber;

    @NotBlank
    private int numberOfRowsPerPage;
}
