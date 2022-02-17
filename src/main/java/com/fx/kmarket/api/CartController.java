package com.fx.kmarket.api;


import com.fx.kmarket.api.request.CartCheckoutHttpRequest;
import com.fx.kmarket.api.response.CartCheckoutHttpResponse;
import com.fx.kmarket.service.CartService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private Logger log = LoggerFactory.getLogger(CartController.class);

    private CartService cartService;

    @Autowired
    public void setCartService(CartService cartService){
        this.cartService = cartService;
    }

    @RequestMapping(path = "/checkout", method = RequestMethod.POST, produces = "application/json")
    @ApiOperation("When receieve a list of code we generate the total price, if offers we calculate the price with offers")
    @ResponseStatus(HttpStatus.OK)
    public CartCheckoutHttpResponse checkout(@RequestBody CartCheckoutHttpRequest request) {
        return cartService.checkout(request);
    }
}
