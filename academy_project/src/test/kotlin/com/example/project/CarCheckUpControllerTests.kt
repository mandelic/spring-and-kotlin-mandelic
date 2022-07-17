package com.example.project

import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.service.CarCheckUpSystem
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import java.time.LocalDateTime

@WebMvcTest
class CarCheckUpControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var carCheckUpSystem: CarCheckUpSystem

    private val carCheckUps = mutableMapOf(
        55L to CarCheckUp(55, LocalDateTime.of(2020, 12, 5, 12, 4), "Amanda", 1250, 2),
        56L to CarCheckUp(56, LocalDateTime.of(2022, 7,13,12,1), "Colleen", 700, 1)
    )

    @BeforeEach
    fun setUp() {
        every { carCheckUpSystem.getAllCheckUps() } answers { carCheckUps }
    }

    @Test
    fun testGetCheckUps(){
        val expectedCheckUps = carCheckUps
        mockMvc.get("/get-all-checkups")
            .andExpect {
                status { is2xxSuccessful() }
                content { expectedCheckUps }
            }
    }


}