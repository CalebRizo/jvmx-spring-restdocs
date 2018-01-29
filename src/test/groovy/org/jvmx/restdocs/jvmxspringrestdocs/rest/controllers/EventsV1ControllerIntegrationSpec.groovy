package org.jvmx.restdocs.jvmxspringrestdocs.rest.controllers

import static groovy.json.JsonOutput.toJson
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
import static org.springframework.restdocs.payload.JsonFieldType.*
import static org.springframework.restdocs.payload.PayloadDocumentation.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup

import org.junit.Rule
import org.jvmx.restdocs.jvmxspringrestdocs.rest.model.EventCommand
import org.jvmx.restdocs.jvmxspringrestdocs.rest.services.EventsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions as Should
import org.springframework.web.context.WebApplicationContext
import spock.lang.Specification

@SpringBootTest
class EventsV1ControllerIntegrationSpec extends Specification {

  public static final List<FieldDescriptor> EVENT_COMMAND_DESCRIPTORS = [
    fieldWithPath('name')
      .description('Event name')
      .type(STRING),
    fieldWithPath('place')
      .description('Place where the event will be')
      .type(STRING),
  ]

  public static final List<FieldDescriptor> EVENT_RESPONSE_DESCRIPTORS = EVENT_COMMAND_DESCRIPTORS + [
    fieldWithPath('id')
      .description('Event id')
      .type(NUMBER),
  ]

  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation()

  private MockMvc mockMvc

  @Autowired
  private WebApplicationContext context

  @Autowired
  EventsService eventsService

  void setup() {
    mockMvc = webAppContextSetup(context)
      .apply(documentationConfiguration(restDocumentation)
      .uris().withScheme('https')
      .withHost('jvmx-spring-restdocs.herokuapp.com')
      .withPort(443))
      .build()
  }

  Should 'get a list of events'() {
    given:
      eventsService.create(new EventCommand(name: 'EDC', place: 'CDMX'))
    when:
      Should result = mockMvc
        .perform(get('/v1/events'))

    then:
      result
        .andExpect(status().isOk())
        .andExpect(jsonPath('events').isArray())
        .andExpect(jsonPath('events[0].name').value('EDC'))
        .andDo(document('eventsV1/getAll', preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath('events[]')
            .description('List of events')
            .type(ARRAY)
        ).andWithPrefix('events[].', EVENT_RESPONSE_DESCRIPTORS)
      ))
  }

  Should 'create a new event'() {
    given:
      String name = 'Pal Norte'
      EventCommand command = new EventCommand(name: name, place: 'Monterrey')
      String bodyRequest = toJson(command)

    when:
      Should result = mockMvc
        .perform(post('/v1/events')
        .contentType(APPLICATION_JSON)
        .content(bodyRequest))

    then:
      result
        .andExpect(status().isCreated())
        .andExpect(jsonPath('name').value(name))
        .andDo(document('eventsV1/post', preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
        requestFields(EVENT_COMMAND_DESCRIPTORS),
        responseFields(EVENT_RESPONSE_DESCRIPTORS),
      ))
  }

  Should 'make a bad request creating a new event'() {
    given:
      EventCommand command = new EventCommand(name: 'Hell & Heaven')
      String bodyRequest = toJson(command)

    when:
      Should result = mockMvc
        .perform(post('/v1/events')
        .contentType(APPLICATION_JSON)
        .content(bodyRequest))

    then:
      result
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath('cause').isString())
        .andDo(document('eventsV1/post', preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath('cause')
            .type(STRING)
            .description('The cause of the bad request reponse'),
          fieldWithPath('violations[]')
            .type(ARRAY)
            .description('The validation errors'),
        ).andWithPrefix('violations[].', [
          fieldWithPath('message')
            .description('Violation message')
            .type(STRING),
          fieldWithPath('invalidValue')
            .description('The invalid value')
            .type(STRING),
          fieldWithPath('entity')
            .description('The invalid entity')
            .type(STRING),
          fieldWithPath('property')
            .description('Incorrect property')
            .type(STRING),
        ])
      ))
  }
}
