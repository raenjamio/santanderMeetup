package com.raenjamio.repository;
import com.raenjamio.domain.Follow;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Follow entity.
 */
@Repository
public interface FollowRepository extends MongoRepository<Follow, String> {

    String FOLLOWS_BY_USER = "folloysByUser";
    String FOLLOWS_BY_MEETUP = "folloysByMeetup";


    @Cacheable(cacheNames = FOLLOWS_BY_USER)
    Page<Follow> findAllByUser(Pageable pageable, String idUser);

    @Cacheable(cacheNames = FOLLOWS_BY_MEETUP)
    Page<Follow> findAllByMeetup(Pageable pageable, String idMeetup);
}
