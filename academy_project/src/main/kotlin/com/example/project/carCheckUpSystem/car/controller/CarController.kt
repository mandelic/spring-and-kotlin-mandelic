package com.example.project.carCheckUpSystem.car.controller

import com.example.project.carCheckUpSystem.car.controller.dto.*
import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.service.CarService
import com.example.project.carCheckUpSystem.car.service.exception.*
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*

@RequestMapping("/car")
@RestController
class CarController(
    private val carService: CarService,
    private val resourceAssembler: CarResourceAssembler,
    private val carDetailsResourceAssembler: CarDetailsResourceAssembler
){
    @PostMapping("/add-with-model-id")
    fun addCar(@RequestBody car: AddCarDTO): ResponseEntity<Unit> {
        val carDTO = carService.addCar(car)
        val location: URI = linkTo(methodOn(CarController::class.java).fetchCar(carDTO.id)).toUri()
        return ResponseEntity.created(location).build()
    }

    @PostMapping("/add-with-model-data")
    fun addCarM(@RequestBody car: AddCarMDTO) : ResponseEntity<Unit> {
        val carMDTO = carService.addCarM(car)
        val location: URI = linkTo(methodOn(CarController::class.java).fetchCar(carMDTO.id)).toUri()
        return ResponseEntity.created(location).build()
    }

    @GetMapping()
    fun getAllCars(): ResponseEntity<CollectionModel<CarResource>>{
        return ResponseEntity.ok(
            resourceAssembler.toCollectionModel(
                carService.getAllCars()
            )
        )
    }

    @GetMapping("/paged")
    fun getAllCars(
        pageable: Pageable,
        pagedResourceAssembler: PagedResourcesAssembler<Car>
    ): ResponseEntity<PagedModel<CarResource>> {
        return ResponseEntity.ok(
            pagedResourceAssembler.toModel(
                carService.getAllCars(pageable),
                resourceAssembler
            )
        )
    }

    @GetMapping("/{carId}")
    fun fetchCar(@PathVariable carId: UUID): ResponseEntity<CarResource> {
        return ResponseEntity.ok(resourceAssembler.toModel(carService.getCar(carId)))
    }

    @GetMapping("/details-{carId}")
    fun fetchCarDetails(@PathVariable carId: UUID): ResponseEntity<CarDetailsResource> {
        return ResponseEntity.ok(carDetailsResourceAssembler.toModel(carService.getCarDetails(carId)))
    }

    @GetMapping("/count-check-ups")
    @ResponseBody
    fun countCheckUpsByManufacturer(): ResponseEntity<Map<String, Int?>> {
        val manufacturerList = carService.getAllCars().map {it.carModel.manufacturer}.toSet()
        val countMap = manufacturerList.associateWith { carService.countCheckUpsByManufacturer(it)}
        return ResponseEntity.ok(countMap)
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
    @ExceptionHandler(ModelIdNotFoundException::class)
    fun handleModelIdNotFoundException(exception: ModelIdNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }
    @ExceptionHandler(ManufacturerModelNotFoundException::class)
    fun handleManufacturerModelNotFoundException(exception: ManufacturerModelNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }
}
