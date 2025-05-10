package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.events.EventSkeleton;

@Repository
public interface EventSkeletonRepository extends JpaRepository<EventSkeleton, Integer> {
}
