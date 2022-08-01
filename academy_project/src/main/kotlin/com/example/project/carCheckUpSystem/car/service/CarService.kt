package com.example.project.carCheckUpSystem.car.service

import com.example.project.carCheckUpSystem.car.controller.dto.AddCarDTO
import com.example.project.carCheckUpSystem.car.controller.dto.AddCarMDTO
import com.example.project.carCheckUpSystem.car.controller.dto.CarDTO
import com.example.project.carCheckUpSystem.car.controller.dto.CarDetailsDTO
import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.repository.CarRepository
import com.example.project.carCheckUpSystem.car.service.exception.*
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import com.example.project.carCheckUpSystem.carCheckUp.repository.CarCheckUpRepository
import com.example.project.carCheckUpSystem.carModel.repository.CarModelRepository
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CarService(
    private val carRepository: CarRepository,
    private val carCheckUpRepository: CarCheckUpRepository,
    private val carModelRepository: CarModelRepository
) {

    fun getAllCars() = carRepository.findAll()

    fun getAllCars(pageable: Pageable) = carRepository.findAll(pageable)

    fun addCar(dto: AddCarDTO): Car {
        val car = dto.toCar { modelId ->
            carModelRepository.findById(modelId).orElseThrow { ModelIdNotFoundException(modelId) }
        }
        if (carRepository.findByVin(dto.vin) != null) {
                throw VinNotUniqueException(dto.vin)
        }
        return carRepository.save(car)
    }

    fun addCarM(dto: AddCarMDTO): Car{
        if (carRepository.findByVin(dto.vin) != null) {
            throw VinNotUniqueException(dto.vin)
        }
        if (!carModelRepository.existsByManufacturerAndModel(dto.carModel.manufacturer, dto.carModel.model))
            throw ManufacturerModelNotFoundException(dto.carModel.manufacturer, dto.carModel.model)
        val id = carModelRepository.findByManufacturerAndModel(dto.carModel.manufacturer, dto.carModel.model).id
        return carRepository.save(dto.toCar(id))
    }


    fun getCar(id: UUID) = carRepository.findByIdOrNull(id) ?: throw CarIdNotFoundException(id)
    fun getCarDetails(id: UUID) = carRepository.findByIdOrNull(id)?.let { car ->
        CarDetailsDTO(car, isCheckUpNecessary(car.vin)).toCarDetails()
    } ?: throw CarIdNotFoundException(id)

    fun isCheckUpNecessary(vin: String): Boolean {
        val car = carRepository.findByVin(vin) ?: throw CarVinNotFoundException(vin)
        return carCheckUpRepository.isCheckUpNecessary(car.id)
    }

    fun getCheckUpList(vin: String): List<CarCheckUp> = carCheckUpRepository.findByCarVin(vin)

    fun countCheckUpsByManufacturer(manufacturer: String): Int {
        val carList = carRepository.findByCarModelManufacturer(manufacturer)
        return carList.sumOf { carCheckUpRepository.findByCarVin(it.vin).count() }
    }
    fun delete(id: UUID) {
        try {
            carRepository.deleteById(id)
        } catch (e: Exception) {
            throw CarIdNotFoundException(id)
        }
    }

}