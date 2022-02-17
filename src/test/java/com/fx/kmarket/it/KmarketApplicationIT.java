package com.fx.kmarket.it;

import com.fx.kmarket.api.request.CartCheckoutHttpRequest;
import com.fx.kmarket.api.response.CartCheckoutHttpResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class KmarketApplicationIT extends BaseApiApplication{

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("buy-one-get-one-free offers and of green tea")
    public void buyOneGetOneForFree() throws Exception {

        String hostUrl = "http://localhost:" + port + "/api/v1/carts/checkout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","GR1"));
        CartCheckoutHttpRequest checkoutHttpRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        // Checkout
        ResponseEntity<CartCheckoutHttpResponse> cartCheckoutHttpResponse =
                this.restTemplate.postForEntity(hostUrl,checkoutHttpRequest, CartCheckoutHttpResponse.class);

        assertThat(cartCheckoutHttpResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals("£3.11", cartCheckoutHttpResponse.getBody().getTotalPrice());
    }

    @Test
    @DisplayName("buy-one-get-one-free offers and of green tea With OtherItems")
    public void buyOneGetOneForFreeWithMultipleItems(){

        String hostUrl = "http://localhost:" + port + "/api/v1/carts/checkout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1", "SR1", "GR1","GR1","CF1"));
        CartCheckoutHttpRequest checkoutHttpRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        // Checkout
        ResponseEntity<CartCheckoutHttpResponse> cartCheckoutHttpResponse =
                this.restTemplate.postForEntity(hostUrl,checkoutHttpRequest, CartCheckoutHttpResponse.class);

        assertThat(cartCheckoutHttpResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals("£22.45", cartCheckoutHttpResponse.getBody().getTotalPrice());
    }

    @Test
    @DisplayName("Strawberries get discount on bulk purchases 3 or more")
    public void strawberriesDiscountForBulkPurchases(){

        String hostUrl = "http://localhost:" + port + "/api/v1/carts/checkout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("SR1","SR1","GR1","SR1"));
        CartCheckoutHttpRequest checkoutHttpRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        // Checkout
        ResponseEntity<CartCheckoutHttpResponse> cartCheckoutHttpResponse =
                this.restTemplate.postForEntity(hostUrl,checkoutHttpRequest, CartCheckoutHttpResponse.class);

        assertThat(cartCheckoutHttpResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals("£16.61", cartCheckoutHttpResponse.getBody().getTotalPrice());
    }

    @Test
    @DisplayName("Price of all coffees should drop to two thirds of the original price.")
    public void coffeeShouldDropTwoThirdsOfTheOriginalPrice(){
        String hostUrl = "http://localhost:" + port + "/api/v1/carts/checkout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","CF1","SR1","CF1","CF1"));
        CartCheckoutHttpRequest checkoutHttpRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        // Checkout
        ResponseEntity<CartCheckoutHttpResponse> cartCheckoutHttpResponse =
                this.restTemplate.postForEntity(hostUrl,checkoutHttpRequest, CartCheckoutHttpResponse.class);

        assertThat(cartCheckoutHttpResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals("£30.57", cartCheckoutHttpResponse.getBody().getTotalPrice());
    }

    @Test
    @DisplayName("Getting the Total Price when we receive one of each product")
    public void gettingOneOfEach(){
        String hostUrl = "http://localhost:" + port + "/api/v1/carts/checkout";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","CF1","SR1"));
        CartCheckoutHttpRequest checkoutHttpRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        // Checkout
        ResponseEntity<CartCheckoutHttpResponse> cartCheckoutHttpResponse =
                this.restTemplate.postForEntity(hostUrl,checkoutHttpRequest, CartCheckoutHttpResponse.class);

        assertThat(cartCheckoutHttpResponse.getStatusCode(), equalTo(HttpStatus.OK));
        assertEquals("£19.34", cartCheckoutHttpResponse.getBody().getTotalPrice());
    }
}
