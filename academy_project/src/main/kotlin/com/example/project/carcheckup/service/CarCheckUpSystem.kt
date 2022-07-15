package com.example.project.carcheckup.service

import com.example.project.carcheckup.entity.Car
import com.example.project.carcheckup.entity.CarCheckUp
import com.example.project.carcheckup.exceptions.CarNotFoundException
import com.example.project.carcheckup.repository.CarCheckUpRepository
import com.example.project.carcheckup.repository.CarRepository
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@Service
class CarCheckUpSystem (val carCheckUpRepository: CarCheckUpRepository,
                        val carRepository: CarRepository
) {

    fun isCheckUpNecessary(vin: String):Boolean {
        val today = LocalDateTime.now()
        val car = carRepository.findAll().first {it.vin == vin}
        return carCheckUpRepository.getAllCheckUps().values.none {it.carId == car.id && it.performedAt.isAfter(today.minusYears(1))}
    }

    /*fun addCarCheckUpData(vin: String, workerName: String, price: Long): CarCheckUp {
        val car = carRepository.findAll().first {it.vin == vin}
        val id = carCheckUpRepository.insertData(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), workerName, price, car.id)
        val addedCarCheckUp = carCheckUpRepository.findById(id)
        carRepository.addCarCheckUp(car.id, addedCarCheckUp)
        return addedCarCheckUp
    }*/

    fun addCarCheckUp(carCheckUp: CarCheckUp): CarCheckUp {
        if (carRepository.findAll().none {it.id == carCheckUp.carId}) throw CarNotFoundException(carCheckUp.carId)
        val addedCarCheckUp = carCheckUpRepository.insertCarCheckUp(carCheckUp)
        carRepository.addCarCheckUp(carCheckUp.carId, addedCarCheckUp)
        return addedCarCheckUp
    }

    /*fun addCarData(manufacturer: String, model: String, productionYear: Int, vin: String): Car {
        val id = carRepository.insertData(LocalDate.now(), manufacturer, model, productionYear, vin)
        return carRepository.findById(id)
    }*/

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

    fun getCarDetails(vin: String): List<Any> {
        val car = carRepository.findAll().first { it.vin == vin }
        return listOf(car.id, car.vin, car.manufacturer,car.model, car.productionYear, car.dateAdded)
    }
}