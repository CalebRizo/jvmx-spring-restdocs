package org.jvmx.restdocs.jvmxspringrestdocs

import spock.lang.Specification

class TastingSpec extends Specification {
  def 'should taste Spock'() {
    expect:
      2 + 2 == 4
  }
}
