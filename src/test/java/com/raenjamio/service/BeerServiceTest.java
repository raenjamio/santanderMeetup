package com.raenjamio.service;

import com.raenjamio.domain.User;
import com.raenjamio.exceptions.NotFoundException;
import com.raenjamio.service.dto.BeerDTO;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.beer.service.BeerService;
import com.raenjamio.useCase.shared.Temperature;
import com.raenjamio.useCase.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.MessageSource;

import java.time.ZonedDateTime;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link BeerService}.
 */
public class BeerServiceTest {

    public static final Double TEMP_MEDIUM = Double.valueOf("20.0");
    public static final String TEMP_LOW = "5.0";
    public static final Double TEMP_HIGH = Double.valueOf("30.0");
    @Mock
    private MeetupService meetupService;

    @Mock
    private WeatherService weatherService;

    @Mock
    private MessageSource messageSource;

    private BeerService beerService;

    private User user;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);
        beerService = new BeerService(meetupService, weatherService, messageSource);
    }

    @Test
    public void shouldCalculateBeersWhenTempIsMedium() {
        MeetupDTO meetupDTO = UtilsTest.getMeetup();
        when(meetupService.findOneOrException(anyString())).thenReturn(meetupDTO);
        when(weatherService.getTempForMeetup(any())).thenReturn(TEMP_MEDIUM);
        Optional<BeerDTO> count = beerService.countByMeetup(meetupDTO.getId());
        assertTrue(count.isPresent());
        assertNotNull(count.get());
        assertEquals(count.get().getTotalBeers().longValue(),
            Temperature.valueOf(Temperature.MEDIUM.name()).deliveryTotalBeers.apply(Long.valueOf(meetupDTO.getNumberOfPeopleConfirmed())).longValue());
    }

    @Test
    public void shouldCalculateBeersWhenTempIsLow() {
        MeetupDTO meetupDTO = UtilsTest.getMeetup();
        when(meetupService.findOneOrException(anyString())).thenReturn(meetupDTO);
        when(weatherService.getTempForMeetup(any())).thenReturn(Double.valueOf(TEMP_LOW));
        Optional<BeerDTO> count = beerService.countByMeetup(meetupDTO.getId());
        assertTrue(count.isPresent());
        assertNotNull(count.get());
        assertEquals(count.get().getTotalBeers().longValue(),
            Temperature.valueOf(Temperature.LOW.name()).deliveryTotalBeers.apply(Long.valueOf(meetupDTO.getNumberOfPeopleConfirmed())).longValue());
    }

    @Test
    public void shouldCalculateBeersWhenTempIsHigh() {
        MeetupDTO meetupDTO = UtilsTest.getMeetup();
        when(meetupService.findOneOrException(anyString())).thenReturn(meetupDTO);
        when(weatherService.getTempForMeetup(any())).thenReturn(TEMP_HIGH);
        Optional<BeerDTO> count = beerService.countByMeetup(meetupDTO.getId());
        assertTrue(count.isPresent());
        assertNotNull(count.get());
        assertEquals(count.get().getTotalBeers().longValue(),
            Temperature.valueOf(Temperature.HIGH.name()).deliveryTotalBeers.apply(Long.valueOf(meetupDTO.getNumberOfPeopleConfirmed())).longValue());
    }

    @Test
    public void shouldThrowNotFoundExceptionWhenMeetupNotExist() {
        when(meetupService.findOneOrException(anyString())).thenThrow(NotFoundException.class);
        assertThrows(NotFoundException.class, () -> beerService.countByMeetup("test"));
    }
}
