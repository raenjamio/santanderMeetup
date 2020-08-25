package com.raenjamio.useCase.beer.service;

import com.raenjamio.service.MeetupService;
import com.raenjamio.service.dto.BeerDTO;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.shared.Temperature;
import com.raenjamio.useCase.weather.service.WeatherService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Optional;

/**
 *This service is in charge of calculating the amount of beers
 */
@Service
public class BeerService {

    private final MeetupService meetupService;
    private final WeatherService weatherService;
    private final MessageSource messageSource;

    public BeerService(MeetupService meetupService, WeatherService weatherService, MessageSource messageSource) {
        this.meetupService = meetupService;
        this.weatherService = weatherService;
        this.messageSource = messageSource;
    }

    public Optional<BeerDTO> countByMeetup(String meetupId) {
        MeetupDTO meetupDTO = meetupService.findOneOrException(meetupId);

        Double tempMeetup = weatherService.getTempForMeetup(meetupDTO);

        Long countBeers = Temperature.calculateBeers(tempMeetup, Optional.ofNullable(meetupDTO.getNumberOfPeopleConfirmed()).orElse(NumberUtils.LONG_ZERO));

        return Optional.of(new BeerDTO(countBeers));
    }
}
