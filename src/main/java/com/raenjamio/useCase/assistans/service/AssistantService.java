package com.raenjamio.useCase.assistans.service;

import com.raenjamio.domain.User;
import com.raenjamio.service.MeetupService;
import com.raenjamio.service.UserService;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.service.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {

    private final MeetupService meetupService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AssistantService(MeetupService meetupService, UserService userService, UserMapper userMapper) {
        this.meetupService = meetupService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    /**
     * if the user and meetup exist, the user is added to the confirmed list
     * @param idUser
     * @param idMeetup
     * @return
     */
    public MeetupDTO checkIn(String idUser, String idMeetup) {
        MeetupDTO meetup = meetupService.findOneOrException(idMeetup);
        User user = userService.findOneOrException(idUser);

        meetup.getAssistantsConfirmeds().add(userMapper.userToUserDTO(user));

        return meetupService.save(meetup);
    }
}
