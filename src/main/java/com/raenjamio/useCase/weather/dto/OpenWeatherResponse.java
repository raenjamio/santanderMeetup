package com.raenjamio.useCase.weather.dto;

import java.io.Serializable;

public class OpenWeatherResponse implements Serializable {

    private OpenWeatherMain main;
    private Integer timezone;
    private String name;
    private Long dt;

    public OpenWeatherResponse() {
        //no-arg construct
    }

    public OpenWeatherResponse(OpenWeatherMain main, Integer timezone, String name, Long dt) {
        this.main = main;
        this.timezone = timezone;
        this.name = name;
        this.dt = dt;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public OpenWeatherMain getMain() {
        return main;
    }

    public void setMain(OpenWeatherMain main) {
        this.main = main;
    }

    public Integer getTimezone() {
        return timezone;
    }

    public void setTimezone(Integer timezone) {
        this.timezone = timezone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
