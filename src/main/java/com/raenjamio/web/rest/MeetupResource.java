package com.raenjamio.web.rest;

import com.raenjamio.security.AuthoritiesConstants;
import com.raenjamio.service.MeetupService;
import com.raenjamio.web.rest.errors.BadRequestAlertException;
import com.raenjamio.service.dto.MeetupDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.raenjamio.domain.Meetup}.
 */
@RestController
@RequestMapping("/api")
public class MeetupResource {

    private final Logger log = LoggerFactory.getLogger(MeetupResource.class);

    private static final String ENTITY_NAME = "meetup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MeetupService meetupService;

    public MeetupResource(MeetupService meetupService) {
        this.meetupService = meetupService;
    }

    /**
     * {@code POST  /meetups} : Create a new meetup.
     *
     * @param meetupDTO the meetupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new meetupDTO, or with status {@code 400 (Bad Request)} if the meetup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/meetups")
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public ResponseEntity<MeetupDTO> createMeetup(@Valid @RequestBody MeetupDTO meetupDTO) throws URISyntaxException {
        log.debug("REST request to save Meetup : {}", meetupDTO);
        if (meetupDTO.getId() != null) {
            throw new BadRequestAlertException("A new meetup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MeetupDTO result = meetupService.save(meetupDTO);
        return ResponseEntity.created(new URI("/api/meetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /meetups} : Updates an existing meetup.
     *
     * @param meetupDTO the meetupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated meetupDTO,
     * or with status {@code 400 (Bad Request)} if the meetupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the meetupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/meetups")
    public ResponseEntity<MeetupDTO> updateMeetup(@Valid @RequestBody MeetupDTO meetupDTO) throws URISyntaxException {
        log.debug("REST request to update Meetup : {}", meetupDTO);
        if (meetupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MeetupDTO result = meetupService.save(meetupDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, meetupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /meetups} : get all the meetups.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of meetups in body.
     */
    @GetMapping("/meetups")
    public ResponseEntity<List<MeetupDTO>> getAllMeetups(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Meetups");
        Page<MeetupDTO> page;
        if (eagerload) {
            page = meetupService.findAllWithEagerRelationships(pageable);
        } else {
            page = meetupService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /meetups/:id} : get the "id" meetup.
     *
     * @param id the id of the meetupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the meetupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/meetups/{id}")
    public ResponseEntity<MeetupDTO> getMeetup(@PathVariable String id) {
        log.debug("REST request to get Meetup : {}", id);
        Optional<MeetupDTO> meetupDTO = meetupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(meetupDTO);
    }

    /**
     * {@code DELETE  /meetups/:id} : delete the "id" meetup.
     *
     * @param id the id of the meetupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/meetups/{id}")
    public ResponseEntity<Void> deleteMeetup(@PathVariable String id) {
        log.debug("REST request to delete Meetup : {}", id);
        meetupService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
