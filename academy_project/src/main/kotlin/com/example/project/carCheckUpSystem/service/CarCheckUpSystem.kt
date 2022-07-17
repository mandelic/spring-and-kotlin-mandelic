package com.example.project.carCheckUpSystem.service

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.car.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarNotFoundException
import com.example.project.carCheckUpSystem.repository.CarCheckUpRepository
import com.example.project.carCheckUpSystem.repository.CarRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CarCheckUpSystem (val carCheckUpRepository: CarCheckUpRepository,
                        val carRepository: CarRepository
) {

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        val car = carRepository.findAll().first {it.vin == vin}
        return carCheckUpRepository.getAllCheckUps().values.none {it.carId == car.id && it.performedAt.isAfter(today.minusYears(1))}
    }

    fun addCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp {
        if (carRepository.findAll().none {it.id == carCheckUp.carId}) {
            throw CarNotFoundException(carCheckUp.carId)
        }
        val addedCarCheckUp = carCheckUpRepository.insertCarCheckUp(carCheckUp)
        carRepository.addCarCheckUp(carCheckUp.carId, addedCarCheckUp)
        return addedCarCheckUp
    }

    fun addCar(car: Car): Car = carRepository.insertCar(car)

    fun getCheckUps(vin: String): List<CarCheckUp> {
        val car = carRepository.findAll().first {it.vin == vin}
        return car.carCheckUpList
    }

    fun getAllCheckUps(): Map<Long, CarCheckUp> = carCheckUpRepository.getAllCheckUps()

    fun getAllCars(): List<Car> = carRepository.findAll()

    fun countCheckUps(manufacturer: String): Int {
        val carList = carRepository.findAll().filter {it.manufacturer == manufacturer}
        return carList.sumOf {it.carCheckUpList.count()}
    }

    fun getCarDetails(vin: String): CarDetails {
        val car = carRepository.findAll().first {it.vin == vin}
        val checkUpNecessary = isCheckUpNecessary(vin)
        val carCheckUpList = getCheckUps(vin).sortedByDescending { it.performedAt }
        return CarDetails(car, checkUpNecessary, carCheckUpList)
    }
}