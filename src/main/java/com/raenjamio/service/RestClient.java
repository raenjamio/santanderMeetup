package com.raenjamio.service;

import com.google.common.collect.Sets;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.raenjamio.config.Constants;
import com.raenjamio.exceptions.NotFoundException;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.weather.dto.OpenWeatherForecastResponse;
import com.raenjamio.useCase.weather.dto.OpenWeatherMain;
import com.raenjamio.useCase.weather.dto.OpenWeatherResponse;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.util.Arrays;

public class RestClient {

    public static final Double DEFAULT_TEMP = Double.valueOf("20");
    private static Logger LOGGER = LoggerFactory.getLogger(RestClient.class);

    private String url;
    private String forecastUrl;
    private HttpHeaders headers;

    public RestClient(String url, String forecastUrl, HttpHeaders headers) {
        this.url = url;
        this.forecastUrl = forecastUrl;
        this.headers = headers;
    }

    @Retryable(value = { NotFoundException.class }, maxAttempts = 4, backoff = @Backoff(delay = 5000))
    public ResponseEntity<OpenWeatherResponse> getCurrentWeather() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.add(Constants.X_RAPIDAPI_HOST, Constants.OPEN_WEATHER_RAPIDAPI_COM);
        httpHeaders.add(Constants.X_RAPIDAPI_KEY, Constants.RAPIDAPI_KEY);

        HttpEntity<String> entity = new HttpEntity<>(Constants.PARAMETERS, httpHeaders);

        return restTemplate.exchange(url, HttpMethod.GET, entity, OpenWeatherResponse.class);
    }

    @Recover
    public ResponseEntity<OpenWeatherResponse> retryRecovery(RuntimeException t) {
        LOGGER.info(String.format("Retry Recovery - %s", t.getMessage()));
        OpenWeatherResponse response = new OpenWeatherResponse(new OpenWeatherMain(DEFAULT_TEMP, null, null), null, null,
            ZonedDateTime.now().toEpochSecond());
        return ResponseEntity.ok(response);
    }

    @HystrixCommand(fallbackMethod = "defaultWeather",  commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1500")})
    public OpenWeatherForecastResponse getForecast16Days(MeetupDTO meetup) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<String>(Constants.PARAMETERS, new HttpHeaders());

        String fullUrl  = forecastUrl.concat("&q=").concat(meetup.getLocationDescription()).concat(",ar");
        ResponseEntity<OpenWeatherForecastResponse> response = restTemplate.exchange(fullUrl, HttpMethod.GET, entity, OpenWeatherForecastResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return new OpenWeatherForecastResponse(response.getStatusCode().name(), null, NumberUtils.INTEGER_ZERO, Sets.newHashSet());
    }

    private OpenWeatherForecastResponse defaultWeather(MeetupDTO meetup) {
        OpenWeatherResponse response = new OpenWeatherResponse(new OpenWeatherMain(DEFAULT_TEMP, null, null), null, null,
            meetup.getDateMeetup().toEpochSecond());
        return new OpenWeatherForecastResponse("default value", null, NumberUtils.INTEGER_ZERO, Sets.newHashSet(response));
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }

    public String getForecastUrl() {
        return forecastUrl;
    }

    public void setForecastUrl(String forecastUrl) {
        this.forecastUrl = forecastUrl;
    }
}
