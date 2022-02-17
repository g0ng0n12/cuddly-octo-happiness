package com.fx.kmarket.service;

import com.fx.kmarket.api.request.CartCheckoutHttpRequest;
import com.fx.kmarket.api.response.CartCheckoutHttpResponse;
import com.fx.kmarket.model.Product;
import com.fx.kmarket.repository.ProductRepository;
import com.fx.kmarket.service.impl.CartServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    CartServiceImpl cartServiceImpl;

    @Test
    @DisplayName("buy-one-get-one-free offers and of green tea With OtherItems")
    public void buyOneGetOneForFreeWithMultipleItems(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1", "SR1", "GR1","GR1","CF1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",new BigDecimal("3.11"),"Green Tea");
        Product sr1 = new Product("SR1",new BigDecimal("5.00"),"Strawberries");
        Product cf1 = new Product("CF1",new BigDecimal("11.23"),"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findAll()).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals(new BigDecimal("22.45"), checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("buy-one-get-one-free offers and of green tea")
    public void buyOneGetOneForFree(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","GR1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",new BigDecimal("3.11"),"Green Tea");
        Product sr1 = new Product("SR1",new BigDecimal("5.00"),"Strawberries");
        Product cf1 = new Product("CF1",new BigDecimal("11.23"),"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findAll()).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals(new BigDecimal("3.11"), checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("Strawberries get discount on bulk purchases 3 or more")
    public void strawberriesDiscountForBulkPurchases(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("SR1","SR1","GR1","SR1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",new BigDecimal("3.11"),"Green Tea");
        Product sr1 = new Product("SR1",new BigDecimal("5.00"),"Strawberries");
        Product cf1 = new Product("CF1",new BigDecimal("11.23"),"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findAll()).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals(new BigDecimal("16.61"), checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("Price of all coffees should drop to two thirds of the original price.")
    public void coffeeShouldDropTwoThirdsOfTheOriginalPrice(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","CF1","SR1","CF1","CF1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",new BigDecimal("3.11"),"Green Tea");
        Product sr1 = new Product("SR1",new BigDecimal("5.00"),"Strawberries");
        Product cf1 = new Product("CF1",new BigDecimal("11.23"),"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findAll()).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals(new BigDecimal("30.57"), checkoutHttpResponse.getTotalPrice());
    }

}
