package com.example.project.carCheckUpSystem.service

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.car.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarNotFoundException
import com.example.project.carCheckUpSystem.exceptions.VinNotUniqueException
import com.example.project.carCheckUpSystem.repository.CarCheckUpRepository
import com.example.project.carCheckUpSystem.repository.CarRepository
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
        val car = carRepository.findAll().first {it.vin == vin}
        return carCheckUpRepository.getAllCheckUps().none {it.carId == car.id && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp {
        if (carRepository.findAll().none {it.id == carCheckUp.carId}) {
            throw CarNotFoundException(carCheckUp.carId)
        }
        return carCheckUpRepository.insertCarCheckUp(carCheckUp)
    }

    fun addCar(car: Car): Car {
        if (carRepository.findAll().any {it.vin == car.vin}){
                throw VinNotUniqueException(car.vin)
        }
        return carRepository.insertCar(car)
    }

    fun getCheckUps(vin: String): List<CarCheckUp> {
        return carRepository.getCheckUps(vin)
    }

    fun getAllCheckUps(): List<CarCheckUp> = carCheckUpRepository.getAllCheckUps()

    fun getAllCars(): List<Car> = carRepository.findAll()

    fun getCarById(id: Long): Car? = carRepository.findById(id)

    fun getCarByVin(vin: String): Car? = carRepository.findByVin(vin)

    fun countCheckUps(manufacturer: String): Int {
        val carList = carRepository.findAll().filter {it.manufacturer == manufacturer}
        return carList.sumOf {carRepository.getCheckUps(it.vin).count()}
    }

    fun getCarDetails(vin: String): CarDetails {
        val car = carRepository.findAll().first {it.vin == vin}
        val checkUpNecessary = isCheckUpNecessary(vin)
        val carCheckUpList = getCheckUps(vin).sortedByDescending { it.performedAt }
        return CarDetails(car, checkUpNecessary, carCheckUpList)
    }
}