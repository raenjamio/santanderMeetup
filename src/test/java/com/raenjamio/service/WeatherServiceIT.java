package com.raenjamio.service;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.raenjamio.MeetupApp;
import com.raenjamio.exceptions.NotFoundException;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.weather.dto.OpenWeatherResponse;
import com.raenjamio.useCase.weather.service.WeatherService;
import org.apache.http.HttpHeaders;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
public class WeatherServiceIT {

    private static final String URL_WEATHER = "weather";
    private static final String LOCAL_HOST = "http://localhost:";

    private WeatherService weatherService;

    @Mock
    private MessageSource messageSource;

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(getWiremockConfig());

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
        String urlTest = LOCAL_HOST + wireMockRule.port() + "/" + URL_WEATHER;
        RestClient restClient = new RestClient(urlTest, urlTest, null);
        weatherService = new WeatherService(restClient, messageSource);
    }


    public WireMockConfiguration getWiremockConfig() {
        WireMockConfiguration configuration = new WireMockConfiguration();
        configuration.dynamicPort();
        configuration.notifier(new ConsoleNotifier(true));
        configuration.usingFilesUnderClasspath("src/test/resources/wiremock/");
        return configuration;
    }

    @Test
    public void shouldReturnWeather() {
        stubForWeather();
        ResponseEntity<OpenWeatherResponse> response = weatherService.getCurrentWeather(new MeetupDTO());

        assertNotNull(response);
        assertEquals(response.getStatusCodeValue(), HttpStatus.OK.value());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getMain().getTemp());
    }

    @Test
    public void shouldReturnTempWhenExistForcastForMeetup() {
        stubForForecast();
        MeetupDTO meetupDTO =  UtilsTest.getMeetup();
        meetupDTO.setDateMeetup(ZonedDateTime.of(2020, 8, 19, 10, 0, 0, 0, ZoneId.systemDefault()));
        Double response = weatherService.getTempForMeetup(meetupDTO);

        assertNotNull(response);
        assertEquals(Double.valueOf("7.64"), response);
    }

    @Test(expected = NotFoundException.class)
    public void shouldReturnExceptionWhenNotExistForcastForMeetup() {
        stubForForecast();
        when(messageSource.getMessage(anyString(), anyList().toArray(), any())).thenReturn("message test");
        MeetupDTO meetupDTO =  UtilsTest.getMeetup();
        meetupDTO.setDateMeetup(ZonedDateTime.of(2021, 8, 19, 10, 0, 0, 0, ZoneId.systemDefault()));
        weatherService.getTempForMeetup(meetupDTO);
    }

    public void stubForWeather() {
        stubFor(get(urlEqualTo("/" + URL_WEATHER))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile("weatherResponse.json")));
    }

    public void stubForForecast() {
        stubFor(get(urlEqualTo("/" + URL_WEATHER + "&q=locationTest,ar"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .withBodyFile("forecastResponse.json")));
    }
}
