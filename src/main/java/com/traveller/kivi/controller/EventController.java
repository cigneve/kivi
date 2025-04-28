package com.traveller.kivi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.traveller.kivi.service.EventService;

@RestController
@RequestMapping("/api/posts")
public class EventController {

    @Autowired
    private EventService eventService;

}