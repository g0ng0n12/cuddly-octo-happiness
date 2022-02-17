package com.fx.kmarket.service;

import com.fx.kmarket.api.request.CartCheckoutHttpRequest;
import com.fx.kmarket.api.response.CartCheckoutHttpResponse;

public interface CartService {

    CartCheckoutHttpResponse checkout(CartCheckoutHttpRequest request);

}
