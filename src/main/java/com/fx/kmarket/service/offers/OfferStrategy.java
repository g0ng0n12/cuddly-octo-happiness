package com.fx.kmarket.service.offers;


public interface OfferStrategy {

    Double applyOffer(Double price, Long total);

    OffersName getOfferName();

}
