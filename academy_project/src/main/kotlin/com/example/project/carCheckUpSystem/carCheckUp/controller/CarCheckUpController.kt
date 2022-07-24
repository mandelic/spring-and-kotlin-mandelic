package com.example.project.carCheckUpSystem.carCheckUp.controller

import com.example.project.carCheckUpSystem.car.service.CarIdNotFoundException
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.AddCarCheckUpDTO
import com.example.project.carCheckUpSystem.carCheckUp.service.CarCheckUpService
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*

@RequestMapping("/car-check-up")
@Controller
class CarCheckUpController(
    private val carCheckUpService: CarCheckUpService
    ) {

    @PostMapping("/add")
    fun addCarCheckUp(@RequestBody carCheckUp: AddCarCheckUpDTO) = ResponseEntity.ok(carCheckUpService.addCarCheckUp(carCheckUp))


    @GetMapping()
    fun getAllCarCheckUps() = ResponseEntity.ok(carCheckUpService.getAllCheckUps())

    @GetMapping("/paged")
    fun getAllCarCheckUps(pageable: Pageable) = ResponseEntity.ok(
        carCheckUpService.getAllCheckUps(pageable)
    )

    @ExceptionHandler(CarIdNotFoundException::class)
    fun handleCarIdNotFoundException(exception: CarIdNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }

}