package org.jvmx.restdocs.jvmxspringrestdocs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
class TastingSpec extends Specification {

  @Autowired
  WebApplicationContext context

  def 'should taste Spock'() {
    expect:
      2 + 2 == 4
  }

  def 'should test spring context'() {
    expect: 'Spring context exists'
      context != null
  }
}
