package com.example.project.carCheckUpSystem.carModel.controller

import com.example.project.carCheckUpSystem.carModel.controller.dto.CarModelDTO
import com.example.project.carCheckUpSystem.carModel.service.CarManufacturers
import com.example.project.carCheckUpSystem.carModel.service.CarModelService
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/models", produces=[MediaType.APPLICATION_JSON_VALUE])
class CarModelController(
    private val carModelService: CarModelService
) {
    @GetMapping("/get-from-template")
    fun getModelsTmp(): ResponseEntity<List<CarManufacturers>> {
        return ResponseEntity.ok(carModelService.getAllTmp())
    }

    @GetMapping("/get-from-database")
    fun getModelsDb(): ResponseEntity<List<CarModelDTO>> {
        return ResponseEntity.ok(carModelService.getAllDb())
    }
}