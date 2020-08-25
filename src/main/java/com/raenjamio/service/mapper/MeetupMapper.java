package com.raenjamio.service.mapper;

import com.raenjamio.domain.*;
import com.raenjamio.service.dto.MeetupDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Meetup} and its DTO {@link MeetupDTO}.
 */
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MeetupMapper extends EntityMapper<MeetupDTO, Meetup> {


    @Mapping(target = "removeAssistantsConfirmed", ignore = true)
    @Mapping(target = "removeEnrolleds", ignore = true)

    default Meetup fromId(String id) {
        if (id == null) {
            return null;
        }
        Meetup meetup = new Meetup();
        meetup.setId(id);
        return meetup;
    }
}
