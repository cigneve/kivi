package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.events.Event;


@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {

    int countByOwner_Id(Integer ownerId);
}