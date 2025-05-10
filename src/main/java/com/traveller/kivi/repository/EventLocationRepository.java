package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.events.EventLocation;

@Repository
public interface EventLocationRepository extends JpaRepository<EventLocation, Integer> {
}
