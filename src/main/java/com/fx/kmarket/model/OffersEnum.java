package com.fx.kmarket.model;

public enum OffersEnum {

    GR1(){
        @Override
        public Double checkOffer(Double price, Long total) {
            return price * ((total / 2) + (total % 2));
        }
    },
    SR1(){
        @Override
        public Double checkOffer(Double price, Long total) {
            return (total >= 3 ? 4.5: price) * total;
        }
    },
    CF1(){
        @Override
        public Double checkOffer(Double price, Long total) {
            return (price *  (( total >= 3 ) ?  2.0/3 : 1)) * total;
        }
    };

    public abstract Double checkOffer(Double price, Long total);
}
