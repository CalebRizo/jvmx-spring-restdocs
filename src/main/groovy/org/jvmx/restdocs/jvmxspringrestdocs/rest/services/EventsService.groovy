package org.jvmx.restdocs.jvmxspringrestdocs.rest.services

import org.jvmx.restdocs.jvmxspringrestdocs.persistence.model.Event
import org.jvmx.restdocs.jvmxspringrestdocs.persistence.repositories.EventsRepository
import org.jvmx.restdocs.jvmxspringrestdocs.rest.model.EventCommand
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EventsService {
  @Autowired
  EventsRepository eventsRepository

  Event create(EventCommand command) {
    Event newEvent = new Event(
      name: command.name,
      place: command.place
    )
    eventsRepository.save(newEvent)
  }

  List<Event> getAll() {
    eventsRepository.findAll()
  }
}
