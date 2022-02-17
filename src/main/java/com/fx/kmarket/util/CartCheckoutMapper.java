package com.fx.kmarket.util;

import com.fx.kmarket.api.response.CartCheckoutHttpResponse;
import com.fx.kmarket.api.response.ProductHttpResponse;
import com.fx.kmarket.model.Product;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class CartCheckoutMapper {

    private static final String currency = "£";


    public static CartCheckoutHttpResponse mapToHttpResponse(List<Product> productsFromDb, Double totalPrice) {

        CartCheckoutHttpResponse cartCheckoutHttpResponse = new CartCheckoutHttpResponse();

        cartCheckoutHttpResponse.setTotalPrice(generatePrice(totalPrice));

        // Mapping the List of Products Model to the Products HttpResponse to return it as a Json
        cartCheckoutHttpResponse.setProducts(
                productsFromDb
                        .stream()
                        .map(
                            product -> {
                                String price = generatePrice(product.getPrice());
                                return new ProductHttpResponse(product.getName(),product.getCode(),price);
                            })
                        .collect(Collectors.toList()));


        return cartCheckoutHttpResponse;
    }

    private static String generatePrice(Double price) {
        return currency.concat(BigDecimal.valueOf(price)
                .setScale(2, RoundingMode.HALF_UP)
                .toString());
    }

}
