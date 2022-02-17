package com.fx.kmarket.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@ApiModel(description = "Class representing the")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckoutHttpRequest {

    @ApiModelProperty(notes = "Products", example = "\"SR1\",\"CF1\",\"GR1\"", required = true, position = 4)
    private List<String> products;
}
