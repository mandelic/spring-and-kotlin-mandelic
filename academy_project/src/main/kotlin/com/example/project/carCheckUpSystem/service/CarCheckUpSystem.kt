package com.example.project.carCheckUpSystem.service

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.car.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarIdNotFoundException
import com.example.project.carCheckUpSystem.exceptions.CarVinNotFoundException
import com.example.project.carCheckUpSystem.exceptions.VinNotUniqueException
import com.example.project.carCheckUpSystem.repository.JdbcCarCheckUpRepository
import com.example.project.carCheckUpSystem.repository.JdbcCarRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CarCheckUpSystem (val carCheckUpRepository: JdbcCarCheckUpRepository,
                        val carRepository: JdbcCarRepository
) {

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        val car = carRepository.findByVin(vin) ?: throw CarVinNotFoundException(vin)
        return carCheckUpRepository.getAllCheckUps().none {it.carId == car.id && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp {
        carRepository.findById(carCheckUp.carId) ?: throw CarIdNotFoundException(carCheckUp.carId)
        return carCheckUpRepository.insertCarCheckUp(carCheckUp)
    }

    fun addCar(car: Car): Car {
        carRepository.findByVin(car.vin) ?: return carRepository.insertCar(car)
        throw VinNotUniqueException(car.vin)
    }

    fun getCheckUps(vin: String): List<CarCheckUp> {
        return carCheckUpRepository.getCheckUpsByVin(vin)
    }

    fun getAllCheckUps(): List<CarCheckUp> = carCheckUpRepository.getAllCheckUps()

    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarById(id: Long): Car = carRepository.findById(id) ?: throw CarIdNotFoundException(id)

    fun countCheckUps(manufacturer: String): Int {
        val carList = carRepository.findAll().filter {it.manufacturer == manufacturer}
        return carList.sumOf {carCheckUpRepository.getCheckUpsByVin(it.vin).count()}
    }

    fun getCarDetails(vin: String): CarDetails {
        val car = carRepository.findByVin(vin) ?: throw CarVinNotFoundException(vin)
        val checkUpNecessary = isCheckUpNecessary(vin)
        val carCheckUpList = getCheckUps(vin).sortedByDescending { it.performedAt }
        return CarDetails(car, checkUpNecessary, carCheckUpList)
    }
}