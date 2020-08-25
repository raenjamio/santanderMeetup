package com.raenjamio.repository;
import com.raenjamio.domain.Notification;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the Notification entity.
 */
@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    @Query("{'user': ?0}")
    List<Notification> findAllByUser(String id);
}
