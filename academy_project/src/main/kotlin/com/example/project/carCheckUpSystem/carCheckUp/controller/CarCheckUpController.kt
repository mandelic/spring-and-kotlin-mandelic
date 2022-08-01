package com.example.project.carCheckUpSystem.carCheckUp.controller

import com.example.project.carCheckUpSystem.car.service.exception.CarIdNotFoundException
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.AddCarCheckUpDTO
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.CarCheckUpResource
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.CarCheckUpResourceAssembler
import com.example.project.carCheckUpSystem.carCheckUp.entity.CarCheckUp
import com.example.project.carCheckUpSystem.carCheckUp.service.CarCheckUpService
import com.example.project.carCheckUpSystem.carCheckUp.service.exception.CarCheckUpNotFoundException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.data.web.SortDefault
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import java.net.URI
import java.util.*
import javax.swing.SortOrder

@RequestMapping("/car-check-up")
@RestController
class CarCheckUpController(
    private val carCheckUpService: CarCheckUpService,
    private val resourceAssembler: CarCheckUpResourceAssembler
    ) {

    @PostMapping("/add")
    fun addCarCheckUp(@RequestBody carCheckUp: AddCarCheckUpDTO): ResponseEntity<Unit> {
        val carCheckUpDTO = carCheckUpService.addCarCheckUp(carCheckUp)
        val location: URI = linkTo(methodOn(CarCheckUpController::class.java).getCarCheckUpById(carCheckUpDTO.id)).toUri()
        return ResponseEntity.created(location).build()
    }


    @GetMapping()
    fun getAllCarCheckUps(): ResponseEntity<CollectionModel<CarCheckUpResource>> {
        return ResponseEntity.ok(
            resourceAssembler.toCollectionModel(
                carCheckUpService.getAllCheckUps()
            )
        )
    }

    @GetMapping("/paged")
    fun getAllCarCheckUps(
        pageable: Pageable,
        pagedResourceAssembler: PagedResourcesAssembler<CarCheckUp>
    ): ResponseEntity<PagedModel<CarCheckUpResource>> {
        return ResponseEntity.ok(
            pagedResourceAssembler.toModel(
                carCheckUpService.getAllCheckUps(pageable),
                resourceAssembler
            )
        )
    }


    @GetMapping("/car/{carId}")
    fun getCarCheckUp(@PathVariable carId: UUID): ResponseEntity<List<CarCheckUpResource>> {
        val carCheckUpResource = carCheckUpService.getCheckUps(carId).map {resourceAssembler.toModel(it)}
        return ResponseEntity.ok(carCheckUpResource)
    }

    @GetMapping("/{checkUpId}")
    fun getCarCheckUpById(@PathVariable checkUpId: UUID): ResponseEntity<CarCheckUpResource> {
        return ResponseEntity.ok(resourceAssembler.toModel(carCheckUpService.getCheckUpById(checkUpId)))
    }

    @GetMapping("/car/paged-{carId}")
    fun getPagedCarCheckUp(
        @PathVariable carId: UUID,
        pageable: Pageable,
        pagedResourceAssembler: PagedResourcesAssembler<CarCheckUp>
    ): ResponseEntity<PagedModel<CarCheckUpResource>> {
        return ResponseEntity.ok(
            pagedResourceAssembler.toModel(
                carCheckUpService.getPagedCheckUps(carId, pageable),
                resourceAssembler
            )
        )
    }

    @DeleteMapping("/delete/{id}")
    fun deleteCarCheckUp(
        @PathVariable id: UUID
    ): ResponseEntity<String> {
        carCheckUpService.delete(id)
        return ResponseEntity.status(200).body("Successfully deleted CarCheckUp with ID: ${id}")
    }

    @ExceptionHandler(CarIdNotFoundException::class)
    fun handleCarIdNotFoundException(exception: CarIdNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }

    @ExceptionHandler(CarCheckUpNotFoundException::class)
    fun handleCarCheckUpNotFoundException(exception: CarCheckUpNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }

}