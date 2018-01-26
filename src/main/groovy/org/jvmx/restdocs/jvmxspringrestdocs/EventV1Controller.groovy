package org.jvmx.restdocs.jvmxspringrestdocs

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/v1/events')
class EventV1Controller {

  @GetMapping
  Map getEvents() {
    [hello: 'hello']
  }
}
