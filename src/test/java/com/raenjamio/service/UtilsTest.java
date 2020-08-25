package com.raenjamio.service;

import com.raenjamio.service.dto.MeetupDTO;

import java.time.ZonedDateTime;

public class UtilsTest {

    public static final Long NUMNER_OF_PEOPLE_CONFIRMED = Long.valueOf("100");

    public static MeetupDTO getMeetup() {
        return new MeetupDTO.Builder()
            .with(builder -> {
                builder.id = "test";
                builder.dateMeetup = ZonedDateTime.now();
                builder.description = "test";
                builder.locationDescription = "locationTest";
                builder.numnerOfPeopleConfirmed = NUMNER_OF_PEOPLE_CONFIRMED;
            })
            .createMeetup();
    }
}
