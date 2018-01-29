package org.jvmx.restdocs.jvmxspringrestdocs.rest.services

import static org.springframework.web.util.UriComponentsBuilder.fromUriString

import org.jvmx.restdocs.jvmxspringrestdocs.persistence.model.Event
import org.jvmx.restdocs.jvmxspringrestdocs.persistence.repositories.EventsRepository
import org.jvmx.restdocs.jvmxspringrestdocs.rest.model.EventCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventsService {
  @Autowired
  EventsRepository eventsRepository
  @Autowired
  ValidationService validationService

  Map create(EventCommand command) {
    Event event = new Event(
      name: command.name,
      place: command.place
    )

    validationService.validate(event)
    eventsRepository.save(event)

    URI uri = fromUriString("/v1/events/${event.id}").build().toUri()

    [
      event: event,
      uri  : uri,
    ]
  }

  List<Event> getAll() {
    eventsRepository.findAll()
  }
}
