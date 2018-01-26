package org.jvmx.restdocs.jvmxspringrestdocs

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
class EventV1ControllerIntegrationSpec extends Specification {

  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

  private MockMvc mockMvc

  @Autowired
  private WebApplicationContext context

  void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
      .build()
  }

  def 'get a list of events'() {
    when:
      ResultActions result = mockMvc
        .perform(get('/v1/events').accept(APPLICATION_JSON))

    then:
      result
        .andExpect(status().isOk())
        .andDo(document('get-eventsV1'))
  }
}
