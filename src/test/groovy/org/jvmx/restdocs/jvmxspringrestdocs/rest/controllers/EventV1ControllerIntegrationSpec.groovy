package org.jvmx.restdocs.jvmxspringrestdocs.rest.controllers

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.restdocs.payload.JsonFieldType.STRING
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

import org.junit.Rule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions as Should
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
    mockMvc = webAppContextSetup(context)
      .apply(documentationConfiguration(restDocumentation))
      .build()
  }

  Should 'get a list of events'() {
    when:
      Should result = mockMvc
        .perform(get('/v1/events').accept(APPLICATION_JSON))

    then:
      result
        .andExpect(status().isOk())
        .andExpect(jsonPath('events').isArray())
        .andExpect(jsonPath('event[0].name').value('EDC'))
        .andDo(document('eventsV1/get', preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath('events[]')
            .description('List of events')
        ).andWithPrefix('events[].',
          [
            fieldWithPath('name')
              .description('Event name')
              .type(STRING),
            fieldWithPath('place')
              .description('Place where the event will be')
              .type(STRING),
          ]
        )))
  }
}
