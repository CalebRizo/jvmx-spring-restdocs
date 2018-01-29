package org.jvmx.restdocs.jvmxspringrestdocs.rest.services

import javax.validation.ConstraintViolation

class ValidationException extends RuntimeException {
  Set<ConstraintViolation> constraintViolations

  ValidationException(Set<ConstraintViolation> constraintViolations) {
    this('Error de validacion', constraintViolations)
  }

  ValidationException(String message, Set<ConstraintViolation> constraintViolations) {
    super(message)
    this.constraintViolations = constraintViolations
  }
}
