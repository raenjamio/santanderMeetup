package com.raenjamio.useCase.assistans.resource;

import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.useCase.assistans.service.AssistantService;
import com.raenjamio.web.rest.FollowResource;
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
 * REST controller for user confirm assistant a meetup
 */
@RestController
@RequestMapping("/api")
public class AssistansResource {

    private final Logger log = LoggerFactory.getLogger(FollowResource.class);

    private final AssistantService assistantService;

    public AssistansResource(AssistantService assistantService) {
        this.assistantService = assistantService;
    }

    @PostMapping("/users/{idUser}/meetups/{idMeetup}/assistants")
    public ResponseEntity checkIn(@PathVariable String idUser, @PathVariable String idMeetup) throws URISyntaxException {
        MeetupDTO meetup = assistantService.checkIn(idUser, idMeetup);
        return ResponseEntity.created(new URI("/api/meetups/" + idMeetup))
            .body(meetup);
    }
}
