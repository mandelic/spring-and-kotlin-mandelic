package com.example.project.carcheckup.controller

import com.example.project.carcheckup.entity.Car
import com.example.project.carcheckup.entity.CarCheckUp
import com.example.project.carcheckup.service.CarCheckUpSystem
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@Controller
class CarCheckUpController(private val carCheckUpSystem: CarCheckUpSystem) {

    /*@PostMapping("/add-car-data")
    @ResponseBody
    fun addCarData(@RequestParam manufacturer: String, model: String, productionYear: Int, vin: String): ResponseEntity<Car> {
        val newCar = carCheckUpSystem.addCarData(manufacturer, model, productionYear, vin)
        return ResponseEntity(newCar, HttpStatus.OK)
    }*/

    @PostMapping("/add-car")
    @ResponseBody
    fun addCar(@RequestBody car: Car): ResponseEntity<Car> {
        val newCar = carCheckUpSystem.addCar(car)
        return ResponseEntity(newCar, HttpStatus.OK)
    }

    /*@PostMapping("/add-car-check-up-data")
    @ResponseBody
    fun addCarCheckUpData(@RequestParam vin: String, workerName: String, price: Long): ResponseEntity<CarCheckUp> {
        val newCarCheckUp = carCheckUpSystem.addCarCheckUpData(vin, workerName, price)
        return ResponseEntity(newCarCheckUp, HttpStatus.OK)
    }*/

    @PostMapping("/add-car-check-up")
    @ResponseBody
    fun addCarCheckUp(@RequestBody carCheckUp: CarCheckUp): ResponseEntity<CarCheckUp> {
        val newCarCheckUp = carCheckUpSystem.addCarCheckUp(carCheckUp)
        return ResponseEntity(newCarCheckUp, HttpStatus.OK)
    }

    @GetMapping("/get-car")
    @ResponseBody
    fun getCar(@RequestParam vin: String): List<Any> {

        return mutableListOf(
            carCheckUpSystem.isCheckUpNecessary(vin),
            carCheckUpSystem.getCarDetails(vin),
            carCheckUpSystem.getCheckUps(vin).sortedByDescending{ it.performedAt }
        )
    }

    @GetMapping("/count-check-ups")
    @ResponseBody
    fun countCheckUpsByManufacturer(): Map<String, Int>{
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