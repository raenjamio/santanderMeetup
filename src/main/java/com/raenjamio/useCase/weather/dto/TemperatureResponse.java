package com.raenjamio.useCase.weather.dto;

import com.raenjamio.domain.Meetup;
import com.raenjamio.service.dto.MeetupDTO;

import java.io.Serializable;

public class TemperatureResponse implements Serializable {

    private Double temperature;
    private MeetupDTO meetup;

    public TemperatureResponse() {
        //no-arg construct
    }

    public TemperatureResponse(Double temperature, MeetupDTO meetup) {
        this.temperature = temperature;
        this.meetup = meetup;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public MeetupDTO getMeetup() {
        return meetup;
    }

    public void setMeetup(MeetupDTO meetup) {
        this.meetup = meetup;
    }
}
