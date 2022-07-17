package com.example.project.carCheckUpSystem.controller

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.car.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.service.CarCheckUpSystem
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CarCheckUpController(private val carCheckUpSystem: CarCheckUpSystem) {

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody car: Car): ResponseEntity<Car> {
        val newCar = carCheckUpSystem.addCar(car)
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    @PostMapping("/add-car-check-up")
    @ResponseBody
    fun addCarCheckUp(@RequestBody carCheckUp: CarCheckUp): ResponseEntity<String> {
        return try {
            val newCarCheckUp = carCheckUpSystem.addCarCheckUp(carCheckUp)
            ResponseEntity(newCarCheckUp.toString(), HttpStatus.OK)
        } catch (e: java.lang.Exception) {
            ResponseEntity(e.message, HttpStatus.NOT_FOUND)
        }


    }


    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam vin: String): CarDetails = CarDetails(carCheckUpSystem, vin)

    @GetMapping("/count-check-ups")
    @ResponseBody
    fun countCheckUpsByManufacturer(): Map<String, Int?>{
        val manufacturerList = carCheckUpSystem.getAllCars().map {it.manufacturer}.toSet()
        return manufacturerList.associateWith { carCheckUpSystem.countCheckUps(it) }

    }

    @GetMapping("/get-all-cars")
    @ResponseBody
    fun getAllCars() = carCheckUpSystem.getAllCars()

    @GetMapping("/get-all-checkups")
    @ResponseBody
    fun getAllCarCheckUps() = carCheckUpSystem.getAllCheckUps()

}