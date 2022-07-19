package com.example.project.carCheckUpSystem.controller

import com.example.project.carCheckUpSystem.car.Car
import com.example.project.carCheckUpSystem.car.CarDetails
import com.example.project.carCheckUpSystem.carCheckUp.CarCheckUp
import com.example.project.carCheckUpSystem.exceptions.CarNotFoundException
import com.example.project.carCheckUpSystem.exceptions.VinNotUniqueException
import com.example.project.carCheckUpSystem.service.CarCheckUpSystem
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CarCheckUpController(
    private val carCheckUpSystem: CarCheckUpSystem
    ) {

    @PostMapping("/add-car")
    fun addCar(@RequestBody car: Car): ResponseEntity<Car> {
        val car = carCheckUpSystem.addCar(car)
        return ResponseEntity.ok().body(car)
    }

    @ExceptionHandler(VinNotUniqueException::class)
    fun handleVinNotUniqueException(exception: VinNotUniqueException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }

    @PostMapping("/add-car-check-up")
    @ResponseBody
    fun addCarCheckUp(@RequestBody carCheckUp: CarCheckUp): ResponseEntity<CarCheckUp> {
        val carCheckUp = carCheckUpSystem.addCarCheckUp(carCheckUp)
        return ResponseEntity.ok().body(carCheckUp)
    }

    @ExceptionHandler(CarNotFoundException::class)
    fun handleCarNotFoundException(exception: CarNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }

    @GetMapping("/get-car-by-id")
    fun getCar(@RequestParam("id", defaultValue = "1") id: Long) : ResponseEntity<Car> {
        val car = carCheckUpSystem.getCarById(id)
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(car)
    }

    @GetMapping("/get-car-details")
    fun getCar(@RequestParam("vin") vin: String) : ResponseEntity<CarDetails> {
        val car = carCheckUpSystem.getCarDetails(vin)
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(car)
    }

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