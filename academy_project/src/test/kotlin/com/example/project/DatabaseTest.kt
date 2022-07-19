package com.example.project

import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.test.annotation.Commit
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.event.annotation.BeforeTestClass
import java.time.LocalDate

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
class DatabaseTest {
    @Autowired
    lateinit var jdbcTemplate: NamedParameterJdbcTemplate

    @BeforeEach
    fun setUp() {
        jdbcTemplate.update(
            "INSERT INTO cars (dateAdded, manufacturer, model, productionYear, vin) VALUES (:dateAdded, :manufacturer, :model, :productionYear, :vin)",
            mapOf("dateAdded" to LocalDate.now(),
                  "manufacturer" to "man",
                  "model" to "mod",
                  "productionYear" to 2020,
                  "vin" to "vinTest15")
        )
    }

    @AfterEach
    fun delete() {
        jdbcTemplate.update(
            "DELETE FROM cars WHERE vin = :vin",
            mapOf("vin" to "vinTest15")
        )
    }

    @Test
    fun test() {
        Assertions.assertThat(
            jdbcTemplate.queryForObject(
                "SELECT vin FROM cars WHERE vin = :vin ",
                mapOf("vin" to "vinTest15"),
                String::class.java
            )
        ).isEqualTo("vinTest15")
    }

    @Test
    fun testCount() {
        Assertions.assertThat(
            jdbcTemplate.queryForObject(
                "SELECT count(*) FROM cars WHERE vin = :vin",
                mapOf("vin" to "vinTest15"),
                Int::class.java
            )
        ).isEqualTo(1)
    }
}