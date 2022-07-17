package com.example.project.carCheckUpSystem.controller

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.car.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarNotFoundException
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
    fun addCarCheckUp(@RequestBody carCheckUp: CarCheckUp): ResponseEntity<CarCheckUp> {
        val newCarCheckUp = carCheckUpSystem.addCarCheckUp(carCheckUp)
        return ResponseEntity(newCarCheckUp, HttpStatus.OK)
    }

    @ExceptionHandler(CarNotFoundException::class)
    fun handleCarNotFoundException(exception: CarNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }


    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam vin: String): CarDetails = carCheckUpSystem.getCarDetails(vin)

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