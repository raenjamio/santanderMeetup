package com.raenjamio.service;

import com.raenjamio.domain.Follow;
import com.raenjamio.domain.Meetup;
import com.raenjamio.domain.User;
import com.raenjamio.repository.FollowRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Follow}.
 */
@Service
public class FollowService {

    private final Logger log = LoggerFactory.getLogger(FollowService.class);

    private final FollowRepository followRepository;

    public FollowService(FollowRepository followRepository) {
        this.followRepository = followRepository;
    }

    /**
     * Save a follow.
     *
     * @param follow the entity to save.
     * @return the persisted entity.
     */
    public Follow save(Follow follow) {
        log.debug("Request to save Follow : {}", follow);
        return followRepository.save(follow);
    }

    /**
     * Get all the follows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<Follow> findAll(Pageable pageable) {
        log.debug("Request to get all Follows");
        return followRepository.findAll(pageable);
    }


    /**
     * Get one follow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<Follow> findOne(String id) {
        log.debug("Request to get Follow : {}", id);
        return followRepository.findById(id);
    }

    /**
     * Delete the follow by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Follow : {}", id);
        followRepository.deleteById(id);
    }

    public void create(User user, Meetup meetup) {
        save(new Follow(user, meetup));
    }

    public Page<Follow> findAllByUser(Pageable pageable, String idUser) {
        return followRepository.findAllByUser(pageable, idUser);
    }

    public Page<Follow> findAllByMeetup(Pageable pageable, String idMeetup) {
        return followRepository.findAllByMeetup(pageable, idMeetup);
    }
}
