package com.example.project

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.repository.CarRepository
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import com.example.project.carCheckUpSystem.carCheckUp.repository.CarCheckUpRepository
import com.example.project.carCheckUpSystem.carModel.entity.CarModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.domain.PageRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DatabaseTest @Autowired constructor(
    val carRepository: CarRepository,
    val carCheckUpRepository: CarCheckUpRepository
){

    @BeforeEach
    fun setUp() {
        val car1 = Car(UUID.fromString("32c93a03-bbbf-47e2-a270-d96123d3f16b"), LocalDate.now(), CarModel(UUID.randomUUID(), "Porsche", "Model1"), 2013, "vin1")
        val car2 = Car(UUID.fromString("4b017174-e69b-4d9f-b801-4149e7fe6708"), LocalDate.now(), CarModel(UUID.randomUUID(), "Porsche", "Model2"), 2015, "vin2")
        val cars = listOf(car1, car2)
        carRepository.saveAll(cars)
        val carCheckUp1 = CarCheckUp(UUID.randomUUID(), LocalDateTime.now(), "Ante", 1000, car1)
        val carCheckUp2 = CarCheckUp(UUID.randomUUID(), LocalDateTime.now(), "Ante", 750, car1)
        val carCheckUp3 = CarCheckUp(UUID.randomUUID(), LocalDateTime.now(), "Ana", 500, car2)
        val checkUps = listOf(carCheckUp1, carCheckUp2, carCheckUp3)
        carCheckUpRepository.saveAll(checkUps)
    }

    @Test
    fun testFindAllCars() {
        val allCars = carRepository.findAll()
        assertThat(allCars.size).isEqualTo(2)
    }

    @Test
    fun testFindAllCheckUps() {
        val allCheckUps = carCheckUpRepository.findAll()
        assertThat(allCheckUps.size).isEqualTo(3)
    }

    @Test
    fun findAllCarsPaged() {
        val pageable = PageRequest.of(0, 2)
        val allCars = carRepository.findAll(pageable)
        assertThat(allCars.totalPages).isEqualTo(1)
        assertThat(allCars.content[0].carModel.model).isEqualTo("Model1")
    }
}