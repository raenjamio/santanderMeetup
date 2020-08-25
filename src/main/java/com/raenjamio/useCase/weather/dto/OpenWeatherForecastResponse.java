package com.raenjamio.useCase.weather.dto;

import java.io.Serializable;
import java.util.Set;

public class OpenWeatherForecastResponse implements Serializable {

    private String message;
    private String cod;
    private int count;
    private Set<OpenWeatherResponse> list;

    public OpenWeatherForecastResponse() {
        //no-arg constructor
    }

    public OpenWeatherForecastResponse(String message, String cod, int count, Set<OpenWeatherResponse> list) {
        this.message = message;
        this.cod = cod;
        this.count = count;
        this.list = list;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Set<OpenWeatherResponse> getList() {
        return list;
    }

    public void setList(Set<OpenWeatherResponse> list) {
        this.list = list;
    }
}
