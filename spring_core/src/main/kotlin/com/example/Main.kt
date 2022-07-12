package com.example

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

fun main() {
    val applicationContext: ApplicationContext = AnnotationConfigApplicationContext(ApplicationConfig::class.java)
    val service = applicationContext.getBean(CarCheckUpSystem::class.java)

}

@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
class ApplicationConfig