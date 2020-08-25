package com.raenjamio.useCase.FollowNotification.service;

import com.raenjamio.domain.Follow;
import com.raenjamio.domain.Notification;
import com.raenjamio.service.FollowService;
import com.raenjamio.service.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FollowNotification {

    private final NotificationService notificationService;
    private final FollowService followService;

    public FollowNotification(NotificationService notificationService, FollowService followService) {
        this.notificationService = notificationService;
        this.followService = followService;
    }

    public void createNotificationByMeetup(String idMeetup, String message) {
        Page<Follow> follows = followService.findAllByMeetup(Pageable.unpaged(), idMeetup);

        follows.getContent()
            .forEach(follow -> createNotification(follow, message));

    }

    private void createNotification(Follow follow, String message) {
        notificationService.save(new Notification(message, follow.getMeetup(), follow.getUser()));
    }
}
