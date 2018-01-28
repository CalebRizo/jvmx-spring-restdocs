package org.jvmx.restdocs.jvmxspringrestdocs.persistence.repositories

import org.jvmx.restdocs.jvmxspringrestdocs.persistence.model.Event
import org.springframework.data.jpa.repository.JpaRepository

interface EventsRepository extends JpaRepository<Event, long> {
}
