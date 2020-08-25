package com.raenjamio.repository;
import com.raenjamio.domain.Meetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data MongoDB repository for the Meetup entity.
 */
@Repository
public interface MeetupRepository extends MongoRepository<Meetup, String> {

    @Query("{}")
    Page<Meetup> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Meetup> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Meetup> findOneWithEagerRelationships(String id);

}
