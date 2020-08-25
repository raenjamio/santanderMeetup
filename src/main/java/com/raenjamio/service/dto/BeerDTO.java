package com.raenjamio.service.dto;

import org.apache.commons.lang3.math.NumberUtils;

import java.io.Serializable;

public class BeerDTO implements Serializable {

    public static final int COUNT_BEER_BOX = 6;
    private Long totalBeers;
    private int amountOfBox;

    public BeerDTO() {
        //no-args construct
    }

    public BeerDTO(Long totalBeers) {
        this.totalBeers = totalBeers;
        if (totalBeers < COUNT_BEER_BOX && totalBeers > 0) {
            this.amountOfBox = NumberUtils.INTEGER_ONE;
        } else {
            this.amountOfBox = Math.round(totalBeers / COUNT_BEER_BOX);
        }
    }

    public Long getTotalBeers() {
        return totalBeers;
    }

    public void setTotalBeers(Long totalBeers) {
        this.totalBeers = totalBeers;
    }

    public int getAmountOfBox() {
        return amountOfBox;
    }

    public void setAmountOfBox(int amountOfBox) {
        this.amountOfBox = amountOfBox;
    }
}
