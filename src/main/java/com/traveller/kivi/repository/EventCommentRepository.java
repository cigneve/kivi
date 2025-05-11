package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.traveller.kivi.model.events.EventComment;

/**
 * To count the number of comments written by the user.
 */
@Repository
public interface EventCommentRepository extends JpaRepository<EventComment, Integer> {

    // Query to count the number of comments written by the user
    long countByOwner_Id(Integer ownerId);
}
