package com.fx.kmarket.service;

import com.fx.kmarket.service.offers.OfferStrategy;
import com.fx.kmarket.service.offers.OfferStrategyFactory;
import com.fx.kmarket.service.offers.OffersName;
import com.fx.kmarket.service.offers.impl.CfOneOfferStrategyImpl;
import com.fx.kmarket.service.offers.impl.GrOneOfferStrategyImpl;
import com.fx.kmarket.service.offers.impl.SrOneOfferStrategyImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OffersStrategyTest {

    OfferStrategyFactory offerStrategyFactory;

    @BeforeEach
    void init()  {
        OfferStrategy cfOneOfferImpl = new CfOneOfferStrategyImpl();
        OfferStrategy grOneOfferImpl = new GrOneOfferStrategyImpl();
        OfferStrategy srOneOfferImpl = new SrOneOfferStrategyImpl();
        Set<OfferStrategy> offerStrategySet = new HashSet<>();
        offerStrategySet.add(cfOneOfferImpl);
        offerStrategySet.add(grOneOfferImpl);
        offerStrategySet.add(srOneOfferImpl);
        this.offerStrategyFactory = new OfferStrategyFactory(offerStrategySet);
    }


    @Test
    public void testGrOneOfferImpl(){
        String grOne = "GR1";
        List<String> products =  new ArrayList<>(Arrays.asList(grOne, grOne));
        Map<OffersName, OfferStrategy> offerStrategies= offerStrategyFactory.findStrategies(products);
        OfferStrategy grOneLogicImplementation = offerStrategies.get(OffersName.valueOf(grOne));

        // buy-one-get-one-free of GR1 Product
        assertEquals(3.11, grOneLogicImplementation.applyOffer(3.11, 2L));
    }

    @Test
    public void testCfOneOfferImpl(){
        String cfOne = "CF1";
        List<String> products =  new ArrayList<>(Arrays.asList(cfOne, cfOne));
        Map<OffersName, OfferStrategy> offerStrategies= offerStrategyFactory.findStrategies(products);
        OfferStrategy cfOneLogicImplementation = offerStrategies.get(OffersName.valueOf(cfOne));

        //  If you buy 3 or more CF1, the price of all CF1 should drop to two thirds of the original price.
        assertEquals(22.46, cfOneLogicImplementation.applyOffer(11.23, 3L));
    }

    @Test
    public void testSrOneOfferImpl(){
        String grOne = "SR1";
        List<String> products =  new ArrayList<>(Arrays.asList(grOne, grOne));
        Map<OffersName, OfferStrategy> offerStrategies= offerStrategyFactory.findStrategies(products);
        OfferStrategy sfOneLogicImplementation = offerStrategies.get(OffersName.valueOf(grOne));

        // If you buy 3 or more SR1, the price should drop to 4.50 per SR1.
        assertEquals(18.00, sfOneLogicImplementation.applyOffer(5.00, 4L));
    }
}
