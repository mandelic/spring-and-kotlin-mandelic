package com.example

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
@Component
data class DataSource(
    @Value("\${database.name}") val dbName: String,
    @Value("\${database.username}") val username: String,
    @Value("\${database.password}") val password: String
)