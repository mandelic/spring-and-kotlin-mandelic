package com.example.project.carCheckUpSystem.carModel.service

import com.example.project.carCheckUpSystem.car.entity.Car
import com.example.project.carCheckUpSystem.car.repository.CarRepository
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.CarCheckUpDTO
import com.example.project.carCheckUpSystem.carModel.controller.dto.AddCarModelDTO
import com.example.project.carCheckUpSystem.carModel.controller.dto.CarModelDTO
import com.example.project.carCheckUpSystem.carModel.repository.CarModelRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

@Service
class RestTemplateCarModelService(
    private val restTemplate: RestTemplate,
    @Value("\${car-model.base-url}") private val baseUrl: String,
    private val carModelRepository: CarModelRepository
): CarModelService {

    val log: Logger = LoggerFactory.getLogger(RestTemplateCarModelService::class.java)

    override fun getAllTmp(): List<CarManufacturers> {
        return restTemplate
            .getForObject<CarModelResponse>(baseUrl, String::class.java)
            .cars
    }

    override fun getAllDb(): List<CarModelDTO> = carModelRepository.findAll().map { CarModelDTO(it) }

    fun updateDatabase() {
        log.info("Fetching data and updating the database...")
        val data = restTemplate.getForObject<CarModelResponse>(baseUrl, String::class.java).cars
        data.forEach { el ->
            el.models.forEach {
                val exists = carModelRepository.existsByManufacturerAndModel(el.manufacturer, it)
                val newCarModel = AddCarModelDTO(el.manufacturer, it).toCarModel()
                if (!exists) carModelRepository.save(newCarModel)
            }
        }
        log.info("Database updated.")
    }
}