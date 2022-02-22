package com.fx.kmarket.service.offers.impl;

import com.fx.kmarket.service.offers.OfferStrategy;
import com.fx.kmarket.service.offers.OffersName;
import org.springframework.stereotype.Component;


@Component
public class GrOneOfferStrategyImpl implements OfferStrategy {

    @Override
    public OffersName getOfferName() {
        return OffersName.GR1;
    }

    @Override
    public Double applyOffer(Double price, Long total) {
        return price * ((total / 2) + (total % 2));
    }
}
