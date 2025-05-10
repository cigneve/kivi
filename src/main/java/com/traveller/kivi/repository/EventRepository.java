package com.traveller.kivi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.dto.EventDetails;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    /**
     * Number of attended events.
     * 
     * @param attendantId
     * @return
     */
    @Query("SELECT COUNT(at) FROM Event e JOIN e.attendants at WHERE at.id = :attendantId")
    Long countByAttendants_Id(Integer attendantId);

    /**
     * List of attended events.
     * 
     * @param attendantId
     * @return
     */
    @Query("SELECT e FROM Event e JOIN e.attendants at WHERE at.id = :attendantId")
    List<Event> getByAttendants_Id(Integer attendantId);

    /**
     * Number of owned events
     * 
     * @param ownerId
     * @return
     */
    Long countByOwner_Id(Integer ownerId);

    List<Event> getByOwner_Id(Integer ownerId);
}