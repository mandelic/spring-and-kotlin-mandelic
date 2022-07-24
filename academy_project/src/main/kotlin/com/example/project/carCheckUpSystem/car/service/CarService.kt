package com.example.project.carCheckUpSystem.car.service

import com.example.project.carCheckUpSystem.car.controller.dto.AddCarDTO
import com.example.project.carCheckUpSystem.car.controller.dto.CarDTO
import com.example.project.carCheckUpSystem.car.controller.dto.CarDetailsDTO
import com.example.project.carCheckUpSystem.car.entity.CarDetails
import com.example.project.carCheckUpSystem.car.repository.CarRepository
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import com.example.project.carCheckUpSystem.carCheckUp.repository.CarCheckUpRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class CarService(
    private val carRepository: CarRepository,
    private val carCheckUpRepository: CarCheckUpRepository
) {

    fun getAllCars() = carRepository.findAll().map { CarDTO(it) }

    fun addCar(dto: AddCarDTO){
        if (carRepository.findByVin(dto.vin) != null) {
                throw VinNotUniqueException(dto.vin)
        }
        CarDTO(carRepository.save(dto.toCar()))
    }
    fun getCar(id: UUID) = carRepository.findByIdOrNull(id)?.let {car ->
        CarDetailsDTO(car, isCheckUpNecessary(car.vin), getCheckUpList(car.vin).sortedByDescending { it.performedAt })
    } ?: throw CarIdNotFoundException(id)

    fun isCheckUpNecessary(vin: String): Boolean {
        val car = carRepository.findByVin(vin) ?: throw CarVinNotFoundException(vin)
        return carCheckUpRepository.isCheckUpNecessary(car.id)
    }

    fun getCheckUpList(vin: String): List<CarCheckUp> = carCheckUpRepository.findByCarVin(vin)

    fun countCheckUpsByManufacturer(manufacturer: String): Int {
        val carList = carRepository.findAll().filter { it.manufacturer == manufacturer }
        return carList.sumOf { carCheckUpRepository.findByCarVin(it.vin).count() }
    }

    fun getAllCars(pageable: Pageable) = carRepository.findAll(pageable).map { CarDTO(it) }
}