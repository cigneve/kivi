package com.traveller.kivi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.events.Event;
import com.traveller.kivi.model.events.dto.EventDetails;
import com.traveller.kivi.model.events.EventLocation;

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
    List<Event> findByAttendants_Id(Integer attendantId);

    /**
     * Number of owned events
     * 
     * @param ownerId
     * @return
     */
    Long countByOwner_Id(Integer ownerId);

    List<Event> findByOwnerId(Integer ownerId);

    @Query("SELECT e FROM Event e JOIN e.locations l WHERE LOWER(l.title) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Event> findByLocations_NameContaining(String name);

    @Query("SELECT e FROM Event e WHERE LOWER(e.owner.username) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Event> findByOwnerNameContaining(String name);

}