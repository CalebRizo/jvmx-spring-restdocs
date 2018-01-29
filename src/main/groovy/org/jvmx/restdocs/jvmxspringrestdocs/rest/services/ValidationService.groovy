package org.jvmx.restdocs.jvmxspringrestdocs.rest.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import javax.validation.ConstraintViolation
import javax.validation.Validator

@Service
class ValidationService {
  @Autowired
  Validator validator

  void validate(Object object) throws ValidationException {
    validate(object, null)
  }

  void validate(Object object, String message) throws ValidationException {
    Set<ConstraintViolation> violations = validator.validate(object)

    if (violations.size() > 0) {
      String errorMessage = message ?: 'Error validando Objeto'
      throw new ValidationException(errorMessage, violations)
    }
  }

}
