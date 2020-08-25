package com.raenjamio.service;

import com.raenjamio.domain.Meetup;
import com.raenjamio.service.dto.MeetupDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.raenjamio.domain.Meetup}.
 */
public interface MeetupService {

    /**
     * Save a meetup.
     *
     * @param meetupDTO the entity to save.
     * @return the persisted entity.
     */
    MeetupDTO save(MeetupDTO meetupDTO);

    /**
     * Get all the meetups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MeetupDTO> findAll(Pageable pageable);

    /**
     * Get all the meetups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<MeetupDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" meetup.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MeetupDTO> findOne(String id);

    /**
     * Delete the "id" meetup.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    MeetupDTO findOneOrException(String id);

    void incrementAssistant(String id);
}
