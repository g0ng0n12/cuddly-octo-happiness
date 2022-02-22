package com.fx.kmarket.service.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OfferStrategyFactory {

    private Map<OffersName, OfferStrategy> offers;

    @Autowired
    public OfferStrategyFactory(Set<OfferStrategy> offerStrategySet) {
        createStrategy(offerStrategySet);
    }

    public Map<OffersName, OfferStrategy> findStrategies(List<String> offersFromRequest) {
        return offers.entrySet().stream()
                .filter(offer -> offersFromRequest.contains(offer.getValue().getOfferName().toString()))
                .collect(Collectors.toMap(x -> x.getKey(), x -> x.getValue()));
    }

    // we fill the offers in a map so we can find it with the get of the map implementation O(1)
    private void createStrategy(Set<OfferStrategy> strategySet) {
        offers = new HashMap<OffersName, OfferStrategy>();
        strategySet.forEach(
                strategy ->offers.put(strategy.getOfferName(), strategy));
    }
}
