package org.jvmx.restdocs.jvmxspringrestdocs.persistence.model

import org.hibernate.validator.constraints.NotBlank

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Event {
  @Id
  @GeneratedValue
  long id

  @NotBlank
  String name

  @NotBlank
  String place
}
