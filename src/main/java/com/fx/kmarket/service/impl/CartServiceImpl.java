package com.fx.kmarket.service.impl;

import com.fx.kmarket.api.request.CartCheckoutHttpRequest;
import com.fx.kmarket.api.response.CartCheckoutHttpResponse;
import com.fx.kmarket.service.offers.OfferStrategy;
import com.fx.kmarket.service.offers.OfferStrategyFactory;
import com.fx.kmarket.service.offers.OffersName;
import com.fx.kmarket.model.Product;
import com.fx.kmarket.repository.ProductRepository;
import com.fx.kmarket.service.CartService;
import com.fx.kmarket.util.CartCheckoutMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private ProductRepository productRepository;

    private OfferStrategyFactory offerStrategyFactory;


    @Autowired
    public void setProductRepository(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Autowired
    public void setOfferStrategyFactory( OfferStrategyFactory offerStrategyFactory){
        this.offerStrategyFactory = offerStrategyFactory;
    }


    @Override
    public CartCheckoutHttpResponse checkout(CartCheckoutHttpRequest request) {

        List<Product> productsFromDb = productRepository.findByCodeIn(request.getProducts());

        Map<OffersName, OfferStrategy> offersLogic =
                offerStrategyFactory.findStrategies(request.getProducts());

        // Map of Products Key=Code, Value=Price
        Map<String, Double> mapOfProducts = productsFromDb.stream()
                .collect(Collectors.toMap(Product::getCode, Product::getPrice));

        // Map of groupedProducts Key=Code, Value=quantityOfEachProduct
        Map<String, Long> groupedProducts = gettingGroupByOfCodeProductsAndQuantityOfEach(request);

        //Sending the products obtained from the DB and the total price generated from products to create the response
        return CartCheckoutMapper.mapToHttpResponse(productsFromDb, gettingTotalPrice(offersLogic, mapOfProducts, groupedProducts));
    }

    // Reducing the total price from
    private Double gettingTotalPrice(Map<OffersName, OfferStrategy> offersLogic, Map<String, Double> mapOfProducts, Map<String, Long> groupedProducts) {
        return groupedProducts.entrySet()
                .stream()
                .map(item ->
                        offersLogic.get(OffersName.valueOf(item.getKey()))
                            .applyOffer(mapOfProducts.get(item.getKey()), item.getValue()))
                .reduce(0.0, Double::sum);
    }

    // Checking How many products of each one do we have from the Request using the Collectors groupBy and the
    // Codes that we get from the request. Function.identity() returns its input arguments, in this case the codes that
    // we get from the requests
    private Map<String, Long> gettingGroupByOfCodeProductsAndQuantityOfEach(CartCheckoutHttpRequest request) {
        return request.getProducts().stream()
                .collect(Collectors.groupingBy( Function.identity(),
                        Collectors.counting()));
    }
}
