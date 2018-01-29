package org.jvmx.restdocs.jvmxspringrestdocs.rest.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean

import javax.validation.Validator

@Configuration
class ValidationConfig {
  @Bean
  Validator localValidatorFactoryBean() {
    new LocalValidatorFactoryBean()
  }
}
