package com.example

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
@Component
data class DataSource(
    @Value("\${dbName}") val dbName: String,
    @Value("\${username}") val username: String,
    @Value("\${password}") val password: String
)