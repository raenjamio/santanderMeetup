package com.raenjamio.useCase.weather.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.raenjamio.exceptions.NotFoundException;
import com.raenjamio.service.RestClient;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.weather.dto.OpenWeatherForecastResponse;
import com.raenjamio.useCase.weather.dto.OpenWeatherMain;
import com.raenjamio.useCase.weather.dto.OpenWeatherResponse;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;

/**
 * Service Implementation for managing weather
 */
@Service
public class WeatherService {

    private final RestClient restClient;
    private final MessageSource messageSource;

    public WeatherService(RestClient restClient, MessageSource messageSource) {
        this.restClient = restClient;
        this.messageSource = messageSource;
    }

    public Double getCurrentTemp(MeetupDTO meetup) {
        ResponseEntity<OpenWeatherResponse> currentWeather = getCurrentWeather(meetup);
        return currentWeather.getBody().getMain().getTemp();

    }

    public Double getTempForMeetup(MeetupDTO meetup) {
        OpenWeatherForecastResponse forecastDays = restClient.getForecast16Days(meetup);

        return getTemp(forecastDays, meetup);

    }

    private Double getTemp(OpenWeatherForecastResponse forecastDays, MeetupDTO meetup) {
        return forecastDays.getList().stream()
            .filter( response -> isForecastForMeetup(meetup, response))
            .findFirst()
            .map(OpenWeatherResponse::getMain)
            .map(OpenWeatherMain::getTemp)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("weather.service.temp.not-found", null, Locale.ENGLISH)));

    }

    /**
     * look for the temperature with a period of +/- 2 hours
     * @param meetup
     * @param response
     * @return
     */
    private boolean isForecastForMeetup(MeetupDTO meetup, OpenWeatherResponse response) {
        ZonedDateTime dateForecast = Instant.ofEpochSecond(response.getDt()).atZone(ZoneId.systemDefault());
        return meetup.getDateMeetup().plusMinutes(-120).isBefore(dateForecast) && meetup.getDateMeetup().plusMinutes(120).isAfter(dateForecast);
    }

    public ResponseEntity<OpenWeatherResponse> getCurrentWeather(MeetupDTO meetup) {
        return restClient.getCurrentWeather();
    }
}
