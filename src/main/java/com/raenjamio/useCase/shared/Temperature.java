package com.raenjamio.useCase.shared;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Function;

/**
 * This class models the calculation of the number of beers according to the temperature
 */
public enum Temperature {
    HIGH(calculateTotalBeers("3")) {
        @Override
        public boolean isValid(Double temp) {
            return temp > 24;
        }
    },
    MEDIUM(calculateTotalBeers("1")) {
        @Override
        public boolean isValid(Double temp) {
            return temp >= 20 && temp <= 24;
        }
    },
    LOW(calculateTotalBeers("0.75")) {
        @Override
        public boolean isValid(Double temp) {
            return temp < 20;
        }
    },
    ;

    private static Function<Long, Long> calculateTotalBeers(String coef) {
        return amountOfPeople -> new BigDecimal(coef).multiply(BigDecimal.valueOf(amountOfPeople)).setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
    }

    public final Function<Long, Long> deliveryTotalBeers;
    public abstract boolean isValid(Double temp);

    Temperature(Function<Long, Long> deliveryTotalBeers) {
        this.deliveryTotalBeers = deliveryTotalBeers;
    }

    public static Long calculateBeers(Double temp, Long numberOfPeople) {
        return Arrays.asList(Temperature.values()).stream()
            .filter( categoryTemp -> categoryTemp.isValid(temp))
            .findFirst()
            .map(category -> category.deliveryTotalBeers.apply(numberOfPeople))
            .orElse(NumberUtils.LONG_ZERO);
    }
}
