package com.raenjamio.useCase.enrolled.service;

import com.raenjamio.domain.User;
import com.raenjamio.service.FollowService;
import com.raenjamio.service.MeetupKafkaProducer;
import com.raenjamio.service.MeetupService;
import com.raenjamio.service.UserService;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.service.dto.UserDTO;
import com.raenjamio.service.mapper.MeetupMapper;
import com.raenjamio.service.mapper.UserMapper;
import com.raenjamio.useCase.FollowNotification.service.FollowNotification;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Objects;

@Service
public class EnrolledService {

    private final MeetupService meetupService;
    private final UserService userService;
    private final MeetupKafkaProducer kafkaProducer;
    private final UserMapper userMapper;
    private final FollowService followService;
    private final MeetupMapper meetupMapper;
    private final FollowNotification followNotification;
    private final MessageSource messageSource;

    public EnrolledService(MeetupService meetupService, UserService userService, MeetupKafkaProducer kafkaProducer, UserMapper userMapper, FollowService followService,
                           MeetupMapper meetupMapper, FollowNotification followNotification, MessageSource messageSource) {
        this.meetupService = meetupService;
        this.userService = userService;
        this.kafkaProducer = kafkaProducer;
        this.userMapper = userMapper;
        this.followService = followService;
        this.meetupMapper = meetupMapper;
        this.followNotification = followNotification;
        this.messageSource = messageSource;
    }

    public MeetupDTO enroll(String idUser, String idMeetup) {
        MeetupDTO meetup = meetupService.findOneOrException(idMeetup);
        User userToRegister = userService.findOneOrException(idUser);

        UserDTO userEnrolled = meetup.getEnrolleds().stream()
            .filter(user -> user.getId().equals(idUser))
            .findFirst()
            .orElse(null);

        if (Objects.isNull(userEnrolled)) {
            meetup.getEnrolleds().add(userMapper.userToUserDTO(userToRegister));
            kafkaProducer.sendTopicUpdatedNumberPeople(idMeetup);
            followService.create(userToRegister, meetupMapper.toEntity(meetup));
            followNotification.createNotificationByMeetup(meetup.getId(),
                messageSource.getMessage("notification.enrolled.meetup.success", null, Locale.ENGLISH));
            return meetupService.save(meetup);
        }
        return meetup;
    }
}
