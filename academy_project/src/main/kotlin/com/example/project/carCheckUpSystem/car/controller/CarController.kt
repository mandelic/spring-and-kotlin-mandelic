package com.example.project.carCheckUpSystem.car.controller

import com.example.project.carCheckUpSystem.car.controller.dto.AddCarDTO
import com.example.project.carCheckUpSystem.car.service.CarIdNotFoundException
import com.example.project.carCheckUpSystem.car.service.CarService
import com.example.project.carCheckUpSystem.car.service.CarVinNotFoundException
import com.example.project.carCheckUpSystem.car.service.VinNotUniqueException
import org.apache.coyote.Response
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.util.*

@RequestMapping("/car")
@Controller
class CarController(
    private val carService: CarService
){
    @PostMapping("/add")
    fun addCar(@RequestBody car: AddCarDTO) = ResponseEntity.ok(carService.addCar(car))

    @GetMapping()
    fun getAllCars() = ResponseEntity.ok(carService.getAllCars())

    @GetMapping("/paged")
    fun getAllCars(pageable: Pageable) = ResponseEntity.ok(
        carService.getAllCars(pageable)
    )

    @GetMapping("/{carId}")
    fun fetchCar(@PathVariable carId: UUID) = ResponseEntity.ok(carService.getCar(carId))

    @GetMapping("/count-check-ups")
    @ResponseBody
    fun countCheckUpsByManufacturer(): Map<String, Int?> {
        val manufacturerList = carService.getAllCars().map {it.manufacturer}.toSet()
        return manufacturerList.associateWith { carService.countCheckUpsByManufacturer(it)}
    }

    @ExceptionHandler(VinNotUniqueException::class)
    fun handleVinNotUniqueException(exception: VinNotUniqueException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.message)
    }
    @ExceptionHandler(CarIdNotFoundException::class)
    fun handleCarIdNotFoundException(exception: CarIdNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }
    @ExceptionHandler(CarVinNotFoundException::class)
    fun handleCarVinNotFoundException(exception: CarVinNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }
}

