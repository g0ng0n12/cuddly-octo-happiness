package com.fx.kmarket.service.offers.impl;

import com.fx.kmarket.service.offers.OfferStrategy;
import com.fx.kmarket.service.offers.OffersName;
import org.springframework.stereotype.Component;

@Component
public class SrOneOfferStrategyImpl implements OfferStrategy {

    @Override
    public OffersName getOfferName() {
        return OffersName.SR1;
    }

    @Override
    public Double applyOffer(Double price, Long total) {
        return (total >= 3 ? 4.5: price) * total;
    }
}