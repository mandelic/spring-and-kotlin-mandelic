package com.example.project

import com.example.project.carCheckUpSystem.car.service.CarService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithAnonymousUser
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTests @Autowired constructor(
    private val mockMvc: MockMvc,
    private val carService: CarService
){

    @Test
    fun contextLoads(){}

    @Test
    @WithAnonymousUser
    fun anonCount(){
        mockMvc.get("/car/count-check-ups")
            .andExpect {
                status { is2xxSuccessful() }
            }
    }

    @Test
    @WithAnonymousUser
    fun anonCar(){
        mockMvc.get("/car")
            .andExpect {
                status{
                    isUnauthorized()
                }
            }
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_ADMIN"])
    fun adminCar() {
        mockMvc.get("/car")
            .andExpect {
                status { is2xxSuccessful() }
            }
    }

    @Test
    @WithMockUser(authorities = ["SCOPE_USER"])
    fun userCar() {
        mockMvc.get("/car")
            .andExpect {
                status { isForbidden() }
            }
    }

}