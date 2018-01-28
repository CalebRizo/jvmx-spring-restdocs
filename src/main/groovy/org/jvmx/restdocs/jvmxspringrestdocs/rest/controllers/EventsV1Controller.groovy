package org.jvmx.restdocs.jvmxspringrestdocs.rest.controllers

import static org.springframework.http.ResponseEntity.created
import static org.springframework.web.util.UriComponentsBuilder.fromUriString

import org.jvmx.restdocs.jvmxspringrestdocs.persistence.model.Event
import org.jvmx.restdocs.jvmxspringrestdocs.rest.model.EventCommand
import org.jvmx.restdocs.jvmxspringrestdocs.rest.services.EventsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping('/v1/events')
class EventsV1Controller {

  @Autowired
  EventsService eventsService

  @GetMapping
  Map all() {
    [events: eventsService.all]
  }

  @PostMapping
  HttpEntity<Event> create(@RequestBody EventCommand command) {
    Event event = eventsService.create(command)
    URI uri = fromUriString("/v1/events/${event.id}").build().toUri()

    created(uri).body(event)
  }
}
