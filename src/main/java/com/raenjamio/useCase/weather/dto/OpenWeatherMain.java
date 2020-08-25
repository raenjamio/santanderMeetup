package com.raenjamio.useCase.weather.dto;

import java.io.Serializable;

public class OpenWeatherMain implements Serializable {

    private Double temp;
    private Double temp_min;
    private Double temp_max;

    public OpenWeatherMain() {
        //no-arg constructor
    }

    public OpenWeatherMain(Double temp, Double temp_min, Double temp_max) {
        this.temp = temp;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }
}
