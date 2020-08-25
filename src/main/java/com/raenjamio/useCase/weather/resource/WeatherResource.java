package com.raenjamio.useCase.weather.resource;

import com.raenjamio.service.MeetupService;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.weather.dto.TemperatureResponse;
import com.raenjamio.useCase.weather.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing the temperature for day meetup
 */
@RestController
@RequestMapping("/api")
public class WeatherResource {

    private final Logger log = LoggerFactory.getLogger(WeatherResource.class);

    private final WeatherService weatherService;
    private final MeetupService meetupService;

    public WeatherResource(WeatherService weatherService, MeetupService meetupService) {
        this.weatherService = weatherService;
        this.meetupService = meetupService;
    }

    /**
     * {@code GET  /meetups/:id/beers} : get the temperature for the "id" meetup.
     *
     * @param id the id of the meetupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meetups/{id}/temperatures")
    public TemperatureResponse getTemperature(@PathVariable String id) {
        log.debug("REST request temperature to get Meetup : {}", id);
        MeetupDTO meetup = meetupService.findOneOrException(id);
        Double temp = weatherService.getTempForMeetup(meetup);
        return new TemperatureResponse(temp, meetup);
    }
}
