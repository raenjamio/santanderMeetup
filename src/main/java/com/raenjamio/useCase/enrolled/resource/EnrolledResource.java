package com.raenjamio.useCase.enrolled.resource;

import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.enrolled.service.EnrolledService;
import com.raenjamio.web.rest.FollowResource;
import io.github.jhipster.web.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * REST controller for user enrolled a meetup
 */
@RestController
@RequestMapping("/api")
public class EnrolledResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    private final EnrolledService enrolledService;

    public EnrolledResource(EnrolledService enrolledService) {
        this.enrolledService = enrolledService;
    }

    @PostMapping("/users/{idUser}/meetups/{idMeetup}")
    public ResponseEntity enroll(@PathVariable String idUser, @PathVariable String idMeetup) throws URISyntaxException {
        MeetupDTO meetup = enrolledService.enroll(idUser, idMeetup);
        return ResponseEntity.created(new URI("/api/meetups/" + idMeetup))
            .body(meetup);
    }
}
