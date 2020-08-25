package com.raenjamio.service.impl;

import com.raenjamio.exceptions.NotFoundException;
import com.raenjamio.service.MeetupService;
import com.raenjamio.domain.Meetup;
import com.raenjamio.repository.MeetupRepository;
import com.raenjamio.service.dto.MeetupDTO;
import com.raenjamio.service.mapper.MeetupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Meetup}.
 */
@Service
public class MeetupServiceImpl implements MeetupService {

    private final Logger log = LoggerFactory.getLogger(MeetupServiceImpl.class);

    private final MeetupRepository meetupRepository;

    private final MeetupMapper meetupMapper;
    private final MessageSource messageSource;

    public MeetupServiceImpl(MeetupRepository meetupRepository, MeetupMapper meetupMapper, MessageSource messageSource) {
        this.meetupRepository = meetupRepository;
        this.meetupMapper = meetupMapper;
        this.messageSource = messageSource;
    }

    /**
     * Save a meetup.
     *
     * @param meetupDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MeetupDTO save(MeetupDTO meetupDTO) {
        log.debug("Request to save Meetup : {}", meetupDTO);
        Meetup meetup = meetupMapper.toEntity(meetupDTO);
        meetup = meetupRepository.save(meetup);
        return meetupMapper.toDto(meetup);
    }

    /**
     * Get all the meetups.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<MeetupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Meetups");
        return meetupRepository.findAll(pageable)
            .map(meetupMapper::toDto);
    }

    /**
     * Get all the meetups with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<MeetupDTO> findAllWithEagerRelationships(Pageable pageable) {
        return meetupRepository.findAllWithEagerRelationships(pageable).map(meetupMapper::toDto);
    }
    

    /**
     * Get one meetup by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<MeetupDTO> findOne(String id) {
        log.debug("Request to get Meetup : {}", id);
        return meetupRepository.findOneWithEagerRelationships(id)
            .map(meetupMapper::toDto);
    }

    public MeetupDTO findOneOrException(String id) {
        log.debug("Request to get Meetup : {}", id);
        return meetupRepository.findOneWithEagerRelationships(id)
            .map(meetupMapper::toDto)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("meetup.service.find.not-found", null, Locale.ENGLISH)));

    }

    @Override
    public void incrementAssistant(String id) {
        MeetupDTO meetup= findOneOrException(id);
        meetup.incrementAssistant();
        save(meetup);
    }

    /**
     * Delete the meetup by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Meetup : {}", id);
        meetupRepository.deleteById(id);
    }
}
