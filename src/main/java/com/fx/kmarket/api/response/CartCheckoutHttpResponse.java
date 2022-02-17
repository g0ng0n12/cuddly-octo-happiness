package com.fx.kmarket.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartCheckoutHttpResponse {

    private List<ProductHttpResponse> products;

    private String totalPrice;
}
