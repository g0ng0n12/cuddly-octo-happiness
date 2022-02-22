package com.fx.kmarket.service;

import com.fx.kmarket.api.request.CartCheckoutHttpRequest;
import com.fx.kmarket.api.response.CartCheckoutHttpResponse;
import com.fx.kmarket.model.Product;
import com.fx.kmarket.repository.ProductRepository;
import com.fx.kmarket.service.impl.CartServiceImpl;
import com.fx.kmarket.service.offers.OfferStrategy;
import com.fx.kmarket.service.offers.OfferStrategyFactory;
import com.fx.kmarket.service.offers.impl.CfOneOfferStrategyImpl;
import com.fx.kmarket.service.offers.impl.GrOneOfferStrategyImpl;
import com.fx.kmarket.service.offers.impl.SrOneOfferStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class CartServiceTest {

    @Mock
    ProductRepository productRepository;


    @InjectMocks
    CartServiceImpl cartServiceImpl;

    @BeforeEach
    void init()  {
        OfferStrategy cfOneOfferImpl = new CfOneOfferStrategyImpl();
        OfferStrategy grOneOfferImpl = new GrOneOfferStrategyImpl();
        OfferStrategy srOneOfferImpl = new SrOneOfferStrategyImpl();
        Set<OfferStrategy> offerStrategySet = new HashSet<>();
        offerStrategySet.add(cfOneOfferImpl);
        offerStrategySet.add(grOneOfferImpl);
        offerStrategySet.add(srOneOfferImpl);
        cartServiceImpl.setOfferStrategyFactory(new OfferStrategyFactory(offerStrategySet));
    }

    @Test
    @DisplayName("buy-one-get-one-free offer and of green tea With Other products")
    public void buyOneGetOneForFreeWithMultipleItems(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1", "SR1", "GR1","GR1","CF1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",3.11,"Green Tea");
        Product sr1 = new Product("SR1",5.00,"Strawberries");
        Product cf1 = new Product("CF1",11.23,"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findByCodeIn(productsHttpRequest)).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals("£22.45", checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("buy-one-get-one-free offers and of green tea")
    public void buyOneGetOneForFree(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","GR1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",3.11,"Green Tea");

        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);

        Mockito.when(productRepository.findByCodeIn(productsHttpRequest)).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals("£3.11", checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("Strawberries get discount on bulk purchases 3 or more")
    public void strawberriesDiscountForBulkPurchases(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("SR1","SR1","GR1","SR1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",3.11,"Green Tea");
        Product sr1 = new Product("SR1",5.00,"Strawberries");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);

        Mockito.when(productRepository.findByCodeIn(productsHttpRequest)).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals("£16.61", checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("Price of all coffees should drop to two thirds of the original price.")
    public void coffeeShouldDropTwoThirdsOfTheOriginalPrice(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","CF1","SR1","CF1","CF1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",3.11,"Green Tea");
        Product sr1 = new Product("SR1",5.00,"Strawberries");
        Product cf1 = new Product("CF1",11.23,"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findByCodeIn(productsHttpRequest)).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals("£30.57", checkoutHttpResponse.getTotalPrice());
    }

    @Test
    @DisplayName("Getting the Total Price when we receive one of each product")
    public void gettingOneOfEach(){
        List<String> productsHttpRequest =  new ArrayList<>(Arrays.asList("GR1","CF1","SR1"));
        CartCheckoutHttpRequest newCheckoutRequest = new CartCheckoutHttpRequest(productsHttpRequest);

        Product gr1 = new Product("GR1",3.11,"Green Tea");
        Product sr1 = new Product("SR1",5.00,"Strawberries");
        Product cf1 = new Product("CF1",11.23,"Coffee");
        List<Product> productListMock = new ArrayList<>();
        productListMock.add(gr1);
        productListMock.add(sr1);
        productListMock.add(cf1);

        Mockito.when(productRepository.findByCodeIn(productsHttpRequest)).thenReturn(productListMock);

        CartCheckoutHttpResponse checkoutHttpResponse = cartServiceImpl.checkout(newCheckoutRequest);

        assertEquals("£19.34", checkoutHttpResponse.getTotalPrice());
    }

}
