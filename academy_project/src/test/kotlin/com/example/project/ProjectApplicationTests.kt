package com.example.project

import com.example.project.carCheckUpSystem.car.service.CarService
import com.example.project.carCheckUpSystem.carCheckUp.service.CarCheckUpService
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
	private lateinit var carService: CarService

    @Autowired
    private lateinit var carCheckUpService: CarCheckUpService

	@Test
	fun testGetCheckUps(){
		val expectedCheckUps = carCheckUpService.getAllCheckUps()
		mockMvc.get("/car-check-up")
			.andExpect {
				status { is2xxSuccessful() }
				content { expectedCheckUps }
			}
	}

	@Test
	fun testGetCars(){
		val expectedCars = carService.getAllCars()
		mockMvc.get("/car")
			.andExpect {
				status { is2xxSuccessful() }
				content { expectedCars }
			}
	}

	@Test
	fun testCountCheckUps() {
		val expectedCount = carService.countCheckUpsByManufacturer("Porsche")
		mockMvc.get("/car/count-check-ups")
			.andExpect {
				status { is2xxSuccessful() }
				content { expectedCount}
			}
	}

	@Test
	fun testPagedCars() {
        val expectedResult = carService.getAllCars()
        mockMvc.get("/car/paged")
            .andExpect {
            status { is2xxSuccessful() }
            content { expectedResult}
            }
    }

}
