package com.example.project.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@Configuration
class RestConfiguration {
    @Bean
    fun provideRestTemplate(
        restTemplateBuilder: RestTemplateBuilder,
        @Value("\${car-model.base-url}") baseUrl: String
    ): RestTemplate {
        return restTemplateBuilder.rootUri(baseUrl).build()
    }
}