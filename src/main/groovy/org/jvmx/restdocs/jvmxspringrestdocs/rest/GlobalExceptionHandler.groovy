package org.jvmx.restdocs.jvmxspringrestdocs.rest

import static org.springframework.http.HttpStatus.BAD_REQUEST

import org.jvmx.restdocs.jvmxspringrestdocs.rest.services.ValidationException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.data.rest.webmvc.support.ConstraintViolationMessage
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class GlobalExceptionHandler {
  @Autowired
  MessageSource messageSource

  @ExceptionHandler
  @ResponseBody
  @ResponseStatus(BAD_REQUEST)
  Map validationException(ValidationException exception) {
    Locale locale = LocaleContextHolder.locale

    [
      cause     : exception.message,
      violations: exception.constraintViolations.collect { violation ->
        new ConstraintViolationMessage(violation, messageSource, locale)
      },
    ]
  }
}
