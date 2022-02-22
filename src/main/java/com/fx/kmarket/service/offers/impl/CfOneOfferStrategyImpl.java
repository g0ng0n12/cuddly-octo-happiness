package com.fx.kmarket.service.offers.impl;

import com.fx.kmarket.service.offers.OfferStrategy;
import com.fx.kmarket.service.offers.OffersName;
import org.springframework.stereotype.Component;

@Component
public class CfOneOfferStrategyImpl implements OfferStrategy {

    @Override
    public OffersName getOfferName() {
        return OffersName.CF1;
    }

    @Override
    public Double applyOffer(Double price, Long total) {
        return (price *  (( total >= 3 ) ?  2.0/3 : 1)) * total;
    }
}