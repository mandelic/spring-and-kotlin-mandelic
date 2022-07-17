package com.example.project

import com.example.project.carCheckUpSystem.service.CarCheckUpSystem
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
class ProjectApplicationTests {

	@Autowired
	private lateinit var mockMvc: MockMvc

	@Autowired
	private lateinit var carCheckUpSystem: CarCheckUpSystem

	@Test
	fun testGetCheckUps(){
		val expectedCheckUps = carCheckUpSystem.getAllCheckUps()
		mockMvc.get("/get-all-checkups")
			.andExpect {
				status { is2xxSuccessful() }
				content { expectedCheckUps }
			}
	}

	@Test
	fun testGetCars(){
		val expectedCars = carCheckUpSystem.getAllCars()
		mockMvc.get("/get-all-cars")
			.andExpect {
				status { is2xxSuccessful() }
				content { expectedCars }
			}
	}

}
