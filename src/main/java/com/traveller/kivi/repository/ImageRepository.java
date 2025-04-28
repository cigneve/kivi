package com.traveller.kivi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.traveller.kivi.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

}
