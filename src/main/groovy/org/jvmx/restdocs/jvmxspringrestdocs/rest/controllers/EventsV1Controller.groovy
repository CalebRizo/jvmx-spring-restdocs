package org.jvmx.restdocs.jvmxspringrestdocs.rest.controllers

import org.jvmx.restdocs.jvmxspringrestdocs.rest.services.EventsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/v1/events')
class EventsV1Controller {

  @Autowired
  EventsService eventsService

  @GetMapping
  Map getEvents() {
    [events: eventsService.getAll()]
  }
}
