package com.example.project.carCheckUpSystem.carCheckUp.service

import com.example.project.carCheckUpSystem.car.repository.CarRepository
import com.example.project.carCheckUpSystem.car.service.exception.CarIdNotFoundException
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.AddCarCheckUpDTO
import com.example.project.carCheckUpSystem.carCheckUp.controller.dto.CarCheckUpDTO
import com.example.project.carCheckUpSystem.carCheckUp.repository.CarCheckUpRepository
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CarCheckUpService (val carCheckUpRepository: CarCheckUpRepository,
                         val carRepository: CarRepository
) {
    fun addCarCheckUp(dto: AddCarCheckUpDTO): CarCheckUpDTO{
        val carCheckUp = dto.toCarCheckUp { carId ->
            carRepository.findById(carId).orElse(null) ?: throw CarIdNotFoundException(carId)
        }
        return CarCheckUpDTO(
            carCheckUpRepository.save(carCheckUp)
        )
    }
    fun getAllCheckUps(): List<CarCheckUpDTO> = carCheckUpRepository.findAll().map { CarCheckUpDTO(it) }

    fun getAllCheckUps(pageable: Pageable) = carCheckUpRepository.findAll(pageable).map { CarCheckUpDTO(it)}

}